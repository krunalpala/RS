package com.org.rs.externalresource;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.org.rs.message.Message;
import com.org.rs.utility.Constants;


public class ExternalResourceProcessor  {
	
	private Integer externalResourceId;
	private Integer delayIntervalSeconds;
	private Integer startAfterSeconds;
	private Integer stopAfterSeconds;
	private ExternalResourceValidation externalRsValidation = ExternalResourceValidation.getInstance();
	private LinkedBlockingDeque<Message> messageExtRsQueue;
	private ExternalResourceThreadFactory externalRsThdFactory ;
	private ScheduledExecutorService schExternalResourceThreadPool;
	
	
	public ExternalResourceProcessor(Integer externalResourceId,
			LinkedBlockingDeque<Message> messageQueue,
			Integer startAfterSeconds,Integer delayIntervalSeconds,
			Integer stopAfterSeconds
			) {
		// TODO can be made configurable for single or multiple threads of a single producer.
		
		try {

			if (Constants.ACTIVATE_VALIDATION.equals(Constants.YES)) {
				externalRsValidation.validateExtRsImplParams(externalResourceId,messageQueue, 
						startAfterSeconds, delayIntervalSeconds,
						stopAfterSeconds);
			}

		} catch (ExternalRsValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.externalResourceId = externalResourceId;
		this.startAfterSeconds = startAfterSeconds;
		this.delayIntervalSeconds = delayIntervalSeconds;
		this.stopAfterSeconds = stopAfterSeconds;

		this.externalRsThdFactory = new ExternalResourceThreadFactory(externalResourceId.toString());
		this.messageExtRsQueue = messageQueue;
//		this.schExternalResourceThreadPool =Executors.newSingleThreadScheduledExecutor(externalRsThdFactory);
		this.schExternalResourceThreadPool =Executors.newScheduledThreadPool(5, externalRsThdFactory);
//		this.externalReport = ExternalResourceReport.getInstance();
		
				//;

	}
	
	/**
	 * Initialization and run a Producer Thread
	 */
	public void initAndRun() throws Exception{
		
		System.out.println(" External Resource with Id - " + externalResourceId
				+ " initializing for consuming Messages from Gateway");
		

		ExternalResourceImpl extrnlRs = new ExternalResourceImpl(externalResourceId, messageExtRsQueue);

//		schProducerThreadPool.schedule(producer1, 0, TimeUnit.SECONDS);
		final ScheduledFuture<?> extRsFuture = schExternalResourceThreadPool.scheduleAtFixedRate(extrnlRs, startAfterSeconds, delayIntervalSeconds, TimeUnit.SECONDS);
		
		StopExternalResource stopExtRs = new StopExternalResource(extRsFuture,schExternalResourceThreadPool, externalResourceId);
		
		schExternalResourceThreadPool.schedule(stopExtRs, stopAfterSeconds, TimeUnit.SECONDS);

	}


	/**
	 * @return the schExternalResourceThreadPool
	 */
	public ScheduledExecutorService getSchExternalResourceThreadPool() {
		return schExternalResourceThreadPool;
	}

	
	

		
	 
	   
}
