package com.wpp.controller;

import com.wpp.pojo.Temperature;
import com.wpp.service.impl.TemperatureServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@Controller
@RequestMapping("/")
public class TemperatureController {

//    @Autowired
//    RedisTemplate redisTemplate;

    @Autowired
    TemperatureServiceImpl temperatureService;



    @RequestMapping("/getTemp")
    public String getTemperature(){
        Temperature temperature = temperatureService.getTemperatureService(1, temperatureService.getDate());
        System.out.println(temperature);
        return "temp";
    }

    @PostMapping("/getTempByDate")
    @ResponseBody
    public Temperature geTempByData(@RequestBody Temperature tempFromView) throws InterruptedException {
        Date date = tempFromView.getDate();
        Temperature temperature = temperatureService.getTemperatureService(1, date);
        System.out.println("查询完成："+new Date());
        return temperature;

    }

    @PostMapping("/getNowTemp")
    @ResponseBody
    public Temperature getNowTemp(@RequestBody Temperature tempFromView) throws InterruptedException {
        Date date = tempFromView.getDate();
        Temperature temperature = temperatureService.getTemperatureService(1, date);
        System.out.println("查询完成："+new Date());
        return temperature;

    }
    @RequestMapping("/setNowTemp")
    @ResponseBody
    public String setTemperature(@RequestBody Temperature temperature){
        if(temperature != null){
            temperatureService.storageTemplate(temperature);
            System.out.println(temperature);
        }
        return "success";
    }
}
