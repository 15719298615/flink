package com.atguigu.apitest.sink;

import com.atguigu.apitest.beans.SensorReading;
import org.apache.flink.api.common.functions.RuntimeContext;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.elasticsearch.ElasticsearchSinkFunction;
import org.apache.flink.streaming.connectors.elasticsearch.RequestIndexer;
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.Requests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/3 10:32 上午
 */
public class SinkTest3_Es {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        //读取文件
        //从文件中读取数据
        String path = "src/main/resources/hello.txt";
        //流处理的重要api为DataStream
        DataStreamSource<SensorReading> dataStreamSource = env.fromCollection(
                Arrays.asList(
                        new SensorReading("s1", "123L", 12.3),
                        new SensorReading("s2", "234L", 13.4)
                )
        );
        // es 的 httpHosts 配置
        List<HttpHost> httpHosts = new ArrayList<>();
        httpHosts.add(new HttpHost("localhost", 9200));

        dataStreamSource.addSink( new ElasticsearchSink.Builder<SensorReading>(httpHosts, new MyEsSinkFunction()).build() );


    }

    public static class MyEsSinkFunction implements ElasticsearchSinkFunction<SensorReading> {
        @Override
        public void process(SensorReading element, RuntimeContext ctx, RequestIndexer
                indexer) {
            //定义写入的数据source
            HashMap<String, String> dataSource = new HashMap<>();
            dataSource.put("id", element.getId());
            dataSource.put("ts", element.getTimestamp().toString());
            dataSource.put("temp", element.getTemperature().toString());
            // 创建请求，像es发送请求，RequestIndexer是es提供的向es发送请求的api
            IndexRequest indexRequest = Requests.indexRequest()
                    .index("sensor")
                    .type("readingData")
                    .source(dataSource);
            //用将要发送的请求加入到indexer中进行发送请求
            indexer.add(indexRequest);
        }

//        @Override
//        public void process(SensorReading sensorReading, RuntimeContext runtimeContext, RequestIndexer requestIndexer) {
//            HashMap<String, String> dataSource = new HashMap<>();
//            dataSource.put("id", element.getId());
//            dataSource.put("ts", element.getTimestamp().toString());
//            dataSource.put("temp", element.getTemperature().toString());
//            IndexRequest indexRequest = Requests.indexRequest()
//                    .index("sensor")
//                    .type("readingData")
//                    .source(dataSource);
//            indexer.add(indexRequest);
//        }
    }

}
