package com.atguigu.apitest.transform;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/2 4:23 下午
 *
 * 这个是一个自己定时设置重新分区的demo
 *
 */
public class TransFormTest6_Partition {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(4);
        //读取文件
        //从文件中读取数据
        String path = "src/main/resources/hello.txt";
        //流处理的重要api为DataStream
        DataStream<String> inputStream = env.readTextFile(path);


        inputStream.print("input");

        //shuffle
        DataStream<String> shuffleStream = inputStream.shuffle();

        shuffleStream.print("shuffle");


        //global,把所有的都设置在一个分区
        inputStream.global().print("global");

        env.execute();

    }
}
