package com.atguigu.apitest.sink;

/**
 * @author:yaoshuai.1024
 * @date: 2021/1/3 10:44 上午
 */
public class tt {

    public static void main(String[] args) {
        a a1 = new a("12");
        System.out.println(a1.a1());

    }

}

class a{
    String a;
    public a(String a){
        this.a = a;
    }

    public String a1(){
        return this.a;
    }

}