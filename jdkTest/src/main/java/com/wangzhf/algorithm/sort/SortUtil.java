package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SortUtil {

    private static Logger logger = LoggerFactory.getLogger(SortUtil.class);

    /**
     * 利用两数相加交换（i=j时失效）
     * @param arr
     * @param i
     * @param j
     */
    public static void swap(int[] arr, int i, int j){
        if(i == j){
            swap3(arr, i, j);
            return ;
        }
        //logger.debug("Before: arr[i] = " + arr[i] + ", arr[j] = " + arr[j]);
        arr[i] = arr[i] + arr[j];
        arr[j] = arr[i] - arr[j];
        arr[i] = arr[i] - arr[j];
        //logger.debug("After: arr[i] = " + arr[i] + ", arr[j] = " + arr[j]);
    }

    /**
     * 利用一个数异或同一数字两次不变的原理实现交换（i=j时失效）
     * @param arr
     * @param i
     * @param j
     */
    public static void swap2(int[] arr, int i, int j){
        if(i == j){
            swap3(arr, i, j);
            return ;
        }
        //logger.debug("Before: arr[i] = " + arr[i] + ", arr[j] = " + arr[j]);
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
        //logger.debug("After: arr[i] = " + arr[i] + ", arr[j] = " + arr[j]);
    }

    /**
     * 借助临时变量交换
     * @param arr
     * @param i
     * @param j
     */
    public static void swap3(int[] arr, int i, int j){
        //logger.debug("Before: arr[i] = " + arr[i] + ", arr[j] = " + arr[j]);
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        //logger.debug("After: arr[i] = " + arr[i] + ", arr[j] = " + arr[j]);
    }

    public static void main(String[] args) {
        swap(new int[]{3, 3}, 0, 0);
    }
}
