package com.sky.profiler4j.transmit.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TransmitService extends Remote {

	public String test(String json) throws RemoteException;
	public String transmit(String json) throws RemoteException;
}
