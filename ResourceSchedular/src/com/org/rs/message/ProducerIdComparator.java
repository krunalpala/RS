package com.org.rs.message;


import java.util.Comparator;

import com.org.rs.gateway.MessageWrapper;

public class ProducerIdComparator implements Comparator<MessageWrapper>{

	@Override
	public int compare(MessageWrapper o1, MessageWrapper o2) {
		// TODO Auto-generated method stub
		return o1.getMsg().getProducerId()-o2.getMsg().getProducerId();
	}

}
