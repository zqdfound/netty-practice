package com.netty.nettypractice.learning;

import org.junit.Test;

import java.nio.IntBuffer;

/**
 * 缓冲区
 * @author zhuangqingdian
 * @date 2021/5/31
 */
public class BufferTest {
    /*使用Java NIO Buffer类的基本步骤如下：
            （1）使用创建子类实例对象的allocate()方法创建一个Buffer类的实例对象。
            （2）调用put()方法将数据写入缓冲区中。
            （3）写入完成后，在开始读取数据前调用Buffer.flip()方法，将缓冲区转换为读模式。
            （4）调用get()方法，可以从缓冲区中读取数据。
            （5）读取完成后，调用Buffer.clear()方法或Buffer.compact()方法，将缓冲区转换为写模式，可以继续写入。*/
    static IntBuffer intBuffer = null;

    public void showFlie(IntBuffer intBuffer){
        System.out.println("position:"+ intBuffer.position());
        System.out.println("limit:"+ intBuffer.limit());
        System.out.println("capacity:"+ intBuffer.capacity());
    }

    //allocate 设置容量
    @Test
    public void allocate(){
        intBuffer = IntBuffer.allocate(1024);
        showFlie(intBuffer);
    }
    //put 添加元素,get 获取元素
    @Test
    public void put(){
        intBuffer = IntBuffer.allocate(1024);
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        showFlie(intBuffer);
        intBuffer.flip();
        for (int i = 0; i < 2; i++) {
            int j = intBuffer.get();
            System.out.println("get ele = "+ j);
        }
        showFlie(intBuffer);
    }
    //flip反转缓冲区
    @Test
    public void flip(){
        intBuffer = IntBuffer.allocate(1024);
        intBuffer.flip();
        showFlie(intBuffer);
    }

}
