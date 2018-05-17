package com.wangzhf.method;

public class Person {

    private String name = "wangzhf";

    public String getName(){
        return name;
    }

    protected String getName2(){
        return name;
    }

    private String getName3(){
        return name;
    }

    String getName4(){
        return name;
    }

    public static void main(String[] args) {
        Person p = new Person();
        p.getName2();
    }
}
