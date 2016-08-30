package com.sky.profiler4j.log;

public class LoggerFactory {

	public static Logger getLogger(String name) {
		return null;
	}

	public static Logger getLogger(Class<?> clazz) {
		return getLogger(clazz.getName());
	}

	public static void initLoggerFactory() {

	}
}
