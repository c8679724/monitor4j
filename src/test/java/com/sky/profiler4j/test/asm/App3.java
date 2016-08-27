package com.sky.profiler4j.test.asm;

public class App3 {

	public static void main(String[] args) {
		// method.invoke
		new App3().test();
	}

	public static void test() {
		System.out.println("test");
		new App3().test1();
		new App3().test2();
	}

	public void test1() {
		try {
			
		} catch (Exception e) {
			
		}
		System.out.println("test1");
		test2();
	}

	public void test2() {
		System.out.println("test2");
		new AppTest4().test();
	}
}