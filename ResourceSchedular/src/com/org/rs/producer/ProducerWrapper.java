package com.org.rs.producer;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.org.rs.message.Message;
import com.org.rs.utility.Constants;


public class ProducerWrapper  {

	private Integer producerId;
	private Integer groupId;
	private Integer cancelMsgsFromNumber; 
	private Integer terminationMsgNumber;
	private Integer delayIntervalSeconds;
	private Integer startAfterSeconds;
	private Integer stopAfterSeconds;
	private ProducerValidation producerValidation = ProducerValidation.getInstance();
	private LinkedBlockingDeque<Message> gtwyMessageQueue;
	private ProducerThreadFactory producerThdFactory ;
	private ScheduledExecutorService schProducerThreadPool;
	
	public ProducerWrapper(Integer producerId,Integer groupId,
			LinkedBlockingDeque<Message> messageQueue,
			Integer startAfterSeconds,Integer delayIntervalSeconds,
			Integer cancelMsgsFromNumber,Integer stopAfterSeconds,
			Integer terminationMsgNumber) {
		// TODO can be made configurable for single or multiple threads of a single producer.
		
		try {

			if (Constants.ACTIVATE_VALIDATION.equals(Constants.YES)) {
				producerValidation.validateProducerWrprParams(producerId,
						groupId, messageQueue, cancelMsgsFromNumber,
						startAfterSeconds, delayIntervalSeconds,
						stopAfterSeconds,terminationMsgNumber);
			}

		} catch (ProducerValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.producerId = producerId;
		this.groupId = groupId;
		this.cancelMsgsFromNumber = cancelMsgsFromNumber;
		this.startAfterSeconds = startAfterSeconds;
		this.delayIntervalSeconds = delayIntervalSeconds;
		this.stopAfterSeconds = stopAfterSeconds;
		this.terminationMsgNumber = terminationMsgNumber;

		this.producerThdFactory = new ProducerThreadFactory(producerId+"-"+groupId);
		this.gtwyMessageQueue = messageQueue;
//		this.schProducerThreadPool =Executors.newSingleThreadScheduledExecutor(producerThdFactory);
				//;
		this.schProducerThreadPool =Executors.newScheduledThreadPool(3, producerThdFactory);

	}
	
	/**
	 * Initialization and run a Producer Thread
	 */
	public void initAndRun() throws Exception{
		
		System.out.println(" Producer - " + producerId
				+ " initializing for producing Messages with groupId "
				+ groupId);

		ProducerImpl producer = new ProducerImpl(groupId, cancelMsgsFromNumber,producerId,terminationMsgNumber,gtwyMessageQueue);

		final ScheduledFuture<?> producerMsgFuture = schProducerThreadPool.scheduleAtFixedRate(producer, startAfterSeconds, delayIntervalSeconds, TimeUnit.SECONDS);
		
		StopProducer stopProducer = new StopProducer(producerMsgFuture,schProducerThreadPool,producerId);
		
		schProducerThreadPool.schedule(stopProducer, stopAfterSeconds, TimeUnit.SECONDS);

	}

	

	/**
	 * @return the schProducerThreadPool
	 */
	public ScheduledExecutorService getSchProducerThreadPool() {
		return schProducerThreadPool;
	}

}
