package com.sky.profiler4j.agent.profile;

import java.util.LinkedList;

import com.sky.profiler4j.agent.profile.util.MethodUtil;

public class AppRootMethodStack {

	public AppRootMethodStack() {
	}

	/**
	 * 总时间
	 */
	private long sum_time;

	/**
	 * 总次数
	 */
	private long count;

	private LinkedList<ThreadMethodStack> threadMethodStacks;

	public long getSum_time() {
		return sum_time;
	}

	public void setSum_time(long sum_time) {
		this.sum_time = sum_time;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public LinkedList<ThreadMethodStack> getThreadMethodStacks() {
		return threadMethodStacks;
	}

	public void setThreadMethodStacks(LinkedList<ThreadMethodStack> threadMethodStacks) {
		this.threadMethodStacks = threadMethodStacks;
	}

	public static void count(ThreadMethodStack threadMethodStack, int depth) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			s.append("--");
		}
		System.out.println(s + MethodUtil.getMethodName(threadMethodStack.getMethod_id()) + "---"
				+ (threadMethodStack.getEnd_time() - threadMethodStack.getStart_time()));

		LinkedList<ThreadMethodStack> childrenMethods = threadMethodStack.getChildrenMethods();
		if (childrenMethods != null && threadMethodStack.getChildrenMethods().size() > 0) {
			for (ThreadMethodStack method2 : childrenMethods) {
				count(method2, depth + 1);
			}
		}
	}
}
