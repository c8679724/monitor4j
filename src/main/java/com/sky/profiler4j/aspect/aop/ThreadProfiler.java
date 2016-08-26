package com.sky.profiler4j.aspect.aop;

import java.util.LinkedList;

public class ThreadProfiler {

	public static final void enterMethod(int method_id) {
		ThreadMethodStack methodTree = null;
		if (ThreadValue.methodTree.get() == null) {// 检查是否存在根方法，如果没有，自己成为树根
			methodTree = new ThreadMethodStack(method_id);
			ThreadValue.methodTree.set(methodTree);
			ThreadValue.fatherMethod.set(methodTree);
			methodTree.setChildrenMethods(new LinkedList<ThreadMethodStack>());
		} else {
			// 找到自己的父节点位置
			ThreadMethodStack fatherMethod = ThreadValue.fatherMethod.get();
			ThreadMethodStack thisMethod = new ThreadMethodStack(method_id);
			thisMethod.setFatherMethod(fatherMethod);
			thisMethod.setChildrenMethods(new LinkedList<ThreadMethodStack>());
			// 告诉自己的子方法自己是父方法
			ThreadValue.fatherMethod.set(thisMethod);
		}
	}

	public static final void exitMethod(int method_id) {

		// 还有一种情况，异常发生的时候，会多次调用exit方法来结束运行，这个时候时间以最后一次为准，但是其他的不变

		// 结算当前方法的子方法。<后续。。。。添加功能.异步结算子方法，结算后内存中不再存储当前方法的子方法，会结算成json文档进行存储，这个时候需要考虑然后存储父方法使在父子关系数据库中保持连贯>
		// 结算之前先比较一下是否已对线程中的方法树进行拷贝，如果已经拷贝，需要更新方法树
		// 结算子方法之前对整个线程的方法树拷贝一份，按照线程存储，然后对拷贝的线程树的每个方法的id赋值，再进行结算
		// 直到只剩下根节点，消除线程中的方法树
		ThreadMethodStack thisMethod_ = ThreadValue.fatherMethod.get();
		thisMethod_.setEnd_time();

		// 结算当前方法。<把当前方法添加到父方法的子方法里面>
		ThreadMethodStack fatherMethod = thisMethod_.getFatherMethod();
		if (fatherMethod != null) {
			LinkedList<ThreadMethodStack> childrenMethods = fatherMethod.getChildrenMethods();
			childrenMethods.add(thisMethod_);
		}

		ThreadMethodStack methodTree = ThreadValue.methodTree.get();
		// 如果树根是自己
		if (methodTree.equals(thisMethod_)) {
			// 异步结算根方法，结算后内存中不再存储根方法
			System.out.println("--------count----------");
			System.out.println("------------------");
			ThreadMethodStack.count(thisMethod_, 0);

			// 直到只剩下根节点，消除线程中的方法树和拷贝的方法树
			ThreadValue.methodTree_Copy = null;
			ThreadValue.fatherMethod.set(null);
			ThreadValue.methodTree.set(null);
		} else {
			// 把父方法设置回父方法线程变量中
			ThreadValue.fatherMethod.set(thisMethod_.getFatherMethod());
		}
	}
}
