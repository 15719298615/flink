package com.atguigu.apitest.sink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/2 4:31 下午
 */
public class SinkTest1_kafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //读取文件
        //从文件中读取数据
        String path = "src/main/resources/hello.txt";
        //流处理的重要api为DataStream
        DataStream<String> inputStream = env.readTextFile(path);


        //传入的参数分别为地址端口，topic，对相应的输出数据类型，这个类型是flink提供的
        DataStreamSink<String> stringDataStreamSink = inputStream.addSink(new FlinkKafkaProducer011<String>("localhost:9092",
                "test", new SimpleStringSchema()));


        env.execute();

    }
}
