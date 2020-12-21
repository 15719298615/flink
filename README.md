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

   

