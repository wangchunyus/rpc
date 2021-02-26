package com.wangcy.edu.handler;

import com.wangcy.edu.UserServiceImpl;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @ClassName UserServiceHandler
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/23
 * @Version 1.0
 */
public class UserServiceHandler extends ChannelInboundHandlerAdapter {

    //当客户端读取数据时 该方法呗调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //判断当前的请求是否符合规则 "客户端将来发送请求时会传递一个参数 ： UserService#sayHello#are you ok
        if(msg.toString().startsWith("UserService")){
            UserServiceImpl userService = new UserServiceImpl();
            String sayHello = userService.sayHello(msg.toString().substring(msg.toString().lastIndexOf("#")+1));
            ctx.writeAndFlush(sayHello);
        }


        //如果符合规则 就调用实现类方法获取结果


        //调用实现类获得的结果写到客户端
    }
}
