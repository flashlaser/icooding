package com.icooding.practice.netty.imsocket.client;

import com.icooding.practice.netty.imsocket.server.MsgHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * project_name icooding-practice
 * class WebSocketClientHandler
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class WebSocketClientHandler extends ChannelInitializer<SocketChannel> {

    public WebSocketClientHandler() {

    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 以("\n")为结尾分割的 解码器
        pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        // 字符串解码 和 编码
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("msgHandler",new ClientMsgHandler());
    }
}
