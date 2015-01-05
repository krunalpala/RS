package com.org.rs.gateway;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import com.org.rs.message.Message;
import com.org.rs.message.MessageImpl;
import com.org.rs.message.MessageStatus;
import com.org.rs.utility.Constants;


public class GatewayBatchTask implements Runnable{
	
	private Integer gatewayBatchId;
	private Integer sizeOfBatch;
	private PriorityStrategy msgPriorityStrategy;
	private LinkedBlockingDeque<Message> messageExtRsQueue; 
	private GatewayReport gtwReport = GatewayReport.getInstance();
	private GatewayValidation gtwValidation = GatewayValidation.getInstance();
	private LinkedBlockingDeque<Message> gtwyMessageQueue;
	private volatile long sendOrderNumber;
	
	public GatewayBatchTask(Integer gatewayBatchId, 
			Integer sizeOfBatch,PriorityStrategy msgPriorityStrategy, LinkedBlockingDeque<Message> messageExtRsQueue, LinkedBlockingDeque<Message> gtwyMessageQueue) {
		// TODO Auto-generated constructor stub
		this.gatewayBatchId = gatewayBatchId;
		this.sizeOfBatch = sizeOfBatch;
		this.messageExtRsQueue =messageExtRsQueue;
		this.msgPriorityStrategy = msgPriorityStrategy;
		this.gtwyMessageQueue = gtwyMessageQueue;
	}


	@Override
	public void run() {
		
		try {
			
//			 receive msg from rceiver, sort and send to gtw sender
			
			if(gtwyMessageQueue.size() == 0){
				System.out.println(" Message List Empty - No more messages available to process");
				return;
			}
			
			List<MessageWrapper> msgWrapperList = extractionTask();
			
			if(msgWrapperList.size() == 0){
				System.out.println(" Message List Empty/ May Have Validation Errors - No more messages available to process");
				return;
			}
			
			sortingTask(msgWrapperList);
			
			sendingTask(msgWrapperList);
			
		} catch (InterruptedException e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		} catch (GatewayValidationException e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("exception " + e.getMessage());
			e.printStackTrace();
		}

	}


	private void sendingTask(List<MessageWrapper> msgWrapperList2) throws InterruptedException {
		// TODO Auto-generated method stub
		for(MessageWrapper msgWrpr : msgWrapperList2){
			messageExtRsQueue.put(msgWrpr.getMsg());
			
		}
		
		
	}


	private void sortingTask(List<MessageWrapper> msgWrapperList2) throws InstantiationException,
																		IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		
		// priority strategy
		List<SortWrapper> sortWrapperList=msgPriorityStrategy.getSortWrapperList();
		
		System.out.println("Sorting Task commencing with Strategy - "+msgPriorityStrategy.toString());
		
		System.out.println("Prior to Sort - msgWrapperList -contents");
		System.out.println(msgWrapperList2.toString());
		
		for(SortWrapper stwp: sortWrapperList){
			
			MsgFieldNames msgFieldName = stwp.getMsgFieldName();
			SortType sortType=stwp.getSortType();
			
			System.out.println("Applying First Sort -"+stwp.toString());
			
			@SuppressWarnings("unchecked")
			Class<Comparator<MessageWrapper>> compClass = (Class<Comparator<MessageWrapper>>) 
											Class.forName(msgFieldName.getClassName());
			Comparator<MessageWrapper> compObj = (Comparator<MessageWrapper>)compClass.newInstance();
			
			if(sortType == SortType.ASC){
				Collections.sort(msgWrapperList2, compObj);
			}else{
				Collections.sort(msgWrapperList2, Collections.reverseOrder(compObj));
			}
			
			System.out.println(" Result - "+msgWrapperList2.toString());
		}
		
		
		System.out.println("Final Result of Sort"+msgWrapperList2.toString());
		
		
	}


	private List<MessageWrapper> extractionTask() throws GatewayValidationException {
		// TODO Auto-generated method stub
		List<MessageWrapper> msgWrapperList = new ArrayList<MessageWrapper>(sizeOfBatch);;
		StrategyType strategyTypes = msgPriorityStrategy.getStrategyTypes();
		System.out.println("Extraction Starting using- Strategy - "+strategyTypes.toString());
		
		MessageImpl msgImpl = null;
		
		int count = 0;
		
		while(count<sizeOfBatch){
			
			if(strategyTypes == StrategyType.FIFO){
				msgImpl = (MessageImpl) gtwyMessageQueue.pollFirst();
			}else{
				msgImpl = (MessageImpl) gtwyMessageQueue.pollLast();
			}
			
			
			if(msgImpl == null){
				System.out.println("Deque Empty as Poll First/Poll Last returned Null - count currently "+
							count + " sizeOfBatch -"+sizeOfBatch);
				break;
			}
				
			
			int groupId=0;
			int producerId=0;
			int msgId=0;
			
//			validate each message includes finding of duplicates, termination , etc
			try {

				if (Constants.ACTIVATE_VALIDATION.equals(Constants.YES)) {
					gtwValidation.messageValidator(msgImpl);
				}
				
				groupId=msgImpl.getGroupId();
				producerId=msgImpl.getProducerId();
				msgId=msgImpl.getMsgId();
				
				

			} catch (GatewayValidationException e) {
				System.err.println(e.getMessage());
				gtwReport.addToValidationErrorMsgList(msgImpl);
				
				continue;
				// decrease count and continue to next - i.e. skip current
//				e.printStackTrace();
			}
			
			
			gtwReport.addToCompleteListOfValidMsg(groupId, producerId, msgId);
			
			if(msgImpl.isTerminationMsg()){
				gtwReport.addToTerminationGroupList(groupId, producerId);	
			}
			
			MessageWrapper msgWrapper = buildWrapper(msgImpl);
			
			gtwReport.addToSentMsg(msgWrapper);
			
			msgWrapperList.add(msgWrapper);
			
			
			count++;
		}
		
		System.out.println(" Extraction Finished - wrapper size - "+msgWrapperList.size());
		return msgWrapperList;
		
	}
	
	private MessageWrapper buildWrapper(MessageImpl msgImpl) {
		// TODO Auto-generated method stub
		msgImpl.setMsgStatus(MessageStatus.SENT);
		
		MessageWrapper msgWrapper = new MessageWrapper();
		msgWrapper.setBatchId(gatewayBatchId);
		msgWrapper.setMsg(msgImpl);
		msgWrapper.setSendOrderNo(generateSendOrderNumber());
		
		return msgWrapper;
		
		
		
	}


	public  long generateSendOrderNumber(){
		return ++sendOrderNumber;
	}
}
