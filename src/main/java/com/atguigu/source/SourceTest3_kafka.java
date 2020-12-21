package com.atguigu.source;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Arrays;
import java.util.Properties;

/**
 * @email yaoshuai.1024@bytedance.com
 * @author:yaoshuai
 * @date: 2020/12/21 10:02 下午
 */
public class SourceTest3_kafka {

    public static void main(String[] args) throws Exception {
        //创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //设置配置文件
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        //从集合中读取数据,传入相应的topic
        DataStreamSource<String> dataStreamSource = env.addSource(new FlinkKafkaConsumer011<String>("test_attributor",new SimpleStringSchema(),properties));


        //打印输出
        dataStreamSource.print();

        //执行
        env.execute();

    }

}
