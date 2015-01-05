package com.org.rs.producer;

import java.util.concurrent.ThreadFactory;

public class ProducerThreadFactory implements ThreadFactory {
	
	private String groupId;
	
	ProducerThreadFactory(String groupId){
		
		this.groupId = groupId;
	}
	
	
	public Thread newThread(Runnable r) {
		return new Thread(r,"Producer-"+groupId);
	}
}
