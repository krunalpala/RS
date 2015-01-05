package com.org.rs.externalresource;

import java.util.ArrayList;

import com.org.rs.message.MessageImpl;


public class ExternalResourceReport {
	
	private static ExternalResourceReport reportInstance = null;
	private static ArrayList<MessageImpl> successMsg;
	private static ArrayList<MessageImpl> failureMsg;

	private ExternalResourceReport() {
		// Exists only to defeat instantiation.
	}

	/**
	 * @return the successMsg
	 */
	public  ArrayList<MessageImpl> getSuccessMsg() {
		return successMsg;
	}

	/**
	 * @return the failureMsg
	 */
	public  ArrayList<MessageImpl> getFailureMsg() {
		return failureMsg;
	}

	public static ExternalResourceReport getInstance() {
		if (reportInstance == null) {
			successMsg = new ArrayList<MessageImpl>();
			failureMsg = new ArrayList<MessageImpl>();
			reportInstance = new ExternalResourceReport();
		}
		return reportInstance;
	}
	
	public void addToSuccess(MessageImpl msg){
		successMsg.add(msg);
	}
	
	public void addToFailure(MessageImpl msg){
		failureMsg.add(msg);
	}

}
