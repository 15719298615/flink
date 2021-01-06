package com.atguigu.wc;

import com.sun.tools.internal.xjc.Language;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntBinaryOperator;
import java.util.function.Predicate;

/**
 * @author:yaoshuai
 * @date: 2020/12/8 9:28 下午
 */
public class tt {

    public static void main(String[] args) {


//        String s = "";
//        System.out.println(s.isEmpty());
//
//
//        HashMap map = new HashMap(){{
//
//           put(1, Arrays.asList(1,2,3,4));
//           put(2,"2");
//           put(3,"3");
//        }};

//        long a = Long.valueOf("");
//        int addition = () -> 5;
//
//        int x = int a -> return a+1
//        System.out.println();
//        for (int i=1;i<10;i++){
//            switch (i){
//                case 1:
//                    System.out.println(1);
//                    return;
//                case 2:
//                    System.out.println(2);
//                case 3:
//                    System.out.println(3);
//
//            }
//
//        }


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


//        System.out.println(map);

        ArrayList<Integer> list = new ArrayList<>();

        int i = 5;
        Collections.addAll(list, 1,2,3,4);

        //lambda表达式 方法引用
//        list.forEach(System.out::println);
//
//        list.forEach(element -> {
//            if (element % 2 == 0) {
//                System.out.println(element);
//            }
//        });

        DoubleToIntFunction doubleToIntFunction = a -> {
            return (int) (a+1);
        };

        Consumer tConsumer = e -> System.out.println(e);
//        tConsumer.accept(1);
        Predicate<Integer> k = a -> a == 1;

        System.out.println(doubleToIntFunction.applyAsInt(2));
//        Runnable runnable = () -> a();

//        runnable.run();

        list.stream().filter(k).forEach(a->a());
    }

    public static void a(){
        System.out.println("1");
    }

}
