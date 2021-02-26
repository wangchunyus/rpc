package com.wangcy.edu;

import com.wangcy.edu.handler.UserServiceHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.security.MessageDigest;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/23
 * @Version 1.0
 */
public class UserServiceImpl  implements IUserService {

     public String sayHello(String msg) {
        System.out.println("are you ok？ "+ msg);
        return "are you ok？ "+ msg;
    }


    public static  void startServer(String ip,int port) throws InterruptedException {

         //创建线程池对象
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workroup = new NioEventLoopGroup();

        //创建启动类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //配置启动引导对象
        serverBootstrap.group(bossGroup,workroup)
                //设置通道
        .channel(NioServerSocketChannel.class)
                //创建监听
        .childHandler(new ChannelInitializer<NioSocketChannel>() {
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                //获取pipeline
                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                //配置编码
                pipeline.addLast(new StringEncoder());
                pipeline.addLast(new StringDecoder());
                //将自定义的channelHandler 添加到通道中
                pipeline.addLast(new UserServiceHandler());
            }
        });

        //绑定端口
        ChannelFuture sync = serverBootstrap.bind(9999).sync();


    }



}
