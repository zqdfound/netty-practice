package com.netty.nettypractice.learning;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 通道
 *
 * @author zhuangqingdian
 * @date 2021/5/31
 */
@Slf4j
public class ChannelTest {

    //fileChannel文件靠北
    public static void fileChannelCopyDemo(String srcPath, String destPath) {
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fic = null;
        FileChannel foc = null;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (!srcFile.exists()) {
                throw new FileNotFoundException("源文件不存在");
            }
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            fic = fis.getChannel();
            foc = fos.getChannel();
            int length = -1;
            //新建buffer 处于写模式

            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //从通道读取到BUffer
            while ((length = fic.read(byteBuffer)) != -1) {
                //转化为读模式
                byteBuffer.flip();
                int outLength = 0;
                //将buffer写入输出的通道
                while ((outLength = foc.write(byteBuffer)) != 0) {
                    System.out.println("写入字节数：" + outLength);
                }
                //清除buffer 变成写模式
                byteBuffer.clear();
            }
            //强制刷新到磁盘
            foc.force(true);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                foc.close();
                fic.close();
                fos.close();
                fis.close();
                long endTime = System.currentTimeMillis();
                System.out.println("耗时：" + (endTime - currentTimeMillis));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        fileChannelCopyDemo("E:\\file\\1.txt","E:\\file\\2.txt");
    }

}
