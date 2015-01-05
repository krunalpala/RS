package com.org.rs.externalresource;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class StopExternalResource implements Runnable {

	private ScheduledFuture<?> extRsFuture;
	private ScheduledExecutorService schExtRsThreadPool;
	private Integer externalResourceId;

	public StopExternalResource(ScheduledFuture<?> extRsFuture,
			ScheduledExecutorService schExtRsThreadPool,
			Integer externalResourceId) {
		this.extRsFuture = extRsFuture;
		this.schExtRsThreadPool = schExtRsThreadPool;
		this.externalResourceId = externalResourceId;
	}

	@Override
	public void run() {
		System.out.println("Stopping External Resource. "+externalResourceId);

		extRsFuture.cancel(false);
		/*
		 * Note that this Task also performs cleanup, by asking the scheduler to
		 * shutdown gracefully.
		 */
		schExtRsThreadPool.shutdownNow();
		
	}

}
