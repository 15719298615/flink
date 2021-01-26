package com.atguigu.reflection;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.crypto.SecureUtil;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/25 4:58 下午
 */
public class reflection_test1 {
    private static Map<String, Class<?>> rpcMethodMap = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Set<Class<?>> classes = ClassUtil.scanPackageBySuper("com.atguigu.reflection", Base.class);
        for (Class<?> aclass:classes) {
            Method[] publicMethods = ClassUtil.getPublicMethods(aclass);
            Arrays.stream(publicMethods).forEach(e->rpcMethodMap.put(e.getName(),aclass));
        }
        System.out.println(rpcMethodMap);
        ClassUtil.invoke(rpcMethodMap.get("emmitt2").getName(),"emmitt2",true);


        String eeee = SecureUtil.md5("eeee");
        System.out.println(eeee);

    }
}
