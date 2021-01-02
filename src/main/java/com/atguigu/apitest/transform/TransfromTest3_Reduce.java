package com.atguigu.apitest.transform;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author:yaoshuai.1024
 * @date: 2020/12/24 9:52 上午
 */
public class TransfromTest3_Reduce {



    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设施全局并行度为一
        env.setParallelism(1);
        //读取文件
        //从文件中读取数据
        String path = "src/main/resources/hello.txt";
        //流处理的重要api为DataStream
        DataStream<String> inputStream = env.readTextFile(path);


        /*
        * new MapFunction<String, SensorReading>() {
            @Override
            public SensorReading map(String s) throws Exception {
                return new SensorReading("hahhaha","21",12.0);

            }
        }*/


        // 转化类型
        DataStream<SensorReading> dataStream = inputStream.map(line -> {
            return new SensorReading(line, String.valueOf(Math.random()), Math.random());

        });


        //分组
        KeyedStream<SensorReading, Tuple> keyedStream = dataStream.keyBy("id");

        //reduce聚合,取当前最大的温度值，以及最新的时间
        DataStream<SensorReading> reduce = keyedStream.reduce(new ReduceFunction<SensorReading>() {
            //sensorReading为之前所有的状态，t1则是最新来的数据
            @Override
            public SensorReading reduce(SensorReading sensorReading, SensorReading t1) throws Exception {
                //此处的处理就是：因为是以id分组的，所以所有的SensorReading的id都是一样的可以直接获取，而为了保持"时间"一直是最新的所以要获取t1的时间，最后的操作就是获取在原来的状态中的最大值和最新的最大值之间的更大的值。
                return new SensorReading(sensorReading.getId(), t1.getTimestamp(), Math.max(sensorReading.getTemperature(), t1.getTemperature()));
            }
        });

        reduce.print();

        env.execute();



    }


    }
