package com.sky.profiler4j.test.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.sky.profiler4j.transmit.cluster.ClusterMain;
import com.sky.profiler4j.transmit.service.TransmitService;

public class TestClusterRmi {

	public static void main(String[] args) {

		TransmitService transmitService = null;
		try {
			transmitService = ClusterMain.getRmiService();
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}

		// 测试多线程
		for (int i = 0; i < 10000; i++) {
			new Thread(new TestRunnable(transmitService, i)).start();
		}
	}
}

class TestRunnable implements Runnable {

	private TransmitService transmitService;
	private int index;

	public TestRunnable(TransmitService transmitService, int index) {
		this.transmitService = transmitService;
		this.index = index;
	}

	public void run() {
		try {
			System.out.println(transmitService.test("Test" + index));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
