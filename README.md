![GitHub stars](https://img.shields.io/badge/%E7%82%B9%E4%B8%AA-star-green)


# flinkStudy

flink学习过程中的笔记   
head - 572378dce0ee37dbef428205fe34e67337b6b603为新增的jacoco测试覆盖率代码的使用。

# jacoco测试覆盖率的使用

#### 1. 背景：

因为需要对一个新flink项目进行单测覆盖率的统计，因为是java项目，所以选择了现在业界较为成熟的jacoco

#### 2. 使用：  

      1. 首先在最外面的pom文件中加入相应的plugn（使用的是on-the-fly模式）       


    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>compile</scope>
    </dependency>
    
    <plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.7.1.201405082137</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
    </plugin>


2.  接下来在src/test中写相应的单测代码，将被测代码放在src/main下（路径不要放错，不然会抱找不到单测代码无法产出覆盖率的报告）
    maven clean package 然后就可以在target/site/index.html中有报告生成

3.  就可以查看覆盖率



# flink项目以kafka为数据源配置

1. 首先加入所需的maven依赖

   ```
   <dependency>
       <groupId>org.apache.flink</groupId>
       <artifactId>flink-connector-kafka-0.11_2.12</artifactId>
       <version>1.10.1</version>
   </dependency>
   ```

2. ```java
   贴一个简单的demo
   import com.atguigu.apitest.beans.SensorReading;
   import org.apache.flink.api.common.serialization.SimpleStringSchema;
   import org.apache.flink.streaming.api.datastream.DataStreamSource;
   import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
   import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
   
   import java.util.Arrays;
   import java.util.Properties;
   
   /**
    * @email yaoshuai.1024
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
   
   ```

   

3. kafka设置

   1. 首先进入kafka安装路径（我的：/Users/App/kafka）
   2. 启动kafka

   ```
   bin/zookeeper-server-start.sh config/zookeeper.properties
   
   bin/kafka-server-start.sh config/server.properties
   ```

   3. 创建topic

   ```
   App/kafka/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test_attributor
   ```

   4. 推送数据（也可以以文件形式推送cat/echo等等）

   ```
   bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test_attributor
   ```

   # keyBy算子

   map

   flatmap

   filter

   以上三个被称为基本转换算子 

   DataStream->KeyByStream:逻辑将一个流拆分成不相交的分区，每个分区包含具有key相等的元素，在内部以hash值形式存在

   keyby之后就可以进行滚动聚合算子（这个就是在分组之后的基础上）：min，max，minby，maxby，sum

```java
demo
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

```

## select与split(及connect，comap)的使用

**split**

DataStream->SplitStream:根据某个特征把一个datastream拆分成两个或多个datastream

真正的做法：**这个拆分并不是一个真的拆分成两个流**，而是把这条流里的数据根据某一个字段打上不同的标志，然后把这个流里的所有数据根据这个打上的字段来进行区分

**slect**

SplitStream->DataStream:从一个SplitStream中获取一个或多个datastream。

在split之后一定要调用select方法才是一个完整的分流操作，调用select方法根据那个标志来进行分流。

```java
demo
SplitStream<SensorReading> splitStream = dataStream.split(new 
OutputSelector<SensorReading>() {
@Override
public Iterable<String> select(SensorReading value) {
return (value.getTemperature() > 30) ? Collections.singletonList("high") : 
Collections.singletonList("low");
}
});
DataStream<SensorReading> highTempStream = splitStream.select("high");
DataStream<SensorReading> lowTempStream = splitStream.select("low");
DataStream<SensorReading> allTempStream = splitStream.select("high", "low");
```

有了select月split的拆流操作，那么就肯定存在与之相反的连接操作

##  Connect与comap，coflatmap

**DataStream,DataStream** **→** **ConnectedStreams**：连接两个保持他们类型的数据流，两个数据流被 Connect 之后，只是被放在了一个同一个流中，内部依然保持各自的数据和形式不发生任何变化，两个流相互独立。

**ConnectedStreams → DataStream**：作用于 ConnectedStreams 上，功能与 map和 flatMap 一样，对 ConnectedStreams 中的每一个 Stream 分别进行 map 和 flatMap处理。

```java
demo
// 合流 connect
DataStream<Tuple2<String, Double>> warningStream = highTempStream.map(new 
MapFunction<SensorReading, Tuple2<String, Double>>() {
@Override
public Tuple2<String, Double> map(SensorReading value) throws Exception {
return new Tuple2<>(value.getId(), value.getTemperature());
}
});
ConnectedStreams<Tuple2<String, Double>, SensorReading> connectedStreams = 
warningStream.connect(lowTempStream);
DataStream<Object> resultStream = connectedStreams.map(new 
CoMapFunction<Tuple2<String,Double>, SensorReading, Object>() {
@Override
public Object map1(Tuple2<String, Double> value) throws Exception {
return new Tuple3<>(value.f0, value.f1, "warning");
}
@Override
public Object map2(SensorReading value) throws Exception {
return new Tuple2<>(value.getId(), "healthy");
}
});
```

## union

使用comap，coflatmap看起来非常方便，但是有一个缺陷，就是只可以合并两条流。但是有一个额外要求必须合并相同的两条流。

DataStream → DataStream：对两个或者两个以上的 DataStream 进行 union 操作，产生一个包含所有 DataStream 元素的新 DataStream。

```java
DataStream<SensorReading> unionStream = highTempStream.union(lowTempStream);
```

