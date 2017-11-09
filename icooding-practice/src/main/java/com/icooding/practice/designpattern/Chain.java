package com.icooding.practice.designpattern;

import java.util.*;

/**
 * project_name icooding-practice
 * class Chain
 * date  2017/11/8
 * author ibm
 * version 1.0
 */
public class Chain {

    public static void main(String[] args) {
        HandlerManager handlerManager = new HandlerManager();
        ArrayList<Handler> costomHandlers = new ArrayList<>();
        costomHandlers.add(new CostomHandler(0,100,"组长"));
        costomHandlers.add(new CostomHandler(1001,5000,"经理"));
        costomHandlers.add(new CostomHandler(5001,10000,"总经理"));
        costomHandlers.add(new CostomHandler(101,1000,"部长"));
        handlerManager.addHandlers(costomHandlers);

        System.out.println(handlerManager.handler("Juck", 3200));

    }



}


abstract class Handler implements Comparable<Handler>{
    public int min;
    public int max;

    public Handler nextHandler;//下一个处理对象
    public abstract String handler(String usr,int money);

    @Override
    public int compareTo(Handler o) {
        return this.max - o.max;
    }
}

class CostomHandler extends Handler{
    public String name;

    public CostomHandler(int min, int max, String name) {
        this.min = min;
        this.max = max;
        this.name = name;
    }

    @Override
    public String handler(String usr,int money) {
        if(min < money &&  money < max){
            return  String.format("%s 处理了 %s 申请的 %s 钱!",name,usr,money);
        }else{
            System.out.println("["+name+"] 无法处理["+usr+"] 申请的["+money+"]");
            return nextHandler == null ? "无法处理你的申请" :nextHandler.handler(usr,money);
        }
    }
}


class HandlerManager extends Handler{
    Handler handler;
    public void addHandlers(List<Handler> handlers){
        Collections.sort(handlers);//排序

        //组成链关系
        int size = handlers.size();
        for (int i = 0; i < handlers.size(); i++) {
            int nextIndex = i+1;
            if(nextIndex <= (size-1)){
                handlers.get(i).nextHandler = handlers.get(nextIndex);
            }
        }
        this.handler = handlers.get(0);

    }


    @Override
    public String handler(String usr, int money) {
        return handler.handler(usr,money);
    }
}
