package com.sky.profiler4j.transmit.client;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sky.profiler4j.transmit.service.TransmitService;
import com.sky.profiler4j.transmit.service.TransmitServiceImpl;

public class AgentClient {

	private final static Logger logger = Logger.getLogger(AgentClient.class.getName());

	public static void startRmiService(String host, int port) {

		try {
			TransmitService transmitService = new TransmitServiceImpl();
			// 本地主机上的远程对象注册表Registry的实例，并指定端口为8888，这一步必不可少（Java默认端口是1099），必不可缺的一步，缺少注册表创建，则无法绑定对象到远程注册表上
			LocateRegistry.createRegistry(port);
			// 将名称绑定到对象,即向命名空间注册已经实例化的远程服务对象
			String url = "rmi://" + host + ":" + port + "/transmitService";
			Naming.bind(url, transmitService);
			logger.log(Level.INFO, "启动 rmi service 完成,url---" + url);
		} catch (Exception e) {
			logger.log(Level.WARNING, "启动 rmi service 异常,下面将无法提供远程数据传输服务!", e);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		startRmiService("localhost", 8869);
		while (true) {
			Thread.sleep(1000);
		}
	}
}
