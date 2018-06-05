package com.icooding.practice.netty.imsocket.server;

import com.icooding.practice.netty.imsocket.User;
import io.netty.channel.Channel;

import java.util.Date;
import java.util.Map;
import java.util.TimerTask;

/**
 * project_name icooding-practice
 * class MsgSendTask
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class MsgSendTask extends TimerTask {
    @Override
    public void run() {
//        for (Map.Entry<String, User> stringUserEntry : MsgHandler.connections.entrySet()) {
//            Channel channel = stringUserEntry.getValue().getChannel();
//            channel.writeAndFlush(String.format("系统时间: %s \r\n",new Date()));
//        }
    }
}
