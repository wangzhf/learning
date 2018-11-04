package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 希尔排序，属于插入排序
 */
public class ShellSort {

	private static Logger logger = LoggerFactory.getLogger(ShellSort.class);

	static int[] arr = {55, 4, 66, 2, 12, 6, 8, 33, 22};
	static int[] arr2 = {1, 2, 3, 4, 5, 8, 9, 33, 22};

	public static void shellSort(int[] arr){
		int len = arr.length,
				temp,
				gap = 1;
		while (gap < len / 3){	// 确定最大的间隔值
			gap = gap * 3 + 1;	// 确保不会丢失元素
		}

		for(; gap > 0; gap /= 3){	// 控制间隔数，需要递减，直至最后一次为1
			for(int i = gap; i < len; i++){		// 从间隔数处开始往后
				temp = arr[i];
				int j = i - gap;	// 相当于快速插入排序，记录第一部分的位置
				while (j >= 0 && arr[j] > temp){
					arr[j + gap] = arr[j];	// 交换每部分相同位置的值
					j -= gap;
				}
				arr[j + gap] = temp;
			}
		}

		logger.debug("After sort is : {}", Arrays.toString(arr));
	}

	public static void main(String[] args) {
		shellSort(arr);
	}

}
