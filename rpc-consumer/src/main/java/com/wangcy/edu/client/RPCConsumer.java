package com.wangcy.edu.client;

import com.wangcy.edu.handler.UserClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName RPCConsumer
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/23
 * @Version 1.0
 */
public class RPCConsumer {

    //创建线城池  它要处理我们自定义事件
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //声明一个 自定义事件处理器  UserClientHandler
    private  static UserClientHandler userClientHandler;


    //编写方法 初始化客户端
    public static void initClient() throws InterruptedException {

        userClientHandler = new UserClientHandler();

        //创建连接池对象  groupLoop
        NioEventLoopGroup group = new NioEventLoopGroup();

        //创建引导类对象 客户端为Bootsrap  服务端为 ServerBootStrap
        Bootstrap bootstrap = new Bootstrap();

        //设置引导类对象
        bootstrap.group(group)
                //设置通道
        .channel(NioSocketChannel.class)
                //设置请求协议为 tcp
        .option(ChannelOption.TCP_NODELAY,true)
                //监听channel 并初始化
        .handler(new ChannelInitializer<SocketChannel>() {
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast(new StringEncoder());
                pipeline.addLast(new StringDecoder());
                //添加自定义事件处理器
                pipeline.addLast(userClientHandler);
            }
        });

        ChannelFuture future = bootstrap.connect("127.0.0.1", 8999).sync();

    }

    //使用jdk的动态代理创建对象
    public static Object createProxy(Class<?> serverClass,String providerParam){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{serverClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }




}
