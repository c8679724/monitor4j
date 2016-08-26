package com.sky.profiler4j.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import com.sky.profiler4j.agent.util.ClassesChoose;
import com.sky.profiler4j.agent.util.PropertiesUtil;

/**
 * agent 入口
 *
 * 这里提供了agent入口，获取到jvm中加载的所有类，可以通过asm或者javassist对已经加载的类添加我们想要的代码
 *
 * @author sky
 * @date 2015年10月13日
 */
public class Agent {

	private static Instrumentation instrumentation;

	// 通过代码加载agent
	public static void agentmain(String args, Instrumentation inst) {
		System.out.println("agentmain");
		agent(args, inst);
	}

	// 通过jvm参数加载agent
	public static void premain(String args, Instrumentation inst) {
		System.out.println("premain");
		agent(args, inst);
	}

	public static void agent(String args, Instrumentation inst) {

		// 用一个map把转换之前的字节码存储起来，序列化这个map到java tmp中，
		// 如果发生改动了，十秒后往缓存文件里写一次
		// 在退出监控的时候把字节码换回去，减少性能消耗，也方便下一次转换

		// 检查是否已经被转换过了，使用类的内存标记类的版本号

		System.out.println("stating agent.........");

		String path = Agent.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		if (path.endsWith(".jar")) {
			String[] path_splits = path.split("/");
			path = path.substring(0, path.length() - path_splits[path_splits.length - 1].length());
		}
		String propertiesFilePath = "/monitor.properties";
		PropertiesUtil.doMonitorSystemProperties(propertiesFilePath);
		PropertiesUtil.doUserProperties(path);
		Class<?>[] classes = inst.getAllLoadedClasses();
		// for (Class class1 : classes) {
		// System.out.println("------"+class1.getName());
		// }

		// 获得筛选后的class集合，准备对该集合里面的class进行替换
		Class<?>[] classes2 = ClassesChoose.chooseClasses(classes);

		inst.addTransformer(new Transformer(), true);
		// inst.addTransformer(new TransformerThrowable(), true);
		if (classes2.length > 0) {
			try {
				inst.retransformClasses(classes2);
			} catch (UnmodifiableClassException e) {
				e.printStackTrace();
			}
		}
	}

	public long getObjectSize(Object objectToSize) {
		return instrumentation.getObjectSize(objectToSize);
	}
}
