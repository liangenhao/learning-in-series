package com.enhao.learning.in.java.concurrency.tool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch：在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待
 * 注意：CountDownLatch的计数器无法被重置
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        countDownLatchWithThreadPool();
    }

    /**
     * 使用线程的例子
     */
    private static void countDownLatchWithThread() {
        CountDownLatch countDownLatch = new CountDownLatch(8);
        new Thread(() -> {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("800米比赛结束，准备清空跑道并继续跨栏比赛");
        }).start();

        for (int i = 0; i < 8; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    Thread.sleep(finalI * 1000L);
                    System.out.println(Thread.currentThread().getName() + "到达终点");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }).start();
        }
    }

    /**
     * 使用线程池的例子
     */
    private static void countDownLatchWithThreadPool() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(5);

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            executorService.submit(() -> {
                try {
                    Thread.sleep(finalI * 1000L);
                    System.out.println(Thread.currentThread().getName() + "到达终点");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    countDownLatch.countDown();
                }

            });
        }

        // 等待完成
        countDownLatch.await();
        System.out.println("800米比赛结束，准备清空跑道并继续跨栏比赛");

        // 关闭线程池
        executorService.shutdown();
    }
}
