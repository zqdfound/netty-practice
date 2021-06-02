package com.netty.nettypractice.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
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
        b.localAddress(serverPort);
        //设置通道参数
        b.option(ChannelOption.SO_KEEPALIVE,true);
        b.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        //装配子通道流水线
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            //有连接时会创建一个通道
            protected void initChannel(SocketChannel ch){
                //向子通道流水线添加一个handler处理器
                ch.pipeline().addLast(new DiscardHandler());
            }
        });
        //绑定服务器
        ChannelFuture channelFuture = null;
        try {
            channelFuture = b.bind().sync();
            System.out.println("server启动，监听端口："+channelFuture.channel().localAddress());
            //通道关闭异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            workerLoopGroup.shutdownGracefully();
            bossLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        int port = 8080;
        new DiscardServer(port).runServer();
    }

}
