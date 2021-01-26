package com.atguigu.PbAndJson;

import com.atguigu.PbAndJson.*;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/26 1:32 下午
 */
public class PbToJson {

    public static void main(String[] args) {
        // protobuf 转 json
//        参考这个网址：https://my.oschina.net/u/1162561/blog/364772非常感谢
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

        byte[] bytes = builder.build().toByteArray();
        System.out.println(bytes);
        try {
            Mobile.MobilePhone mobilePhone = Mobile.MobilePhone.parseFrom(bytes);
            System.out.println("由byte转换成结构");
            System.out.println(mobilePhone);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }


    }

}

