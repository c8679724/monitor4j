package com.sky.profiler4j.aspect.aop;

public class ThreadValue {

	public static ThreadLocal<ThreadMethodStack> methodTree = new ThreadLocal<ThreadMethodStack>();

	public static ThreadLocal<ThreadMethodStack> methodTree_Copy = new ThreadLocal<ThreadMethodStack>();

	public static ThreadLocal<ThreadMethodStack> fatherMethod = new ThreadLocal<ThreadMethodStack>();

	/**
	 * web、rpc、socket、自定义
	 */
	public static int threadType = 0;
}
