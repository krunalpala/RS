package externalresourcetest;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

import com.org.rs.externalresource.ExternalResourceProcessor;
import com.org.rs.externalresource.ExternalResourceReport;
import com.org.rs.gateway.GatewayBatch;
import com.org.rs.gateway.GatewayReport;
import com.org.rs.gateway.GatewayValidationException;
import com.org.rs.gateway.MsgFieldNames;
import com.org.rs.gateway.PriorityStrategy;
import com.org.rs.gateway.SortType;
import com.org.rs.gateway.SortWrapper;
import com.org.rs.gateway.StrategyType;
import com.org.rs.message.Message;
import com.org.rs.producer.ProducerWrapper;


public class Setup {
	
	private static LinkedBlockingDeque<Message> gtwyReceiverQueue = new LinkedBlockingDeque<Message>();
	private static LinkedBlockingDeque<Message> extRsQueue = new LinkedBlockingDeque<Message>();
	
	public static void main(String[] args) throws InterruptedException, GatewayValidationException
	{
		testProducer();
	}
	
	public static void testProducer() throws InterruptedException, GatewayValidationException {
		
		Integer p1ProducerId =1;
		Integer p1GroupId=1;
		Integer p1cancelMsgsFromNumber=50; 
		Integer p1DelayIntervalSeconds=8;
		Integer p1StartAfterSeconds=1;
		Integer p1StopAfterSeconds=40;
		Integer p1TerminationMsgNumber= 40;
		
		ProducerWrapper producerObject  = new ProducerWrapper(p1ProducerId, p1GroupId, gtwyReceiverQueue,
				p1StartAfterSeconds ,p1DelayIntervalSeconds, p1cancelMsgsFromNumber, p1StopAfterSeconds,p1TerminationMsgNumber);	
		
		Integer p2ProducerId =2;
		Integer p2GroupId=2;
		Integer p2CancelMsgsFromNumber=40; 
		Integer p2DelayIntervalSeconds=7;
		Integer p2StartAfterSeconds=1;
		Integer p2StopAfterSeconds=40;
		Integer p2TerminationMsgNumber=40;
		
		ProducerWrapper producer2Object  = new ProducerWrapper(p2ProducerId, p2GroupId, gtwyReceiverQueue,
				p2StartAfterSeconds ,p2DelayIntervalSeconds, p2CancelMsgsFromNumber, p2StopAfterSeconds,p2TerminationMsgNumber);
		
		
		 Integer c1externalResourceId=1;
		 Integer c1delayIntervalSeconds=5;
		 Integer c1startAfterSeconds=5;
		 Integer c1stopAfterSeconds=40;
		 
		 ExternalResourceProcessor externalRsProcessor = new ExternalResourceProcessor(c1externalResourceId, extRsQueue, c1startAfterSeconds, c1delayIntervalSeconds, c1stopAfterSeconds);
		
		 Integer c2externalResourceId=2;
		 Integer c2delayIntervalSeconds=5;
		 Integer c2startAfterSeconds=5;
		 Integer c2stopAfterSeconds=40;
		 
		 ExternalResourceProcessor externalRsProcessor2 = new ExternalResourceProcessor(c2externalResourceId, extRsQueue, c2startAfterSeconds, c2delayIntervalSeconds, c2stopAfterSeconds);
		 
		 Integer gbDelayIntervalSeconds = 3;// poll interval
		 Integer gbStartAfterSeconds=2;
		 Integer gbStopAfterSeconds=40;
		 Integer gbGatewayBatchId = 1;
		 Integer gbSizeOfBatch = 5;
		 PriorityStrategy gbMsgPriorityStrategy = new PriorityStrategy();
		 List<SortWrapper> sortWrapperList = new ArrayList<SortWrapper>();
//		 sortWrapperList.add(new SortWrapper(MsgFieldNames.PRODUCER_ID, SortType.ASC));
//		 sortWrapperList.add(new SortWrapper(MsgFieldNames.GROUP_ID, SortType.DESC));
//		 sortWrapperList.add(new SortWrapper(MsgFieldNames.MSG_ID, SortType.DESC));
		 sortWrapperList.add(new SortWrapper(MsgFieldNames.MSG_NAME, SortType.ASC));
		 
		 
		 gbMsgPriorityStrategy.setSortWrapperList(sortWrapperList );
		 gbMsgPriorityStrategy.setStrategyName("Normal Strategy");
		 gbMsgPriorityStrategy.setStrategyTypes(StrategyType.FIFO);
		
		
		 GatewayBatch gtwBatch = new GatewayBatch(gbGatewayBatchId, gbSizeOfBatch, gbDelayIntervalSeconds,
				gbStartAfterSeconds, gbStopAfterSeconds, gbMsgPriorityStrategy, extRsQueue, gtwyReceiverQueue);
		
		
		 
	 
		try {
			producerObject.initAndRun();
			producer2Object.initAndRun();
			gtwBatch.initAndRun();
			
			externalRsProcessor.initAndRun();
			externalRsProcessor2.initAndRun();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(!(producerObject.getSchProducerThreadPool().isTerminated() && 
				producer2Object.getSchProducerThreadPool().isTerminated() &&
				externalRsProcessor.getSchExternalResourceThreadPool().isTerminated() &&
				gtwBatch.getSchProducerThreadPool().isTerminated() && externalRsProcessor2.getSchExternalResourceThreadPool().isTerminated()
				))
		{
			Thread.currentThread();
			Thread.sleep(4000);
		}
		
		
			
		System.out.println("Failue Messages in ext rs  are : "+ ExternalResourceReport.getInstance().getFailureMsg().size() );
		System.out.println("Success of Messages in ext rs are : "+ExternalResourceReport.getInstance().getSuccessMsg().size()  );
		
		System.out.println("Gateway Reports ------------------");
		System.out.println("1. getCompletedMsgList  " + GatewayReport.getCompletedMsgList().size());
		System.out.println(GatewayReport.getCompletedMsgList().toString());
		
		System.out.println("2. getFailureMsgList  " + GatewayReport.getFailureMsgList().size());
		System.out.println(GatewayReport.getFailureMsgList().toString());
		
		System.out.println("3. getCompleteListOfValidMsg "+GatewayReport.getCompleteListOfValidMsg().size());
		System.out.println(GatewayReport.getCompleteListOfValidMsg().toString());
		
		System.out.println("4. getDuplicateMsgList "+GatewayReport.getDuplicateMsgList().size());
		System.out.println(GatewayReport.getDuplicateMsgList().toString());
		
		System.out.println("5. getSentMsgList "+GatewayReport.getSentMsgList().size());
		System.out.println(GatewayReport.getSentMsgList().toString());
		
		System.out.println("6. getTerminationGroupList " +GatewayReport.getTerminationGroupList().size());
		System.out.println(GatewayReport.getTerminationGroupList().toString());
		
		System.out.println("7. getValidationErrorMsgList " + GatewayReport.getValidationErrorMsgList().size());
		System.out.println(GatewayReport.getValidationErrorMsgList().toString());
		
		
		
		
	}

}
