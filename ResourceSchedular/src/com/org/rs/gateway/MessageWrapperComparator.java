package com.org.rs.gateway;

import java.util.Comparator;

public class MessageWrapperComparator   implements Comparator<MessageWrapper>{

	
	/**
	 * a negative integer, zero, or a positive integer as the first argument is less than, equal to, or greater than the second.
	 */
	@Override
	public int compare(MessageWrapper o1, MessageWrapper o2) {
		// TODO Auto-generated method stub
		if(o1.getSendOrderNo() - o2.getSendOrderNo() > 0){
			return 1;
		} else if(o1.getSendOrderNo() - o2.getSendOrderNo() < 0){
			return -1;
		}else {
			return 0;
		}
	}


}
