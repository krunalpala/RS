package com.org.rs.producer;

import java.util.concurrent.LinkedBlockingDeque;

import com.org.rs.message.Message;
import com.org.rs.message.MessageImpl;
import com.org.rs.message.MessageStatus;
import com.org.rs.utility.Constants;


public class ProducerImpl implements Producer {
	
	private Integer groupId;
	private Integer terminationMsgNumber;
	private Integer cancelMsgsFromNumber;
	private LinkedBlockingDeque<Message> gtwyMessageQueue;
	private Integer producerId;
	private volatile  int messageId=0;
	
	public ProducerImpl(Integer groupId,Integer cancelMsgsFromNumber,Integer producerId, Integer terminationMsgNumber, LinkedBlockingDeque<Message> gtwyMessageQueue) {
		// TODO Auto-generated constructor stub
		this.groupId = groupId;
		this.terminationMsgNumber = terminationMsgNumber;
		this.cancelMsgsFromNumber = cancelMsgsFromNumber;
		this.producerId = producerId;
		this.gtwyMessageQueue = gtwyMessageQueue;
		System.out.println(" Message Producer Initialized with producer Id - "+producerId+ " " +
				" groupId - "+groupId+ " terminationMsgNumber  "+terminationMsgNumber +
				" cancelMsgsFromNumber "+ cancelMsgsFromNumber);
	}

	public Message produceMessages() {
		
		System.out.println("Producer of Messages start producerId -" + producerId);
		
		
		int msgId = generateMessageId();
		
		if(msgId >= cancelMsgsFromNumber){
			System.out.println("All the messages from Producer "+ producerId+" are cancelled from this number- "+
								cancelMsgsFromNumber+ " , No More Message generation permitted");
			return null;
		}
		
		MessageImpl msg = new MessageImpl();
		
		msg.setGroupId(groupId);
		msg.setProducerId(producerId);
		msg.setMsgId(msgId);
		
		if(Constants.PRODUCE_ERROR.equals(Constants.YES) && msgId%2 == 0)
		{
			msg.setMsgName(null);
		}
		else
		{
			msg.setMsgName(Constants.MSG_NAME_APPENDER +msgId +Constants.GROUP_NAME_APPENDER + groupId);
		}
		msg.setMsgStatus(MessageStatus.NOT_SENT);
		
		// Termination message Flag.
		if(msgId >=terminationMsgNumber){
			msg.setTerminationMsg(true);	
		}
		else
		{
			msg.setTerminationMsg(false);
		}
		
		System.out.println(msg.toString());
		return msg;

	}

	@Override
	public void run() {
		
		try {
			
			Message msg = null;
			
			msg = produceMessages();
			
			
			/*
			 * Inserts the specified element into the queue represented by
			 * this deque (in other words, at the tail of this deque),
			 * waiting if necessary for space to become available.
			 */
			sendToGateway(msg);
			
		} catch (InterruptedException ex) {
			System.out.println("exception " + ex.getMessage());
			ex.printStackTrace();
			// TODO mark the message with error

		}

	}

	private void sendToGateway(Message msg) throws InterruptedException {
		// check for cancelled messages.
		if(msg != null){
			gtwyMessageQueue.put(msg);
		}
		
	}
	
	public  int generateMessageId(){
		return ++messageId;
	}
	

}
