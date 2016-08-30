package com.sky.profiler4j.test.asm;

import com.sky.profiler4j.agent.profile.threadStack.ThreadProfiler;
import com.sky.profiler4j.agent.profile.util.MethodUtil;

/**
 * 最初原型
 * @author sky
 *
 */
public class Test {

	public static void main(String[] args) {

		// method.invoke
		new Test().test();
	}

	public void test() {
		int gmid = 0;
		try {
			gmid = MethodUtil.registerMethod(this.getClass().getDeclaredMethod("test"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		ThreadProfiler.enterMethod(gmid);
		System.out.println("test");
		test1();
		ThreadProfiler.exitMethod(gmid);
	}

	public void test1() {
		int gmid = 0;
		try {
			gmid = MethodUtil.registerMethod(this.getClass().getDeclaredMethod("test1"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		ThreadProfiler.enterMethod(gmid);
		System.out.println("test1");
		test2();
		new AppTest3().test();
		ThreadProfiler.exitMethod(gmid);
	}

	public void test2() {
		int gmid = 0;
		try {
			gmid = MethodUtil.registerMethod(this.getClass().getDeclaredMethod("test2"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		ThreadProfiler.enterMethod(gmid);
		System.out.println("test2");
		new AppTest3().test();
		ThreadProfiler.exitMethod(gmid);
	}
}

class AppTest3 {
	public void test() {
		int gmid = 0;
		try {
			gmid = MethodUtil.registerMethod(this.getClass().getDeclaredMethod("test"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		ThreadProfiler.enterMethod(gmid);
		System.out.println("test");
		test1();
		ThreadProfiler.exitMethod(gmid);
	}

	public void test1() {
		int gmid = 0;
		try {
			gmid = MethodUtil.registerMethod(this.getClass().getDeclaredMethod("test1"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		ThreadProfiler.enterMethod(gmid);
		System.out.println("test1");
		test2();
		ThreadProfiler.exitMethod(gmid);
	}

	public void test2() {
		int gmid = 0;
		try {
			gmid = MethodUtil.registerMethod(this.getClass().getDeclaredMethod("test2"));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		ThreadProfiler.enterMethod(gmid);
		System.out.println("test2");
		ThreadProfiler.exitMethod(gmid);
	}
}