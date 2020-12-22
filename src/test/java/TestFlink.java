import com.atguigu.wc.StreamWordCount;
import org.apache.flink.api.common.JobExecutionResult;
import org.junit.Test;

/**
 * @author:yaoshuai
 * @date: 2020/12/17 9:14 下午
 */
public class TestFlink {

    @Test
    public void testWordCount(){
        StreamWordCount streamWordCount = new StreamWordCount();
        streamWordCount.wordCount();
        System.out.println();

    }


}
