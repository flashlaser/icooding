package com.icooding.practice.netty.websocket;

import com.icooding.practice.netty.imsocket.User;

import java.util.ArrayList;
import java.util.List;

/**
 * project_name icooding-practice
 * class UserManager
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class UserManager {
    static List<User> users = new ArrayList<>();
    static{
        users.add(new User(null,"1","张三"));
        users.add(new User(null,"2","李四"));
        users.add(new User(null,"3","王五"));
        users.add(new User(null,"4","赵六"));
        users.add(new User(null,"5","陈七"));
    }

    static  Integer index = 0;

    public static User getUser(){
        synchronized (index){
            User user = users.get(index);
            index ++;
            return user;
        }
    }



}
