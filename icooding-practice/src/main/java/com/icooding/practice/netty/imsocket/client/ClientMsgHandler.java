package com.icooding.practice.netty.imsocket.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * project_name icooding-practice
 * class ClientMsgHandler
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class ClientMsgHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

}
