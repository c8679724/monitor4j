package com.sky.profiler4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.sky.profiler4j.agent.profile.AppRootMethodStack;
import com.sky.profiler4j.agent.profile.util.MinuteTime;

public class Application {

	/**
	 * 应用标志,每个jvm的每次重启都算一个应用，数据入库的时候thread、method的id需要拼接上applicationFlag
	 */
	public static String applicationFlag = "";

	private static long Application_Method_id = 0;

	private static Lock lock_get_next_Application_Method_id = new ReentrantLock(false);

	/**
	 * 记录所有方法的总耗时和总执行次数,作为实时监控用
	 */
	private static AppRootMethodStack applicationMethodStack = new AppRootMethodStack();

	/**
	 * 每分钟运行线程对正在运行或者已经结束的线程里面的已退出的方法进行结算。
	 * 结算栈，每3分钟进行清算，清算后方法顺序不变，总时间和总次数清零。清算时，先复制一个清空数据的栈给后台结算线程使用，复制一份数据专门进行清算
	 */
	private static AppRootMethodStack methodStack2 = new AppRootMethodStack();

	/**
	 * 按照时间存储每一次清算结果
	 */
	private static Map<MinuteTime, AppRootMethodStack> methodStack_ = new HashMap<MinuteTime, AppRootMethodStack>();

	/**
	 * 结算时给方法提供一个唯一的id
	 * 
	 * @return
	 */
	public static long get_next_Application_Method_id() {

		lock_get_next_Application_Method_id.lock();

		try {
			Application_Method_id = Application_Method_id + 1;
		} finally {
			lock_get_next_Application_Method_id.unlock();
		}

		return Application_Method_id;
	}
}
