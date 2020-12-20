package com.atguigu.source;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

/**
 * @email yaoshuai.1024@bytedance.com
 * @author:yaoshuai
 * @date: 2020/12/20 9:59 上午
 */
public class SourceTest1_collection {

    public static void main(String[] args) throws Exception {
        //创建执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        //从集合中读取数据,
        DataStreamSource<SensorReading> dataStreamSource = env.fromCollection(
                Arrays.asList(
                        new SensorReading("s1", "123L", 12.3),
                        new SensorReading("s2", "234L", 13.4)
                )
        );

        DataStreamSource<Integer> integerDataStreamSource = env.fromElements(1, 2, 3, 4);
        //打印输出
        dataStreamSource.print("data");
        integerDataStreamSource.print("int");

        //执行
        env.execute();

    }

}
