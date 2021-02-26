package com.wangcy.edu.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * @ClassName UserClientHandler
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/23
 * @Version 1.0
 */
public class UserClientHandler extends ChannelInboundHandlerAdapter implements Callable {

    //定义成员变量
    private ChannelHandlerContext channelHandlerContext;//事件处理器上下文  存储handler信息 和写操作
    private String result;//记录服务器返回的数据
    private String param;//记录将要返回给服务器的数据
    //实现channelActive方法 客户端和服务器连接时改方法被调用

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //此方法用来初始化context
        this.channelHandlerContext = ctx;
    }

    //实现channelRead 当我们读到服务器数据 执行
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //将读到的服务器数据设置为成员变量的值
        this.result = msg.toString();
        notify();

    }


    //将客户端的数据写到服务器
    public synchronized  Object call() throws Exception {
        //给服务器写数据
        channelHandlerContext.writeAndFlush(param);
        wait();
        return result;
    }

    //设置参数方法
}
