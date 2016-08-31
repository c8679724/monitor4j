package com.sky.profiler4j.test.rmi;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.sky.profiler4j.transport.cluster.ClusterMain;
import com.sky.profiler4j.transport.service.TransportService;

public class TestClusterRmi {

	public static void main(String[] args) {

		TransportService transportService = null;
		try {
			transportService = ClusterMain.getRmiService();
		} catch (MalformedURLException | RemoteException | NotBoundException e1) {
			e1.printStackTrace();
		}

		// 测试多线程
		for (int i = 0; i < 10000; i++) {
			new Thread(new TestRunnable(transportService, i)).start();
		}
	}
}

class TestRunnable implements Runnable {

	private TransportService transportService;
	private int index;

	public TestRunnable(TransportService transportService, int index) {
		this.transportService = transportService;
		this.index = index;
	}

	public void run() {
		try {
			System.out.println(transportService.test("Test" + index));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
