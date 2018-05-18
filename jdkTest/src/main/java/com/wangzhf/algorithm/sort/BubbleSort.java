package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 冒泡排序，属于内部交换排序
 * 时间复杂度（平均）： O(n^2)
 * 时间复杂度（最坏）： O(n^2)
 * 时间复杂度（最好）： O(n)
 * 空间复杂度： O(1)
 * 稳定性：稳定
 *
 * 算法描述：
 *      1. 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
 *      2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
 *      3. 针对所有的元素重复以上的步骤，除了最后一个；
 *      4. 重复步骤1~3，直到排序完成。
 *
 */
public class BubbleSort {

    private static Logger logger = LoggerFactory.getLogger(BubbleSort.class);

    static int[] arr = {55, 4, 2, 12, 6, 8, 9, 33, 22};
    static int[] arr2 = {1, 2, 3, 4, 5, 8, 9, 33, 22};

    public static void bubbleSort(int[] arr) {
        int len = arr.length;
        boolean isSwap;
        for (int i = 0; i < len - 1; i++) {     // 控制次数
            isSwap = false;
            for(int j = 0; j < len - i - 1; j++){   // 控制相邻两个数
                if(arr[j] > arr[j + 1]){
                    SortUtil.swap(arr, j, j + 1);
                    isSwap = true;
                }
            }
            if(!isSwap){
                break;
            }
        }
        logger.debug(Arrays.toString(arr));
    }

    public static void main(String[] args) {
        bubbleSort(arr);
        //swap2(2, 4);
    }

}
