package com.icooding.practice.designpattern;

import java.util.HashMap;
import java.util.Map;

/**
 * project_name icooding-practice
 * class FlyweightTest
 * date  2017/11/8
 * author ibm
 * version 1.0
 */
public class FlyweightTest {

    public static void main(String[] args) {

    }
}

class FlyweightObject{
    public Object obj;
    public LifeCycle lifeCycle;

    public FlyweightObject(Object obj, LifeCycle lifeCycle) {
        this.obj = obj;
        this.lifeCycle = lifeCycle;
    }
}

enum LifeCycle{
    NEW,INSTENCE,DESTROY
}

interface Flyweight{
    void get();
}
class  CFlyweight implements Flyweight{
    @Override
    public void get() {
        System.out.println("get");
    }

}



class FlyweightFactory{
    private static Map<String,Flyweight> map = new HashMap<String, Flyweight>();
}

