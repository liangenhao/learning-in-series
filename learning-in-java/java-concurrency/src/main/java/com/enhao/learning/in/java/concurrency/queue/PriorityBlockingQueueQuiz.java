package com.enhao.learning.in.java.concurrency.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class PriorityBlockingQueueQuiz {

    public static void main(String[] args) throws Exception {
        BlockingQueue<Integer> queue = new PriorityBlockingQueue<>(2);
        // 考点：
        // 1. PriorityBlockingQueue put(Object) 方法不阻塞，put方法里调用offer方法
        // 2. PriorityBlockingQueue offer(Object) 方法不限制，可以使队列长度变长
        // 3. PriorityBlockingQueue 插入对象会做排序，默认参照元素 Comparable 实现，或者显示地传递 Comparator
        queue.put(9);
        queue.put(1);
        queue.put(8);
        System.out.println("queue.size() = " + queue.size());
        System.out.println("queue.take() = " + queue.take());
        System.out.println("queue = " + queue);
    }
}