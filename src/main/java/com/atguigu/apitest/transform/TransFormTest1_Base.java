package com.atguigu.apitest.transform;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author:yaoshuai
 * @date: 2020/12/22 10:07 上午
 */
public class TransFormTest1_Base {

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设施全局并行度为一
        env.setParallelism(1);
        //读取文件
        //从文件中读取数据
        String path = "src/main/resources/hello.txt";
        //流处理的重要api为DataStream
        DataStream<String> inputStream = env.readTextFile(path);
        //基本转换
        //1.map把string转换成长度输出
        DataStream<Integer> map = inputStream.map(new MapFunction<String, Integer>() {
            @Override
            public Integer map(String s) throws Exception {
                return s.length();
            }
        });

        //2.flatmap,按空格切分字段
        DataStream<String> flatMapString = inputStream.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                String[] fields = s.split(" ");
                for (String field: fields) {
                    collector.collect(field);
                }
            }
        });

        //3.filter,筛选数据
        DataStream<String> filterStream = inputStream.filter(new FilterFunction<String>() {
            @Override
            public boolean filter(String s) throws Exception {
                return s.contains(" ");
            }
        });

        //打印输出
        map.print("map");
        flatMapString.print("flatmap");
        filterStream.print("filter");

        env.execute();

    }

}
