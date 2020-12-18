package com.atguigu.wc;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @email yaoshuai.1024@bytedance.com
 * @author:yaoshuai
 * @date: 2020/12/8 9:28 下午
 */
public class tt {

    public static void main(String[] args) {
        HashMap map = new HashMap(){{

           put(1, Arrays.asList(1,2,3,4));
           put(2,"2");
           put(3,"3");
        }};


        /*
        *
        * Arrays.asList(
                            new AttributionConfigItem(AttributionModelType.LAST_CLICK,
                                    new HashMap() {{
                                        put(PlatformType.IOS, Arrays.asList(IdentityType.UID));
                                        put(PlatformType.Android, Arrays.asList(IdentityType.UID));
                                        put(PlatformType.UnknownPlatform, Arrays.asList(IdentityType.UID));
                                    }}, TouchType.CLICK, BusinessType.LUBAN.getCode(), TIME_DAY,
                                    Arrays.asList(
                                            new Validator(ValidateField.TO_UID, ValidateOperation.EQUAL,null)
                                    ), AttributionResult.ATTRIBUTE, null
                            )
                    )
        *
        *
        *
        * */


        System.out.println(map);


    }
}
