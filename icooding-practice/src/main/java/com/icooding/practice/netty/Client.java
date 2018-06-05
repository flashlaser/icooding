package com.icooding.practice.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

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
            .channel(NioSocketChannel.class)
            .handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel channelHandler) throws Exception {
                    channelHandler.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                        @Override
                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                            byte[] bytes = "Hello word!".getBytes();
                            ByteBuf buffer = Unpooled.buffer(bytes.length);
                            buffer.writeBytes(bytes);
                            ctx.writeAndFlush(buffer);
                        }

                        @Override
                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                            ByteBuf msg1 = (ByteBuf) msg;// (3)
                            byte[] bytes = new byte[msg1.readableBytes()];
                            msg1.readBytes(bytes);
                            String msgstr = new String(bytes,"UTF-8");
                            System.out.println("channelRead:"+msgstr);
                            System.out.println("Object:"+msg);
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
