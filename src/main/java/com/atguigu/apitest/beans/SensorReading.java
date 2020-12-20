package com.atguigu.apitest.beans;

/**
 * @email yaoshuai.1024@bytedance.com
 * @author:yaoshuai
 * @date: 2020/12/20 9:53 上午
 */

//传感器温度读数的数据类型
public class SensorReading {
    //属性：id，时间戳，温度值
    private String id;
    private String timestamp;
    private Double temperature;

    public SensorReading() {
    }

    public SensorReading(String id, String timestamp, Double temperature) {
        this.id = id;
        this.timestamp = timestamp;
        this.temperature = temperature;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "SensorReading{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", temperature=" + temperature +
                '}';
    }
}
