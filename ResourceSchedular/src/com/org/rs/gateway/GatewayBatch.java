package com.org.rs.gateway;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.org.rs.message.Message;
import com.org.rs.utility.Constants;


public class GatewayBatch {

	private Integer delayIntervalSeconds;// poll interval
	private Integer startAfterSeconds;
	private Integer stopAfterSeconds;
	private Integer gatewayBatchId;
	private Integer sizeOfBatch;
	private GatewayValidation gatewayValidation = GatewayValidation.getInstance();
	private PriorityStrategy msgPriorityStrategy;
	private GatewayThreadFactory gatewayThdFactory ;
	private ScheduledExecutorService schGtwBtchThreadPool;
	private LinkedBlockingDeque<Message> extRsMessageQueue;
	private LinkedBlockingDeque<Message> gtwyMessageQueue;
	
	
	
	public GatewayBatch(Integer gatewayId,  Integer sizeOfBatch,Integer delayIntervalSeconds,
			Integer startAfterSeconds, Integer stopAfterSeconds,PriorityStrategy msgPriorityStrategy,
			LinkedBlockingDeque<Message> messageExtRsQueue,LinkedBlockingDeque<Message> gtwyMessageQueue) throws GatewayValidationException {
		super();
		
		try {

			if (Constants.ACTIVATE_VALIDATION.equals(Constants.YES)) {
				gatewayValidation.validateGatwyBatchParams(sizeOfBatch,
						gatewayId,startAfterSeconds,stopAfterSeconds,delayIntervalSeconds,msgPriorityStrategy);		
					
			}

		} catch (GatewayValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.gatewayBatchId = gatewayId;
		this.sizeOfBatch = sizeOfBatch;
		this.delayIntervalSeconds = delayIntervalSeconds;
		this.startAfterSeconds = startAfterSeconds;
		this.stopAfterSeconds = stopAfterSeconds;
		this.msgPriorityStrategy = msgPriorityStrategy;
		this.extRsMessageQueue = messageExtRsQueue;
		this.gtwyMessageQueue =gtwyMessageQueue;
		
		this.gatewayThdFactory = new GatewayThreadFactory("Batch:"+gatewayId.toString());
//		this.schGtwBtchThreadPool=Executors.newSingleThreadScheduledExecutor(gatewayThdFactory);
		this.schGtwBtchThreadPool=Executors.newScheduledThreadPool(5, gatewayThdFactory);
	}
	
	public void initAndRun() throws Exception{
		
		System.out.println(" Gateway-Batch- " + gatewayBatchId
				+ " initializing for receiving from Gateway Receiver and Making a Batch to send to Gateway Sender");

		GatewayBatchTask gatewayBatchTask = new GatewayBatchTask(gatewayBatchId,sizeOfBatch,msgPriorityStrategy,extRsMessageQueue,gtwyMessageQueue);
				

		final ScheduledFuture<?> gatewayMsgFuture = schGtwBtchThreadPool.scheduleAtFixedRate(gatewayBatchTask, startAfterSeconds, delayIntervalSeconds, TimeUnit.SECONDS);
		
		StopGatewayBatch stopGwBtch = new StopGatewayBatch(gatewayMsgFuture,schGtwBtchThreadPool,gatewayBatchId);
		
		schGtwBtchThreadPool.schedule(stopGwBtch, stopAfterSeconds, TimeUnit.SECONDS);

	}
	
	
	
	/**
	 * @return the schProducerThreadPool
	 */
	public ScheduledExecutorService getSchProducerThreadPool() {
		return schGtwBtchThreadPool;
	}
	
}
