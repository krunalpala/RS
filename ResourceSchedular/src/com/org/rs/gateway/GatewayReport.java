package com.org.rs.gateway;

import java.util.ArrayList;

import com.org.rs.message.MessageImpl;
import com.org.rs.utility.Constants;


public class GatewayReport {

	private static GatewayReport gwReportInstance = null;
	private static  ArrayList<MessageWrapper> sentMsgList;
	private static  ArrayList<MessageImpl> failureMsgList;
	private static  ArrayList<MessageImpl> completedMsgList;
	private static  ArrayList<MessageImpl> validationErrorMsgList;
	private static  ArrayList<MessageImpl> duplicateMsgList;
	private static  ArrayList<String> terminationGroupList;
	private static  ArrayList<String> completeListOfValidMsg;
	
	

	private GatewayReport() {
		// Exists only to defeat instantiation.
	}



	public static GatewayReport getInstance() {
		if (gwReportInstance == null) {
			sentMsgList = new ArrayList<MessageWrapper>();
			failureMsgList = new ArrayList<MessageImpl>();
			completedMsgList = new ArrayList<MessageImpl>();
			validationErrorMsgList = new ArrayList<MessageImpl>();
			terminationGroupList = new ArrayList<String>();
			duplicateMsgList = new ArrayList<MessageImpl>();
			completeListOfValidMsg = new ArrayList<String>();
			gwReportInstance = new GatewayReport();
		}
		return gwReportInstance;
	}
	
	public void addToTerminationGroupList(Integer groupId, Integer producerId){
		terminationGroupList.add(Constants.PRODUCER+producerId+Constants.SPACE+Constants.GROUP+groupId);
	}
	
	public void addToCompleteListOfValidMsg(Integer groupId, Integer producerId,Integer msgId){
		completeListOfValidMsg.add(Constants.PRODUCER+producerId+Constants.SPACE+Constants.GROUP+groupId
				+Constants.SPACE+Constants.MSGID+msgId);
	}
	
	public void addToDuplicateMsgList(MessageImpl msgImpl){
		duplicateMsgList.add(msgImpl);
	}
	

	public void addToValidationErrorMsgList(MessageImpl msgImpl){
		validationErrorMsgList.add(msgImpl);
	}
	

	public void addToSentMsg(MessageWrapper msgWrpr){
		sentMsgList.add(msgWrpr);
	}
	
	public void addToFailureMsg(MessageImpl msgWrpr){
		failureMsgList.add(msgWrpr);
	}

	
	public void addToCompletedMsg(MessageImpl msgImpl){
		completedMsgList.add(msgImpl);
	}


	/**
	 * @return the sentMsgList
	 */
	public static ArrayList<MessageWrapper> getSentMsgList() {
		return sentMsgList;
	}


	/**
	 * @return the failureMsgList
	 */
	public static ArrayList<MessageImpl> getFailureMsgList() {
		return failureMsgList;
	}




	/**
	 * @return the completedMsgList
	 */
	public static ArrayList<MessageImpl> getCompletedMsgList() {
		return completedMsgList;
	}


	/**
	 * @return the validationErrorMsgList
	 */
	public static ArrayList<MessageImpl> getValidationErrorMsgList() {
		return validationErrorMsgList;
	}


	/**
	 * @return the terminationGroupList
	 */
	public static ArrayList<String> getTerminationGroupList() {
		return terminationGroupList;
	}

	/**
	 * @return the completeListOfValidMsg
	 */
	public static ArrayList<String> getCompleteListOfValidMsg() {
		return completeListOfValidMsg;
	}
	
	
	/**
	 * @return the duplicateMsgList
	 */
	public static ArrayList<MessageImpl> getDuplicateMsgList() {
		return duplicateMsgList;
	}	
}
