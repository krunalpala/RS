package com.org.rs.gateway;

import java.util.concurrent.ThreadFactory;

public class GatewayThreadFactory implements ThreadFactory {

	private String gatewayId;

	GatewayThreadFactory(String gatewayId) {

		this.gatewayId = gatewayId;
	}

	public Thread newThread(Runnable r) {
		return new Thread(r, "Gateway-"+gatewayId);
	}
}
