package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 快速排序，属于内部交换排序
 *
 * 快速排序使用分治法来把一个串（list）分为两个子串（sub-lists）。具体算法描述如下：
 *      1. 从数列中挑出一个元素，称为 “基准”（pivot）；
 *      2. 重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。
 *          在这个分区退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
 *      3. 递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 *
 */
public class QuickSort {

    private static Logger logger = LoggerFactory.getLogger(QuickSort.class);

    static int[] arr = {55, 4, 66, 2, 12, 6, 8, 33, 22};
    static int[] arr2 = {1, 2, 3, 4, 5, 8, 9, 33, 22};

    public static void quickSort(int[] arr, int left, int right){
        if(left >= right) return;
        int pivotIndex = partition(arr, left, right);
        quickSort(arr, left, pivotIndex - 1);
        quickSort(arr, pivotIndex + 1, right);
    }

    public static int partition(int[] arr, int left, int right){
        int pivot = arr[right];     // 取最右边的为基准
        int i = left - 1;           // i用来记录小于pivot值的位置（即小于基准值数组的最后一个索引位置）
        for(int j = left; j < right; j++){
            if(arr[j] <= pivot){    // 如果j对应的值小于基准值，那么就需要将该值跟i+1的值调换
                i++;
                if(i == j) continue;    // 相等的话，无需交换，保证稳定性
                // i与j不相等，说明之前出现大于基准值的数，通过交换可以把该值往后移
                SortUtil.swap(arr, i, j);
            }
        }
        // i位置的值是小于基准值的，i+1的值是大于基准值的，通过该交换，把基准值放到合适的位置
        SortUtil.swap(arr, i + 1, right);
        //logger.debug("pivote: " + pivot + ", arr: " + Arrays.toString(arr));
        return i + 1;
    }

    public static void main(String[] args) {
        quickSort(arr, 0, arr.length - 1);
        logger.debug(Arrays.toString(arr));
    }
}
