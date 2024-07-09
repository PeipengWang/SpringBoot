package com.wpp.dao;

import com.wpp.pojo.Measure;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MeasureDao {
    List<Measure> getAllMeasure();
}
