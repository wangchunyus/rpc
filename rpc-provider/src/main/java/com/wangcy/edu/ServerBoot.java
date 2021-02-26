package com.wangcy.edu;

/**
 * @ClassName ServerBoot
 * @Description TODO
 * @Author wcy
 * @Date 2021/2/23
 * @Version 1.0
 */
public class ServerBoot {

    public static void main(String[] args) throws InterruptedException {

        UserServiceImpl.startServer("127.0.0.1",8999);
    }
}
