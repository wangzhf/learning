package com.wangzhf.thread;

import java.util.concurrent.TimeUnit;

/**
 * 测试变量在没有volatile修饰时，会发生指令重排序问题
 * 在1.8下未测试出指令重排问题
 */
public class InstructionRebuild {

    private static boolean stop;

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(!stop){
                    i++;
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println("work: " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        worker.start();
        TimeUnit.SECONDS.sleep(3);
        stop = true;
    }
}
