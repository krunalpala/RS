package com.org.rs.gateway;

public enum MsgFieldNames {
	
	GROUP_ID("groupId","com.org.rs.message.MessageGroupComparator"), 
	PRODUCER_ID("producerId","com.org.rs.message.ProducerIdComparator"),
	MSG_ID("msgId","com.org.rs.message.MessageIdComparator"),
	MSG_NAME("msgName","com.org.rs.message.MessageNameComparator");
	
	private String fieldName;
	private String className;
	
	private MsgFieldNames(String fieldName,String className) {
		// TODO Auto-generated constructor stub
		this.fieldName = fieldName;
		this.className = className;
	}
	
	public String getFieldName(){
		return this.fieldName;
	}
	
	public String getClassName(){
		return this.className;
	}

}
