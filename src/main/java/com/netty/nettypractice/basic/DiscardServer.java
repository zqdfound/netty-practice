package com.netty.nettypractice.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author zhuangqingdian
 * @date 2021/5/19
 */
public class DiscardServer {
    private final int serverPort;
    ServerBootstrap b = new ServerBootstrap();
    public DiscardServer(int port){
        this.serverPort = port;
    }

    public void runServer(){
        //创建反应器线程组
        EventLoopGroup bossLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerLoopGroup = new NioEventLoopGroup();

        //设置反应器线程组
        b.group(bossLoopGroup,workerLoopGroup);
        //设置NIO类型通道
        b.channel(NioServerSocketChannel.class);
        //设置监听端口




    }


}
