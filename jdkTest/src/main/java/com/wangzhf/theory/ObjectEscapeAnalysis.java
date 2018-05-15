package com.wangzhf.theory;

/**
 * 对象逃逸分析
 *
 * VM 参数：
 *      未开启逃逸分析：-server -verbose:gc -Xmx10m
 *      开启逃逸分析：-server -verbose:gc -XX:+DoEscapeAnalysis -Xmx10m
 *
 */
public class ObjectEscapeAnalysis {

    private static class Foo {
        private int x;
        private static int counter;

        public Foo() {
            x = (++counter);
        }
    }

    public static void main(String[] args) {
        long start = System.nanoTime();
        for (int i = 0; i < 1000 * 1000 * 10; ++i) {
            Foo foo = new Foo();
        }
        long end = System.nanoTime();
        System.out.println("Time cost is " + (end - start));
    }

}
