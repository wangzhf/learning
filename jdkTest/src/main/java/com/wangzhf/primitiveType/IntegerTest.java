package com.wangzhf.primitiveType;

public class IntegerTest {

    public static void main(String[] args) {
		testShift();
    }

    public static void testShift(){
		int COUNT_BITS = Integer.SIZE - 3;
		int CAPACITY   = (1 << COUNT_BITS) - 1;
		System.out.println(COUNT_BITS);
		System.out.println(CAPACITY);
	}

	/**
	 * 测试整形默认初始化值
	 * 默认常量池中会存放-128~127，其余的都会重新创建
	 */
	public static void testIntCache(){
		Integer a = 0;
		Integer i = 11;
		Integer j = 11;
		Integer m = 2100;
		Integer n = 2100;
		System.out.println(i == j);  // true
		System.out.println(m == n);  // false
	}
}
