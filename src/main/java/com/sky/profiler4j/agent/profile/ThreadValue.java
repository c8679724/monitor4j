package com.sky.profiler4j.agent.profile;

public class ThreadValue {

	public static ThreadLocal<ThreadMethodStack> methodTree = new ThreadLocal<ThreadMethodStack>();

	public static ThreadLocal<ThreadMethodStack> fatherMethod = new ThreadLocal<ThreadMethodStack>();

	/**
	 * 用来做定时清算的备份，这个时候一般方法还没有结束，但是需要对方法进行一次清算，为了避免影响运行结果，对线程方法访问树做一个备份，清算备份
	 */
	public static ThreadLocal<ThreadMethodStack> methodTree_Copy = new ThreadLocal<ThreadMethodStack>();

	/**
	 * web、rpc、socket、自定义
	 */
	public static int threadType = 0;
}
