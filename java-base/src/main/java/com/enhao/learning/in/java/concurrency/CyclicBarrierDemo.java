package com.enhao.learning.in.java.concurrency;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier：一组线程互相等待，当全部线程到达公共屏障点，才会进行后续任务
 * 注意：CyclicBarrier可以被重置后使用
 *
 * @author enhao
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        // 方式一：构造函数：CyclicBarrier(int)
        // cyclicBarrierWithoutBarrierAction();
        // 方式二：构造函数：CyclicBarrier(int， Runnable)
        cyclicBarrierWithBarrierAction();
    }

    private static void cyclicBarrierWithoutBarrierAction() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8);

        for (int i = 0; i < 8; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    Thread.sleep(finalI * 1000L);
                    System.out.println(Thread.currentThread().getName() + " 准备就绪");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }

                System.out.println("开始比赛");
            }).start();
        }
    }

    private static void cyclicBarrierWithBarrierAction() {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(8, () -> {
            System.out.println("开始比赛");
        });

        for (int i = 0; i < 8; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    Thread.sleep(finalI * 1000L);
                    System.out.println(Thread.currentThread().getName() + " 准备就绪");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
