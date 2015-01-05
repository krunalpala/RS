package com.org.rs.gateway;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

// TODO can be made to one class
public class StopGatewayBatch implements Runnable  {

	private ScheduledFuture<?> gtwBatchFuture;
	private ScheduledExecutorService schGtwBatchThreadPool;
	private Integer gatewayBatchId;
	
	public StopGatewayBatch(ScheduledFuture<?> gtwBatchFuture,
			ScheduledExecutorService schGtwBatchThreadPool, Integer gatewayBatchId) {
		this.gtwBatchFuture = gtwBatchFuture;
		this.schGtwBatchThreadPool = schGtwBatchThreadPool;
		this.gatewayBatchId =gatewayBatchId;
	}

	@Override
	public void run() {
		System.out.println("Stopping Batch."+ gatewayBatchId);

		gtwBatchFuture.cancel(false);
		/*
		 * Note that this Task also performs cleanup, by asking the scheduler to
		 * shutdown gracefully.
		 */
		schGtwBatchThreadPool.shutdownNow();
	}
}
