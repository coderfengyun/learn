/**
 * @(#)L3Cache.java, Nov 19, 2015.
 * <p>
 * Copyright 2015 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tcse.algorithms.learn.cpucache;

/**
 * @author chentienan
 */
public class L3Cache {
    static class VolatileLong {
        volatile long value;
    }

    private static class Task extends Thread {
        private final VolatileLong volatileLong;

        public Task(VolatileLong volatileLong) {
            this.volatileLong = volatileLong;
        }

        @Override
        public void run() {
            //直接访问L3 cache 512 * 1024 * 1024次
            long iter = 512 * 1024 * 1024;
            for (long i = 0; i < iter; i++) {
                volatileLong.value = i;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        //this threadCount should be typical to your machine
        int threadCount = 4;
        VolatileLong[] values = new VolatileLong[threadCount];

        for (int i = 0; i < threadCount; i++) {
            values[i] = new VolatileLong();
        }

        Task[] tasks = new Task[threadCount];
        for (int i = 0; i < threadCount; i++) {
            tasks[i] = new Task(values[i]);
        }
        for (int i = 0; i < threadCount; i++) {
            tasks[i].start();
        }

        for (int i = 0; i < threadCount; i++) {
            tasks[i].join();
        }
        //18337 ms

        System.out.println(System.currentTimeMillis() - start);
    }
}