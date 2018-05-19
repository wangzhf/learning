package com.wangzhf.algorithm.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 堆排序，属于选择排序
 */
public class HeapSort {

	private static Logger logger = LoggerFactory.getLogger(HeapSort.class);

	static int[] arr = {55, 4, 2, 12, 6, 8, 9, 33, 22};
	static int[] arr2 = {1, 2, 3, 4, 5, 8, 9, 33, 22};

	/**
	 * 构建最大堆
	 * @param arr
	 * @param heapSize 堆中有效元素数量
	 * @param i 索引位置
	 */
	public static void maxHeapify(int[] arr, int heapSize, int i){
		int left = 2 * i + 1;
		int right = 2 * i + 2;
		int largest = i;
		if(left < heapSize && arr[left] > arr[largest]){
			largest = left;
		}
		if(right < heapSize && arr[right] > arr[largest]){
			largest = right;
		}
		if(largest != i) {
			SortUtil.swap(arr, i, largest);
			maxHeapify(arr, heapSize, largest);
		}
	}

	public static void buildMaxHeap(int[] arr){
		int len = arr.length;
		// 从最后一个非叶子节点开始
		for(int i = len / 2; i > 0; i--){
			maxHeapify(arr, len, i);
		}
	}

	public static void heapSort(int[] arr) {
		buildMaxHeap(arr);
		logger.debug("maxHeap: {}", arr);
		int len = arr.length;
		for(int i = len - 1; i > 0; i--){
			// 将最大值放到最后一位，剩下的再次构建最大堆
			SortUtil.swap(arr, 0, i);
			// 最后一位已换成之前大顶堆的最大值，再次构建的时候此值不在堆的范围内
			maxHeapify(arr, i,0);
		}
		logger.debug("After sort: {}", Arrays.toString(arr));
	}

	public static void main(String[] args) {
		heapSort(arr);
	}
}
