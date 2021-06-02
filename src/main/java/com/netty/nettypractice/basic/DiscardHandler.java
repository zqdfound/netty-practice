package com.netty.nettypractice.basic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/**
 * @author zhuangqingdian
 * @date 2021/5/20
 */
public class DiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf in = (ByteBuf) msg;
        System.out.println("accept msg : ");
        try {
            while(in.isReadable()){
                System.out.println((char)in.readByte());
            }
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            ReferenceCountUtil.release(msg);
        }
    }
}
