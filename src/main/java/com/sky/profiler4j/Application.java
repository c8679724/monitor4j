package com.sky.profiler4j;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sky.profiler4j.agent.profile.AppMethodStack;
import com.sky.profiler4j.agent.profile.ThreadMethodStack;
import com.sky.profiler4j.agent.profile.util.MinuteTime;

public class Application {

	public static long agent_start_time = 0;

	public static final Gson gson = new GsonBuilder().create();

	/**
	 * 应用标志,每个jvm的每次重启都算一个应用，数据入库的时候thread、method的主键需要拼接上applicationFlag
	 */
	public static String applicationFlag = "";

	/**
	 * 记录所有方法的总耗时和总执行次数,作为实时监控用
	 */
	private static AppMethodStack appStackRoot = AppMethodStack.getTreeRoot();

	public static LinkedList<ThreadMethodStack> threadMethodStacks = new LinkedList<ThreadMethodStack>();

	/**
	 * 每分钟运行线程对正在运行或者已经结束的线程里面的已退出的方法进行结算。
	 * 结算栈，每3分钟进行清算，清算后方法顺序不变，总时间和总次数清零。清算时，先复制一个清空数据的栈给后台结算线程使用，复制一份数据专门进行清算
	 */
	private static AppMethodStack appStackRoot2 = null;

	/**
	 * 按照时间存储每一次清算结果
	 */
	private static Map<MinuteTime, AppMethodStack> applicationStack = new HashMap<MinuteTime, AppMethodStack>();

	/**
	 * agent里面的异步引擎</br>
	 * agent的所有异步任务从这里开始.
	 */
	public static void async() {

		try {
			//
			asyncMergeThreadMethodStack();

			//
		} catch (Exception e) {

		} finally {

		}
	}

	/**
	 * 异步合并线程方法访问树到应用访问树去
	 */
	public static void asyncMergeThreadMethodStack() {

		try {
			new Thread(new Runnable() {
				public void run() {
					while (true) {

						try {
							Thread.sleep(1000 * 30);
						} catch (InterruptedException e) {

						}

						int threadMethodStacksSize = threadMethodStacks.size();
						if (threadMethodStacksSize > 0) {
							for (int i = 0; i < threadMethodStacksSize; i++) {
								appStackRoot = AppMethodStack.merge(appStackRoot, threadMethodStacks.removeFirst());
							}
						}

						// 排序，排序的作用不只是为了好看，还是为了寻找节点的时候速度更快，概率大的会放到前面，减少搜索次数
						appStackRoot = AppMethodStack.sort(appStackRoot, 1, 0);

						System.out.println("------定时打印appStack");
						AppMethodStack.print(appStackRoot, 0);
					}
				}
			}, "asyncMergeThreadStackTask").start();
		} catch (Exception e) {

		}
	}
}
