package com.org.rs.message;


import java.util.Comparator;

import com.org.rs.gateway.MessageWrapper;

public class MessageIdComparator implements Comparator<MessageWrapper>{

	@Override
	public int compare(MessageWrapper o1, MessageWrapper o2) {
		// TODO Auto-generated method stub
		return o1.getMsg().getMsgId()-o2.getMsg().getMsgId();
	}

}
