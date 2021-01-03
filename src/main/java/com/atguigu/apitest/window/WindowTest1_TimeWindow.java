package com.atguigu.apitest.window;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingAlignedProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

import java.util.Arrays;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/3 12:00 下午
 */
public class WindowTest1_TimeWindow {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStreamSource<SensorReading> dataStreamSource = env.fromCollection(
                Arrays.asList(
                        new SensorReading("s1", "123L", 12.3),
                        new SensorReading("s2", "234L", 13.4)
                )
        );

        //开窗测试
        dataStreamSource.keyBy("id")
                //滑动计数窗口
                .countWindow(10,2);
                //timeWindow有两中，一种是穿一个参就是创建滚动窗口，另一个是穿两个参数创建滑动窗口
//                .timeWindow(Time.seconds(15));
//                .window(TumblingProcessingTimeWindows.of(Time.seconds(15)))

        dataStreamSource.print();
        env.execute();


    }

}
