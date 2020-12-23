package com.atguigu.apitest.transform;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author:yaoshuai.1024
 * @date: 2020/12/23 9:49 上午
 */
public class TransformTest2_RollingAggregation {


    public static void main(String[] args) throws Exception{
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
        DataStream<SensorReading> dataStream = inputStream.map( line ->{
            return new SensorReading(line,"21",Math.random());

        } );


        //分组,以id为分组进行hash
        KeyedStream<SensorReading, Tuple> keyedStream = dataStream.keyBy("id");


//        KeyedStream<SensorReading, String> keyedStream1 = dataStream.keyBy(SensorReading::getId);



        //滚动聚合，取之前以id为分组之后的和，也就是同一个id的话就把他们的temperature加起来
        SingleOutputStreamOperator<SensorReading> id = keyedStream.sum("temperature");


        id.print();
        System.out.println("************************");
        env.execute();



    }

}
