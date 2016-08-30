package com.sky.profiler4j.log;

public class Logger {

	protected static boolean isLog = true;
	protected static boolean isDebug = false;
	protected static boolean isInfo = true;
	protected static boolean isWarn = true;
	protected static boolean isError = true;

	private boolean log(LogLevel level, String log) {
		return false;
	}

	public boolean debug(String log) {

		return log(LogLevel.DEBUG, log);
	}

	public boolean info() {
		return false;
	}

	public boolean warn() {
		return false;
	}

	public boolean error() {
		return false;
	}

	public boolean isLog() {
		return isLog;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public boolean isInfo() {
		return isInfo;
	}

	public boolean isWarn() {
		return isWarn;
	}

	public boolean isError() {
		return isError;
	}

}

enum LogLevel {
	DEBUG, INFO, WARN, ERROR;
}
