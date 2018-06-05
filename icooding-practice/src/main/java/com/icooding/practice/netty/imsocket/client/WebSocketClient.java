package com.icooding.practice.netty.imsocket.client;

import com.icooding.practice.LoggerInterface;
import com.icooding.practice.netty.imsocket.server.ServerChannelHandler;
import com.icooding.practice.netty.imsocket.server.WebSocketServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * project_name icooding-practice
 * class WebSocketClient
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class WebSocketClient implements LoggerInterface{
    public void connetion(String host,int port){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        try {
           Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boss);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.handler(new WebSocketClientHandler());
            ChannelFuture connect = bootstrap.connect(host,port).sync();
            if(connect.isSuccess()){
                logger.log(Level.INFO,"连接端口："+port);
                logger.log(Level.INFO,"连接服务器成功!");
            }
            Channel channel = connect.channel();
            channel.writeAndFlush("我来啦\r\n");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Boolean flag = true;
            while(flag){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        }
    }

    public static void main(String[] args) {
        WebSocketClient webSocketServer = new WebSocketClient();
        webSocketServer.connetion("127.0.0.1",3100);
    }
}
