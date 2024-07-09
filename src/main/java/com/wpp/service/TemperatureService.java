package com.wpp.service;

import com.wpp.pojo.Temperature;

import java.util.Calendar;
import java.util.Date;


public interface TemperatureService {
    Temperature getTemperatureService(int id, Date date);
    void storageTemplate(Temperature temperature);
     Date getDate();
}
