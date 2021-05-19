package com.netty.nettypractice.reactorPattern;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 反应器模式
 * @author zhuangqingdian
 * @date 2021/5/19
 */
public class Reactor implements Runnable{

    public final Selector selector;
    public final ServerSocketChannel serverSocketChannel;

    public Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(InetAddress.getLocalHost(),port);
        serverSocketChannel.socket().bind(inetSocketAddress);
        serverSocketChannel.configureBlocking(false);
        //向selector注册channel
        SelectionKey selectionKey = serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        //利用selectionKey的attach绑定Accept 如果有事件，则触发Accptor
        selectionKey.attach(new Acceptor(this));
    }
    @Override
    public void run() {
        while(!Thread.interrupted()){
            try {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                //select如果监听到OP_ACCEPT或者READ事件发生，便会遍历key
                while(it.hasNext()){
                    //来一个事件 第一次触发一个accept线程 之后触发SocketReadHandler
                    SelectionKey key = it.next();
                    dispatch(key);
                    selectionKeys.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 运行Accept或SocketReadhandler
     * @param key
     */
    void dispatch(SelectionKey key){
        Runnable r = (Runnable) key.attachment();
        if(r != null){
            r.run();
        }
    }

}
