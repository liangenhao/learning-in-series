package com.enhao.learning.in.java.concurrency.tool;

import java.util.concurrent.Semaphore;

/**
 * Semaphore 通常用于限制可以访问某些资源（物理或逻辑的）的线程数目。
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "开始执行");
                    Thread.sleep(5000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }).start();
        }
    }

}
