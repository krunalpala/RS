package com.org.rs.producer;

import com.org.rs.message.Message;

public interface Producer extends Runnable {
	
	Message produceMessages();
	
	
	
	
	

}
