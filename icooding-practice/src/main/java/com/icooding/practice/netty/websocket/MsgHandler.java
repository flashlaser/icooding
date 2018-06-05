package com.icooding.practice.netty.websocket;

import com.icooding.practice.LoggerInterface;
import com.icooding.practice.netty.imsocket.User;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * project_name icooding-practice
 * class MsgHandler
 * date  2017/11/15
 * author ibm
 * version 1.0
 */
public class MsgHandler extends ChannelInboundHandlerAdapter implements LoggerInterface {
//    Logger logger = Logger.getLogger(getClass().getName());
    protected static Map<String,User> connections = new HashMap<String,User>();


    public Object getUserNames(){
        return connections.values();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user = UserManager.getUser();
        user.setChannel(ctx.channel());
        for (Map.Entry<String, User> stringUserEntry : connections.entrySet()) {
            Channel channel = stringUserEntry.getValue().getChannel();
            channel.writeAndFlush(String.format("系统消息: %s 加入聊天室.\r\n",user.getName()));
        }
        ctx.writeAndFlush("尊敬的:"+user.getName()+" 欢迎你!\r\n");
        ctx.writeAndFlush("当前聊天室在线的成员有:"+getUserNames()+"\r\n");
        logger.log(Level.INFO,"用户："+user.getName()+"已进入聊天室。");
        connections.put(ctx.channel().id().asLongText(),user);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        User user = connections.get(ctx.channel().id().asLongText());
        logger.log(Level.INFO,user.getName()+":"+msg);
        for (Map.Entry<String, User> stringUserEntry : connections.entrySet()) {
            Channel channel = stringUserEntry.getValue().getChannel();
            if(stringUserEntry.getKey().equals(ctx.channel().id().asLongText())){
                channel.writeAndFlush(String.format("[我]: %s .\r\n",msg));
            }else{
                channel.writeAndFlush(String.format("[%s]: %s.\r\n",user.getName(),msg));
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        User user = connections.get(ctx.channel().id().asLongText());
        connections.remove(ctx.channel().id().asLongText());
        logger.log(Level.INFO,"用户："+user.getName()+"已退出聊天室。");
        for (Map.Entry<String, User> stringUserEntry : connections.entrySet()) {
            Channel channel = stringUserEntry.getValue().getChannel();
            channel.writeAndFlush(String.format("系统消息: %s 已退出聊天室.\r\n",user.getName()));
        }
    }


}
