# flinkStudy
flink学习过程中的笔记   
head - 572378dce0ee37dbef428205fe34e67337b6b603为新增的jacoco测试覆盖率代码的使用。

jacoco测试覆盖率的使用
## 1. 背景：
因为需要对一个新flink项目进行单测覆盖率的统计，因为是java项目，所以选择了现在业界较为成熟的jacoco
## 2. 使用：  
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
maven clean package 即可有报告 在target文件中 site index.html

3. 就可以查看覆盖率
![image-20201218130655497](/Users/bytedance/Library/Application Support/typora-user-images/image-20201218130655497.png)