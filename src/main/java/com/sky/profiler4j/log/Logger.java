package com.sky.profiler4j.log;

public class Logger {

	private static String logFilePath = "";

	protected static boolean isLogFileEnabled = true;
	protected static boolean isDebugEnabled = false;
	protected static boolean isInfoEnabled = true;
	protected static boolean isWarnEnabled = true;
	protected static boolean isErrorEnabled = true;

	/**
	 * 记录日志
	 * 
	 * @param level
	 * @param log
	 */
	private void log(LogLevel level, String log) {

		appendToFile(log);
	}

	/**
	 * 把日志写入文件
	 * 
	 * @param log
	 */
	private void appendToFile(String log) {

	}

	public void debug(String msg) {

		log(LogLevel.DEBUG, msg);
	}

	public void debug(String format, Object arg) {

	}

	public void debug(String format, Object arg1, Object arg2) {
	}

	public void debug(String format, Object... arguments) {
	}

	public void debug(String msg, Throwable t) {
	}

	public void info(String msg) {

		log(LogLevel.DEBUG, msg);
	}

	public void info(String format, Object arg) {

	}

	public void info(String format, Object arg1, Object arg2) {
	}

	public void info(String format, Object... arguments) {
	}

	public void info(String msg, Throwable t) {
	}

	public void warn(String msg) {

		log(LogLevel.DEBUG, msg);
	}

	public void warn(String format, Object arg) {

	}

	public void warn(String format, Object arg1, Object arg2) {
	}

	public void warn(String format, Object... arguments) {
	}

	public void warn(String msg, Throwable t) {
	}

	public void error(String msg) {

		log(LogLevel.DEBUG, msg);
	}

	public void error(String format, Object arg) {

	}

	public void error(String format, Object arg1, Object arg2) {
	}

	public void error(String format, Object... arguments) {
	}

	public void error(String msg, Throwable t) {
	}

	public boolean isLogFileEnabled() {
		return isLogFileEnabled;
	}

	public boolean isDebugEnabled() {
		return isDebugEnabled;
	}

	public boolean isInfoEnabled() {
		return isInfoEnabled;
	}

	public boolean isWarnEnabled() {
		return isWarnEnabled;
	}

	public boolean isErrorEnabled() {
		return isErrorEnabled;
	}

}

enum LogLevel {
	DEBUG, INFO, WARN, ERROR;
}
