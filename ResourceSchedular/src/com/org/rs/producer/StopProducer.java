package com.org.rs.producer;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class StopProducer  implements Runnable {
		
		private ScheduledFuture<?> producerMsgFuture;
		private ScheduledExecutorService schProducerThreadPool;
		private Integer producerId;
		
		public StopProducer(ScheduledFuture<?> producerMsgFuture,
				ScheduledExecutorService schProducerThreadPool, Integer producerId) {
			this.producerMsgFuture = producerMsgFuture;
			this.schProducerThreadPool= schProducerThreadPool;
			this.producerId= producerId;
		}
 

		@Override
		public void run() {
			System.out.println("Stopping producer."+producerId);
			
			
			producerMsgFuture.cancel(false);
			/*
			 * Note that this Task also performs cleanup, by asking the
			 * scheduler to shutdown gracefully.
			 */
			schProducerThreadPool.shutdownNow();
		}

		

}
