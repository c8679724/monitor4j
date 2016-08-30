package com.sky.profiler4j.agent.profile;

import java.util.LinkedList;

import com.sky.profiler4j.agent.profile.util.MethodUtil;

/**
 * 应用方法访问树
 * 
 * @author sky
 *
 */
public class AppMethodStack {

	public AppMethodStack() {

	}

	/**
	 * 获取一个应用方法访问树的树根
	 * 
	 * @return
	 */
	public static AppMethodStack getTreeRoot() {
		AppMethodStack root = new AppMethodStack();
		root.setMethodName("Thread.run()");
		root.setCount(0);
		root.setSum_time(0);
		return root;
	}

	/**
	 * 方法名称
	 */
	private String methodName;

	/**
	 * 总时间
	 */
	private long sum_time;

	/**
	 * 执行次数
	 */
	private long count;

	/**
	 * 异常次数
	 */
	private long exceptionCount;

	/**
	 * 子节点
	 */
	private LinkedList<AppMethodStack> childrenMethodStack;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

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

	public long getExceptionCount() {
		return exceptionCount;
	}

	public void setExceptionCount(long exceptionCount) {
		this.exceptionCount = exceptionCount;
	}

	public LinkedList<AppMethodStack> getChildrenMethodStack() {
		return childrenMethodStack;
	}

	public void setChildrenMethodStack(LinkedList<AppMethodStack> childrenMethodStack) {
		this.childrenMethodStack = childrenMethodStack;
	}

	/**
	 * 合并线程方法访问树到应用方法访问树的子节点中
	 * 
	 * @param methodTree
	 * @param threadMethodStack
	 * @return
	 */
	public static AppMethodStack merge(AppMethodStack methodTree, ThreadMethodStack threadMethodStack) {

		if (methodTree == null) {
			methodTree = getTreeRoot();
		}

		LinkedList<AppMethodStack> childrenMethodStack = methodTree.getChildrenMethodStack();
		AppMethodStack childMethodStack = null;
		int index = -1;
		String methodName = MethodUtil.getMethodName(threadMethodStack.getMethod_id());
		if (childrenMethodStack == null || childrenMethodStack.size() == 0) {// 应用方法访问树是否存在子节点，不存在新建一个
			if (childrenMethodStack == null) {
				childrenMethodStack = new LinkedList<AppMethodStack>();
			}
			childMethodStack = new AppMethodStack();
			childMethodStack.setMethodName(methodName);
		} else {
			boolean found = false;
			for (int i = 0; i < childrenMethodStack.size(); i++) {
				// 如果找到相同的节点了
				if (childrenMethodStack.get(i).getMethodName().equals(methodName)) {
					childMethodStack = childrenMethodStack.get(i);
					index = i;
					found = true;
					break;
				}
			}
			// 如果没有在方法访问树种找到要合并的方法节点，那么添加进去
			if (!found) {
				childMethodStack = new AppMethodStack();
				childMethodStack.setMethodName(methodName);
			}
		}

		childMethodStack.setCount(childMethodStack.getCount() + 1);
		childMethodStack.setSum_time(
				childMethodStack.getSum_time() + (threadMethodStack.getEnd_time() - threadMethodStack.getStart_time()));

		// 接下来通过递归处理方法访问树中的子节点
		LinkedList<ThreadMethodStack> threadMethodStacks = threadMethodStack.getChildrenMethods();
		if (threadMethodStacks != null && threadMethodStacks.size() > 0) {
			for (int i = 0; i < threadMethodStacks.size(); i++) {
				childMethodStack = merge(childMethodStack, threadMethodStacks.get(i));
			}
		}

		// 把处理完的childMethodStack添加到childrenMethodStack中
		if (index > -1) {
			childrenMethodStack.remove(index);
		}
		childrenMethodStack.add(childMethodStack);
		methodTree.setChildrenMethodStack(childrenMethodStack);

		// 结算自己
		long count = 0;
		long sumTime = 0;
		AppMethodStack bak = null;
		for (int i = 0; i < childrenMethodStack.size(); i++) {
			bak = childrenMethodStack.get(i);
			count += bak.getCount();
			sumTime += bak.getSum_time();
		}
		methodTree.setSum_time(sumTime);
		methodTree.setCount(count);
		return methodTree;
	}

	/**
	 * 对方法访问树进行按照耗时降序排序
	 * 
	 * @param methodTree
	 *            方法访问树进行
	 * @return 排序完成的方法访问树
	 */
	public static AppMethodStack sort(AppMethodStack methodTree) {

		return sort(methodTree, 0, 0);
	}

	/**
	 * 对方法访问树进行排序<升序、降序排序>
	 * 
	 * @param methodTree
	 *            方法访问树进行
	 * @param field
	 *            排序字段 <0、耗时 1、次数>
	 * @param orderBy
	 *            排序方式<0、降序 1、升序>
	 * @return 排序完成的方法访问树
	 */
	public static AppMethodStack sort(AppMethodStack methodTree, int field, int orderBy) {

		if (methodTree == null) {
			return methodTree;
		} else {
			return methodTree;
		}
	}

	public static String getJson(ThreadMethodStack threadMethodStack, int depth) {
		return "";
	}

	public static void print(AppMethodStack appStack, int depth) {

		StringBuilder s = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			s.append("--");
		}
		System.out.println(s + appStack.getMethodName() + "---count:" + appStack.getCount() + "---sumtime:"
				+ appStack.getSum_time() + "ns");

		LinkedList<AppMethodStack> childrenMethods = appStack.getChildrenMethodStack();
		if (childrenMethods != null && childrenMethods.size() > 0) {
			for (AppMethodStack appStack_ : childrenMethods) {
				print(appStack_, depth + 1);
			}
		}
	}
}
