package com.wpp.dao;

import com.wpp.pojo.Temperature;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface TemperatureDao {
    Temperature getTemperaById(Map<String, Object> map);
    void insertTemperature(Temperature temperature);

}
