package com.org.rs.externalresource;

import java.util.concurrent.LinkedBlockingDeque;

import com.org.rs.message.Message;


public interface ExternalResource extends Runnable {
	
	public int processMessage4Gateway(LinkedBlockingDeque<Message> messageQueue) throws InterruptedException;

}
