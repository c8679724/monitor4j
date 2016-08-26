package com.sky.profiler4j.aspect.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.objectweb.asm.Type;

public class MethodUtil {

	/**
	 * String key = methodName+"("+methodParamsType+")"+returnTpye </br>
	 * 远程存储的时候需要转换
	 */
	private static Map<String, Integer> methods = new HashMap<String, Integer>();
	private static Map<Integer, String> methods_ = new HashMap<Integer, String>();

	private static int methodIndex = 0;
	private static Lock lock = new ReentrantLock();

	public static String getMethodName(int method_id) {
		return methods_.get(method_id);
	}

	public static int registerMethod(Method method) {

		lock.lock();
		Integer index = 0;
		try {
			methodIndex = methodIndex + 1;
			index = methodIndex;
		} finally {
			lock.unlock();
		}
		String methodDescriptor = Type.getMethodDescriptor(method);
		methods.put(method.getDeclaringClass().getName() + "." + method.getName() + methodDescriptor, index);
		methods_.put(index, method.getDeclaringClass().getName() + "." + method.getName() + methodDescriptor);
		return index;
	}

	public static int registerMethod(String methodName, String methodDescriptor) {
		lock.lock();
		Integer index = 0;
		try {
			methodIndex = methodIndex + 1;
			index = methodIndex;
		} finally {
			lock.unlock();
		}
		methods.put(methodName + methodDescriptor, index);
		methods_.put(index, methodName + methodDescriptor);
		return index;
	}

	public static int registerMethod(String methodName, Class<?>[] paramsType, Class<?> returnTpye) {
		lock.lock();
		Integer index = 0;
		try {
			methodIndex = methodIndex + 1;
			index = methodIndex;
		} finally {
			lock.unlock();
		}

		Type[] paramsType_ = new Type[paramsType.length];
		for (int i = 0; i < paramsType_.length; i++) {
			paramsType_[i] = Type.getType(paramsType[i]);
		}

		Type returnTpye_ = Type.getType(returnTpye);

		String methodDescriptor = Type.getMethodDescriptor(returnTpye_, paramsType_);
		methods.put(methodName + methodDescriptor, index);
		methods_.put(index, methodName + methodDescriptor);
		return index;
	}

	public static long get_Method_id(Method method) {

		return 0;
	}

	public static long get_Method_id(String methodName, String methodDescriptor) {

		return 0;
	}

	public static long get_Method_id(String methodName, Class<?>[] paramsType, Class<?> returnTpye) {

		return 0;
	}

}
