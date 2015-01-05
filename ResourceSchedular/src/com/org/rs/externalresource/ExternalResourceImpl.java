/**
 * 
 */
package com.org.rs.externalresource;

import java.util.concurrent.LinkedBlockingDeque;

import com.org.rs.message.Message;
import com.org.rs.message.MessageImpl;
import com.org.rs.utility.Constants;


public class ExternalResourceImpl implements ExternalResource {
	
	private Integer externalResourceId;
	private LinkedBlockingDeque<Message> messageQueue;
	 

	public ExternalResourceImpl(Integer externalResourceId,LinkedBlockingDeque<Message> messageQueue) {
		// TODO Auto-generated constructor stub
		this.externalResourceId = externalResourceId;
		this.messageQueue =messageQueue;
		System.out.println(" External Resource Impl Initialized with Id - "+externalResourceId);
	}

	
	@Override
	public void run() {
		
		
		try {
			if(messageQueue.size()>0){
				processMessage4Gateway(messageQueue);
			}
			else{
				System.out.println("NO More Messages waiting to be processed by External Resource.");
			}
			
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Override
	public int processMessage4Gateway(LinkedBlockingDeque<Message> messageQueue) throws InterruptedException {
		System.out.println("External Resource processing started ..");
		MessageImpl msg = (MessageImpl) messageQueue.take();
		if(msg.getMsgName() != null){
			msg.completed(externalResourceId);
			ExternalResourceReport.getInstance().addToSuccess(msg);
			
			return 0;
		}
		else{
			msg.error(externalResourceId,Constants.ERROR_STRING_NULL);
			ExternalResourceReport.getInstance().addToFailure(msg);
			return -1;
		}
		
		
		
		
	}

}
