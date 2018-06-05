package com.icooding.practice.netty.imsocket;

import io.netty.channel.Channel;

/**
 * project_name icooding-practice
 * class User
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class User {
    private Channel channel;
    private String name;
    private String id;

    public User(Channel channel,  String id,String name) {
        this.channel = channel;
        this.name = name;
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
