package com.org.rs.externalresource;

import java.util.concurrent.ThreadFactory;

public class ExternalResourceThreadFactory implements ThreadFactory {

	private String consumerId;

	ExternalResourceThreadFactory(String consumerId) {

		this.consumerId = consumerId;
	}

	public Thread newThread(Runnable r) {
		return new Thread(r, "ExtResource"+consumerId);
	}
}
