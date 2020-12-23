package com.atguigu.wc;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSink;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author:yaoshuai
 * @date: 2020/12/6 10:22 上午
 */
public class StreamWordCount {

    public static void main(String[] args) {
        StreamWordCount streamWordCount = new StreamWordCount();
        streamWordCount.wordCount();
    }


    public void wordCount() {
        //创建流处理执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //从文件中读取数据
        String path = "src/main/resources/hello.txt";
        //流处理的重要api为DataStream
        DataStream<String> inputDataStream = env.readTextFile(path);

        //用parameter tool工具从程序启动参数中提取配置项
//        ParameterTool parameterTool = ParameterTool.fromArgs(args);
//        String host = parameterTool.get("host");
//        int port = parameterTool.getInt("port");


        //从socket文本流读取数据
//        DataStream<String> inputDataStream = env.socketTextStream(host,port);


        //基于数据流进行转换计算,可以通过设置solt共享组slotSharingGroup("red")，当为同一个组的时候就可以共享同一个solt槽位，当不设置时默认和上一个一样。
        //第一个不设置就为defalut共享组
        SingleOutputStreamOperator<Tuple2<String, Integer>> resultStream = inputDataStream.flatMap(new WordCount.MyFlatMapper())
                .keyBy(0)
                .sum(1);

        DataStreamSink<Tuple2<String, Integer>> print = resultStream.print();

//        System.out.println(resultStream.print());
        try {
            //执行任务
            env.execute();
        }catch (Exception E){
            System.out.println("芜湖，抛出异常，芜湖胡！！！");
        }
        /*
        * 运行结果，前面的数字就代表了是哪一个线程处理了，还可以通过        env.setParallelism()设置并行数
        * 5> (world,1)
        8> (and,1)
        3> (hello,1)
        5> (you,1)
        1> (scala,1)
        3> (hello,2)
        4> (are,1)
        5> (you,2)
        6> (how,1)
        3> (hello,3)
        1> (spark,1)
        3> (thank,1)
        5> (fine,1)
        5> (you,3)
        7> (flink,1)
        3> (hello,4)
        * */


    }

}
