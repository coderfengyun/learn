/**
 * @(#)CpuCacheL1L2.java, Nov 19, 2015.
 * <p>
 * Copyright 2015 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.tcse.algorithms.learn.cpucache;

/**
 * @author chentienan
 */
public class CpuCacheL1L2 {
    static int h = 1024 * 1024;
    static  int w = 64;
    static int[][] m = new int[h][w];

    static {
        //assign value along column
        for (int i=0; i < h ; i ++){
            for (int j = 0; j < w; j++){
                m[i][j] = i + j;
            }
        }
    }

    public static void main(String[] args){
        long sum = 0;
        long start = System.currentTimeMillis();

        //sum vale along column
//        for (int i = 0; i < h; i++){
//            for (int j = 0; j < w; j ++){
//                sum += m[i][j];
//            }
//        }
        // 113ms

        for (int j = 0; j < w; j ++){
            for (int i = 0; i < h; i ++){
                sum += m[i][j];
            }
        }
        //2338ms

        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}