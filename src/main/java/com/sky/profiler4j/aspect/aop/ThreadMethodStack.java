package com.sky.profiler4j.aspect.aop;

import java.util.LinkedList;

import com.sky.profiler4j.aspect.util.MethodUtil;

/**
 * 运行时方法 </br>
 * 原则上为减少内存消耗，运行时方法不存储方法名、方法参数类型、方法返回值类型。需要通过方法id去获取这些元素 </br>
 * 后面需要添加后台静默生产该类型的对象的过程，产用克隆 </br>
 * Method对象产用ehcache存储，后台静默计算是否只做统计还是需要上传到数据库
 * 
 * @author c8679724
 *
 */
public class ThreadMethodStack {

	public ThreadMethodStack(int method_id) {
		this.method_id = method_id;
		this.setStart_time();
	}

	/**
	 * 方法的id，每执行一次方法都有一个id,结算的时候才赋值id
	 */
	private long id;

	/**
	 * 通过method_id能换取方法的方法名、方法参数类型、方法返回值类型<这个值在字节码阶段就要搞到，不然运行期获取会很消耗性能>
	 */
	private int method_id;

	private long start_time;

	private long end_time;

	private ThreadMethodStack fatherMethod;

	private LinkedList<ThreadMethodStack> childrenMethods;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getMethod_id() {
		return method_id;
	}

	public void setMethod_id(int method_id) {
		this.method_id = method_id;
	}

	public long getStart_time() {
		return start_time;
	}

	public void setStart_time() {
		this.start_time = System.nanoTime();
	}

	public void setStart_time(long start_time) {
		this.start_time = start_time;
	}

	public long getEnd_time() {
		return end_time;
	}

	public void setEnd_time() {
		this.end_time = System.nanoTime();
	}

	public void setEnd_time(long end_time) {
		this.end_time = end_time;
	}

	public ThreadMethodStack getFatherMethod() {
		return fatherMethod;
	}

	public void setFatherMethod(ThreadMethodStack fatherMethod) {
		this.fatherMethod = fatherMethod;
	}

	public LinkedList<ThreadMethodStack> getChildrenMethods() {
		return childrenMethods;
	}

	public void setChildrenMethods(LinkedList<ThreadMethodStack> childrenMethods) {
		this.childrenMethods = childrenMethods;
	}

	public static void count(ThreadMethodStack threadMethodStack, int depth) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			s.append("--");
		}
		System.out.println(s + MethodUtil.getMethodName(threadMethodStack.getMethod_id()) + "---"
				+ (threadMethodStack.getEnd_time() - threadMethodStack.getStart_time()) + "ns");

		LinkedList<ThreadMethodStack> childrenMethods = threadMethodStack.getChildrenMethods();
		if (childrenMethods != null && threadMethodStack.getChildrenMethods().size() > 0) {
			for (ThreadMethodStack method2 : childrenMethods) {
				count(method2, depth + 1);
			}
		}
	}
}
