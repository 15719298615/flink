package com.atguigu.wc;
/*
* 使用flink的批处理api dataset处理一个文件，进行离线分析单词的个数
*
* */

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.AggregateOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

//批处理word count
public class WordCount {
    public static void main(String[] args) throws Exception {
        //创建批处理执行文件
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //从文件中读取数据
        String path = "src/main/resources/hello.txt";
        DataSet<String> inputDataSet = env.readTextFile(path);
        //对数据集进行处理，按照空格进行分词展开，转换成（word，1）这样的二元组
        DataSet<Tuple2<String, Integer>> resultSet = inputDataSet.flatMap(new MyFlatMapper())
                .groupBy(0)     //按照第一个位置的word进行分组
                .sum(1);//按照第二个位置上的数据求和
        resultSet.print();

    }


    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String,Integer>> {

        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            //按空格进行分词
            String[] words = s.split(" ");
            for (String word:words) {
                collector.collect(new Tuple2<>(word,1));
            }

        }
    }

}
