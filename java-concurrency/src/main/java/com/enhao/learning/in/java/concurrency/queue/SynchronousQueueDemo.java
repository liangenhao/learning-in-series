package com.enhao.learning.in.java.concurrency.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @author enhao
 */
public class SynchronousQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        // 并未插入成功，SynchronousQueue只能使用 put
        queue.offer(1);

        System.out.println(queue.take()); // 由于offer并未成功，所以 take 会处于阻塞状态
    }
}
