package com.wpp.service.impl;

import com.wpp.dao.MeasureDao;
import com.wpp.pojo.Measure;
import com.wpp.service.MeasureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasureServiceImpl implements MeasureService {


    @Autowired
    MeasureDao measureDao;

    @Override
    public List<Measure> getAllMeasure() {
        return measureDao.getAllMeasure();
    }
}
