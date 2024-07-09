package com.wpp.service.impl;
import com.wpp.dao.TemperatureDao;
import com.wpp.pojo.Temperature;
import com.wpp.pojo.TemperatureExpire;
import com.wpp.service.TemperatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Autowired
    TemperatureDao temperatureDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public Temperature getTemperatureService(int id, Date date) {

        Map<String, Object> mapKey = getMapKey(id, date);

        Temperature temperature = queryWithMutex(mapKey);

        return temperature;
    }


    private Map<String, Object> getMapKey(int id, Date date){
        // 将当前时间转换为Calendar对象
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 减去5秒
        calendar.add(Calendar.SECOND, +0);
        // 获取减去5秒后的时间
        date = calendar.getTime();
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("date", date);
        return map;
    }

    private boolean tryLock(String key){
        Boolean lock = redisTemplate.opsForValue().setIfAbsent(key, key + "_lock", 10, TimeUnit.SECONDS);
        return lock != null && lock;
    }

    private void unLock(String key){
        redisTemplate.delete(key);
    }
    private Temperature queryWithMutex(Map<String, Object> mapKey){
        String key = mapKey.get("id") + "_" + mapKey.get("date").toString();
        Temperature temperaById ;
        try{
            Object o = redisTemplate.opsForValue().get(key);
            if(o != null){
                return (Temperature) o;
            }
            //这里互斥锁方式解决缓存击穿问题
            //获取锁
            boolean lock = tryLock(key);
            if(!lock){
                //锁不存在，则休眠并重试
                Thread.sleep(50);
                return queryWithMutex(mapKey);
            }
            System.out.println("缓存未查到，去库里查------------");
            temperaById = temperatureDao.getTemperaById(mapKey);
            //这里为了突出一下解决缓存穿透问题，如果数据库里也没查到就先把null放到缓存里
            if(temperaById != null){
                redisTemplate.opsForValue().set(key, temperaById,30, TimeUnit.MILLISECONDS );
            }else {
                redisTemplate.opsForValue().set(key, null,10, TimeUnit.MILLISECONDS );
            }
        }catch (Exception e){
            throw  new RuntimeException("获取失败");
        }finally {
            unLock(key);
        }

       return temperaById;
    }

    /**
     * 逻辑过期方式解决缓存击穿问题
     */
    private static final ExecutorService CACHE_REBUILD_EXECUTOR = Executors.newFixedThreadPool(10);
    private Temperature queryWithLogicalExpire(Map<String, Object> mapKey){
        String key = mapKey.get("id") + "_" + mapKey.get("date").toString();
        Temperature temperaById ;
        try{
            //1、获取缓存信息
            Object o = redisTemplate.opsForValue().get(key);
            //2、判断是否存在
            if(o == null){
                //3、不存在,直接返回
                return null;
            }
            //4、存在，则从数据库里序列化，这里写法不太对
            TemperatureExpire temperature = (TemperatureExpire) o;
            //5、判断是否过期
            Date expire = temperature.getExpire();

            //6。1、未过期
            if(expire.after(new Date())){
                return temperature;
            }
            //6.2、已过期
            //7、缓存重建
            //获取互斥锁
            boolean isLock = tryLock(key);
            //判断是否获取锁成功
            if(isLock){
                //成功，开启独立线程，实现缓存重建，
                CACHE_REBUILD_EXECUTOR.submit(()->{
                    buildCache(mapKey, new Date(2030-10-20));
                });
            }
            //失败则返回过期信息
            return temperature;

            //这里为了突出不需要再解决缓存穿透问题，因为数据已经进过预热加入到了缓存，没在缓存中的数据必然是没在数据库中的
//            if(temperaById != null){
//                redisTemplate.opsForValue().set(key, temperaById,30, TimeUnit.MILLISECONDS );
//            }else {
//                redisTemplate.opsForValue().set(key, null,10, TimeUnit.MILLISECONDS );
//            }
        }catch (Exception e){
            throw  new RuntimeException("获取失败");
        }finally {
            unLock(key);
        }

    }


    private void buildCache(Map<String, Object> mapKey, Date expireDate){
        String key = mapKey.get("id") + "_" + mapKey.get("date").toString();
        //查询基本温度信息
        Temperature temperaById = temperatureDao.getTemperaById(mapKey);
        //封装逻辑过期时间
        TemperatureExpire temperatureExpire = (TemperatureExpire) temperaById;
        temperatureExpire.setExpire(expireDate);
        //加入缓存
        redisTemplate.opsForValue().set(key, temperatureExpire);
    }
    @Override
    public void storageTemplate(Temperature temperature) {
        String key = temperature.getId()+"_"+temperature.getDate().toString();
        redisTemplate.opsForValue().set(key, temperature);
        temperatureDao.insertTemperature(temperature);
    }

    @Override
    public Date getDate(){
        // 创建一个 Calendar 实例
        Calendar calendar = Calendar.getInstance();

        // 设置年、月、日、时、分、秒
        calendar.set(Calendar.YEAR, 2023);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER); // 注意月份从0开始，所以9月对应Calendar.SEPTEMBER
        calendar.set(Calendar.DAY_OF_MONTH, 10);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        // 获取包含特定日期时间的 Date 对象
        Date specificDate = calendar.getTime();
        return specificDate;

    }
}
