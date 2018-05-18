package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 简单选择排序，属于选择排序
 *
 */
public class SelectionSort {

	private static Logger logger = LoggerFactory.getLogger(SelectionSort.class);

	static int[] arr = {55, 4, 2, 12, 6, 8, 9, 33, 22};
	static int[] arr2 = {1, 2, 3, 4, 5, 8, 9, 33, 22};

	public static void selectionSort(int[] arr){
		int len = arr.length;
		int minIndex;
		for(int i = 0; i < len - 1; i++){
			minIndex = i;
			for(int j = i + 1; j < len; j++){
				if(arr[minIndex] > arr[j]){
					minIndex = j;
				}
			}
			if(minIndex != i){
				SortUtil.swap(arr, minIndex, i);
			}
		}
		logger.debug("After sort: {}", Arrays.toString(arr));
	}

	public static void main(String[] args) {
		selectionSort(arr);
	}
}
