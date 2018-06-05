package com.icooding.practice.netty.websocket;

import com.icooding.practice.LoggerInterface;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Timer;
import java.util.logging.Level;

/**
 * project_name icooding-practice
 * class WebSocketServer
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class WebSocketServer implements LoggerInterface {
    public void start(int port){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,work);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG,1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE,true);
            bootstrap.childHandler(new ServerChannelHandler());
            ChannelFuture sync = bootstrap.bind(port).sync();
            if(sync.isSuccess()){
                logger.log(Level.INFO,"端口："+port);
                logger.log(Level.INFO,"服务器启动成功!");
            }
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    public static void main(String[] args) {

        WebSocketServer webSocketServer = new WebSocketServer();
        new Timer().schedule(new MsgSendTask(),1000,100000);
        webSocketServer.start(3100);

    }


}
