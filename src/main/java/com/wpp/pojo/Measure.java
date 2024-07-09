package com.wpp.pojo;


public class Measure {
    //当前温度计
    private int id;
    //当前温度阈值
    private float temperature;
    //是否开启监测
    private boolean isOpenCheck;
    //是否开启告警
    private boolean isSendAlarm;
    //监测粒度
    private int secGranularity;
    //上限
    private float maxTemp;
    //下限
    private float minTemp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public boolean isOpenCheck() {
        return isOpenCheck;
    }

    public void setOpenCheck(boolean openCheck) {
        isOpenCheck = openCheck;
    }

    public boolean isSendAlarm() {
        return isSendAlarm;
    }

    public void setSendAlarm(boolean sendAlarm) {
        isSendAlarm = sendAlarm;
    }

    public int getSecGranularity() {
        return secGranularity;
    }

    public void setSecGranularity(int secGranularity) {
        this.secGranularity = secGranularity;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", temperature=" + temperature +
                ", isOpenCheck=" + isOpenCheck +
                ", isSendAlarm=" + isSendAlarm +
                ", secGranularity=" + secGranularity +
                ", maxTemp=" + maxTemp +
                ", minTemp=" + minTemp +
                '}';
    }
}
