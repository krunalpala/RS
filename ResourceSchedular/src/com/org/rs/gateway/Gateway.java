package com.org.rs.gateway;

import com.org.rs.message.Message;

public interface Gateway {

	public void send(Message msg);
	
}
