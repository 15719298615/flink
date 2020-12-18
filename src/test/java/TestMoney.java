import com.coverage.MoneyUtil;
import org.junit.Test;

/**
 * @email yaoshuai.1024@bytedance.com
 * @author:yaoshuai
 * @date: 2020/12/17 8:50 下午
 */
public class TestMoney {

    @Test
    public void testGetUnitMoney(){
//        MoneyUtil.getUnitMoney("50", 2);
        MoneyUtil.getUnitMoney("60", 2);
        MoneyUtil.getUnitMoney("70", 2);
        MoneyUtil.getUnitMoney("75", 2);

    }

}
