package com.netty.nettypractice.reactorPattern;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * @author zhuangqingdian
 * @date 2021/5/19
 */
public class SocketReadHandler implements Runnable{

    private SocketChannel socketChannel;
    public SocketReadHandler(Selector selector,SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        SelectionKey selectionKey = socketChannel.register(selector,0);
        //将SelectionKey绑定为本handler 下一次事件触发时 将调用本类的run方法
        selectionKey.attach(this);
        //将seletionKey标记为可读
        selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    /**
     * 处理读取数据
     */
    @Override
    public void run() {
        ByteBuffer inputBuffer = ByteBuffer.allocate(1024);
        inputBuffer.clear();
        try {
            socketChannel.read(inputBuffer);
            //处理handler

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
