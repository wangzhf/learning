package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 直接插入排序，属于插入排序
 *
 * 插入排序（Insertion-Sort）的算法描述是一种简单直观的排序算法。它的工作原理是通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。
 * 具体算法描述如下：
 * 		1. 从第一个元素开始，该元素可以认为已经被排序；
 * 		2. 取出下一个元素，在已经排序的元素序列中从后向前扫描；
 * 		3. 如果该元素（已排序）大于新元素，将该元素移到下一位置；
 * 		4. 重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
 * 		5. 将新元素插入到该位置后；
 * 		6. 重复步骤2~5。
 */
public class StraightInsertionSort {

	private static Logger logger = LoggerFactory.getLogger(StraightInsertionSort.class);

	static int[] arr = {55, 4, 2, 12, 6, 8, 9, 33, 22};
	static int[] arr2 = {1, 2, 3, 4, 5, 8, 9, 33, 22};

	public static void insertionSort(int[] arr){
		int len = arr.length;
		int current, preIndex;
		for(int i = 1; i < len; i++){	// 默认第一个已经排好序，所以从第二个开始
			preIndex = i - 1;			// i-1表示已经排好序子数组的最后一个元素
			current = arr[i];			// 记录需要比较的元素
			while (preIndex >= 0 && arr[preIndex] > current){	// 依次与前面有序数列比较
				arr[preIndex + 1] = arr[preIndex];		// 交换
				preIndex-- ;	// 需要与前面有序依次比较
			}
			arr[preIndex + 1] = current;	// 如果不符合while条件，证明已经找到current的合适的位置
		}

		logger.debug("After sorted: {}", Arrays.toString(arr));
	}

	public static void main(String[] args) {
		insertionSort(arr);
	}

}
