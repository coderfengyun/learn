/**
 * @(#)L3CacheWithoutCompilerOptimization.java, Nov 19, 2015.
 * <p>
 * Copyright 2015 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tcse.algorithms.learn.cpucache;

/**
 * @author chentienan
 */
public class L3CacheWithoutCompilerOptimization {

    static class VolatileLong {
        public long p1, p2, p3, p4, p5, p6, p7;

        public static long fixCompilerOptimization(VolatileLong v) {
            return v.p1 + v.p2 + v.p3 + v.p4 + v.p5 + v.p6 + v.p7;
        }
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
                volatileLong.p1 = i + 7;
                volatileLong.p2 = i + 6;
                volatileLong.p3 = i + 5;
                volatileLong.p4 = i + 4;
                volatileLong.p5 = i + 3;
                volatileLong.p6 = i + 2;
                volatileLong.p7 = i + 1;
                VolatileLong.fixCompilerOptimization(volatileLong);
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
        //9856 ms
        System.out.println(System.currentTimeMillis() - start);
    }

}