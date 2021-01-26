package com.atguigu.PbAndJson;

import com.atguigu.PbAndJson.*;
/**
 * @author:yaoshuai.1024
 * @date: 2021/1/26 1:32 下午
 */
public class PbToJson {

    public static void main(String[] args) {
        // protobuf 转 json
        Mobile.MobilePhone.Builder builder = Mobile.MobilePhone.newBuilder();
        Mobile.Hardware.Builder hardware = Mobile.Hardware.newBuilder();
        hardware.setRam(2).setRom(16).setSize(5);
        builder.setHardware(hardware)
                .setBrand("Apple")
                .addSoftware("camera")
                .addSoftware("tecent")
                .addSoftware("browser")
                .addSoftware("player");
        String messageBody = builder.build().toString();
        System.out.println(messageBody);

    }

}

