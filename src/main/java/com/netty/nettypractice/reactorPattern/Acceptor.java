package com.netty.nettypractice.reactorPattern;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * @author zhuangqingdian
 * @date 2021/5/19
 */
public class Acceptor implements Runnable{

    private Reactor reactor;
    public Acceptor(Reactor reactor){
        this.reactor = reactor;
    }

    @Override
    public void run() {
        try {
            SocketChannel socketChannel = reactor.serverSocketChannel.accept();
            if(socketChannel != null){
                //调用handler处理channel
                new SocketReadHandler(reactor.selector,socketChannel);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
