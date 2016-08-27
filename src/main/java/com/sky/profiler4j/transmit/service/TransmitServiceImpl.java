package com.sky.profiler4j.transmit.service;

import java.io.Serializable;
import java.rmi.RemoteException;

public class TransmitServiceImpl implements TransmitService, Serializable {

	private static final long serialVersionUID = -1519633910298701578L;

	public TransmitServiceImpl() throws RemoteException {

	}

	public String transmit(String json) {

		return "hi:" + json;
	}
	
	public String test(String json) {

		return "hi:" + json;
	}
}
