package com.sky.profiler4j.transmit.cluster;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.sky.profiler4j.transmit.service.TransmitService;

/**
 * 命令行入口
 * 
 * @author sky
 *
 */
public class ClusterMain {

	private static TransmitService transmitService = null;

	/**
	 * 获取单例的远程服务
	 * 
	 * @return
	 * @throws MalformedURLException
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public static TransmitService getRmiService() throws MalformedURLException, RemoteException, NotBoundException {
		if (transmitService == null) {
			transmitService = (TransmitService) Naming.lookup("rmi://localhost:8869/transmitService");
		}
		return transmitService;
	}

	public static void main(String[] args) {

	}
}
