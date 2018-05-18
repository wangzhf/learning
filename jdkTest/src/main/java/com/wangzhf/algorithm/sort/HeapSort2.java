package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 堆排序，属于选择排序
 */
public class HeapSort2 {

	private static Logger logger = LoggerFactory.getLogger(HeapSort2.class);

	static int[] arr = {55, 4, 2, 12, 6, 8, 9, 33, 22};
	static int[] arr2 = {1, 2, 3, 4, 5, 8, 9, 33, 22};

	public static void heapSort(int[] arr){
		buildMaxHeap(arr);
		int len = arr.length;
		for(int i = len - 1; i > 0; i--){
			SortUtil.swap(arr, 0, i);
			len--;
			heapify(arr, 0);
		}

		logger.debug("After sort: {}", Arrays.toString(arr));
	}

	public static void buildMaxHeap(int[] arr){
		int len = arr.length;
		for(int i = len / 2; i >= 0; i--) {
			heapify(arr, i);
		}
	}

	public static void heapify(int[] arr, int i){
		int left = 2 * i,
				right = 2 * i + 1,
				largest = i,
				len = arr.length;
		if(left < len && arr[left] > arr[largest]){
			largest = left;
		}
		if(right < len && arr[right] > arr[largest]){
			largest = right;
		}

		if(largest != i){
			SortUtil.swap(arr, i, largest);
			heapify(arr, largest);
		}
	}

	public static void main(String[] args) {
		heapSort(arr);
	}
}
