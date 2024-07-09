package com.wpp.pojo;

import java.util.Date;

public class TemperatureExpire extends Temperature{
    private Date expire;

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }
}
