package com.netty.nettypractice.pool;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author zhuangqingdian
 * @date 2021/5/21
 */
public class CreateThreadPoolDemo {

    @Test
    public void testSubmit(){
        ExecutorService pool = Executors.newCachedThreadPool();
        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                return (int)(Math.random()*10);
            }
        });

        try {
            for (int i = 0; i < 3; i++) {
                Integer result = future.get();
                System.out.println(Thread.currentThread().getName()+"-异步执行结果 result="+result);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        pool.shutdown();
    }
    @Test
    public void testSubmit1() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return (int)(Math.random()*10);
            }
        };

        for (int i = 0; i < 3; i++) {
            Future<Integer> future = pool.submit(callable);
            Integer result = future.get();
            System.out.println(Thread.currentThread().getName()+"-异步执行结果 result="+result);
        }
    }
    //单核心线程阻塞
    @Test
    public void testThreadPoolExecutor() throws InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1,100,100,TimeUnit.SECONDS,new LinkedBlockingDeque<>(100));
        for (int i = 0; i < 5; i++) {
            final int index = i;
            executor.execute(()->{
                System.out.println("index:"+ index);
                try {
                    Thread.sleep(Long.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        while(true){
            System.out.println("activeCount:" + executor.getActiveCount()+"-taskCount:"+executor.getTaskCount());
            Thread.sleep(1000);
        }
    }


}
