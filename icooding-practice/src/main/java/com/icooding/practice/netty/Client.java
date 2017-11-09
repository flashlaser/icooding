package com.icooding.practice.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * project_name icooding-practice
 * class Client
 * date  2017/11/9
 * author ibm
 * version 1.0
 */
public class Client {

    public static void main(String[] args) {


        NioEventLoopGroup work = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(work)
            .channel(NioServerSocketChannel.class)
            .handler(new ChannelInitializer<NioServerSocketChannel>() {
                @Override
                protected void initChannel(NioServerSocketChannel channelHandler) throws Exception {
                    channelHandler.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            ctx.writeAndFlush("hello word!");
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            System.out.println("channelRead:"+msg);
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                            cause.printStackTrace();
                            ctx.close();
                        }
                    });
                }
            });
            ChannelFuture sync = bootstrap.connect("127.0.0.1", 8080).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            work.shutdownGracefully();
        }

    }
}
