package com.icooding.practice.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.UnsupportedEncodingException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * project_name icooding-practice
 * class DiscardServer
 * date  2017/11/7
 * author ibm
 * version 1.0
 */
public class DiscardServer {


    public void run(){
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelHanddler());
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture sync = bootstrap.bind(8080).sync();
            if(sync.isSuccess()){
                System.out.println("启动成功 端口:"+8080);
            }
            sync.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new DiscardServer().run();
    }
}


class ChannelHanddler extends ChannelInitializer<SocketChannel>{
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new DiscardServerHandler());
    }
}

class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)


    public DiscardServerHandler() {

    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelRegistered");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)
        // 以静默方式丢弃接收的数据
        try {
            ByteBuf msg1 = (ByteBuf) msg;// (3)
            byte[] bytes = new byte[msg1.readableBytes()];
            msg1.readBytes(bytes);
            String msgstr = new String(bytes,"UTF-8");
            System.out.println(msg);
            ctx.channel().writeAndFlush("Server 回复的消息");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)
        // 出现异常时关闭连接。
        cause.printStackTrace();
        ctx.close();
    }
}