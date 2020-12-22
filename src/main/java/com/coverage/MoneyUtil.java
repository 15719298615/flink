package com.coverage;
import java.math.BigDecimal;
/**
 * @author:yaoshuai
 * @date: 2020/12/17 8:48 下午
 */
public class MoneyUtil {



    /** * <pre> * 功能说明 : 安全转换double类型, 将单位元的钱转为分 * 如: 19.9(元), 最终结果: 1990(分) * </pre> * * @param money 金额(元) * @return */
    public static int getFenMoney(String money) {
        BigDecimal hundred = new BigDecimal(100);
        BigDecimal decimalMoney = new BigDecimal(money);
        return decimalMoney.multiply(hundred).intValue();
    }


    /** * 功能说明 : 获取单价, 保留小数点两位数 * * @param money 总金额(元) * @param quantity 数量 * @return */
    public static double getUnitMoney(String money, int quantity) {
        if ("50".equals(money)) {
            System.out.println("branch1");
        }else if ("60".equals(money)) {
            System.out.println("branch2");
        }else if ("70".equals(money)) {
            System.out.println("branch3");
        }else {
            System.out.println("other branch");
        }
        BigDecimal decimalMoney = new BigDecimal(money);
        BigDecimal unitMoney = decimalMoney.divide(new BigDecimal(quantity), 2, BigDecimal.ROUND_HALF_UP);

        return unitMoney.doubleValue();
    }

}
