package com.org.rs.gateway;

import java.util.concurrent.ArrayBlockingQueue;

import com.org.rs.message.MessageImpl;
import com.org.rs.message.MessageStatus;
import com.org.rs.utility.Constants;
import com.org.rs.utility.ValidationUtility;


public class GatewayValidation {

	private static GatewayValidation validationInstance = null;
	private GatewayReport gtwReport = GatewayReport.getInstance();

	private GatewayValidation() {
		// Exists only to defeat instantiation.
	}

	public static GatewayValidation getInstance() {
		if (validationInstance == null) {
			validationInstance = new GatewayValidation();
		}
		return validationInstance;
	}

	public void validateGatwyBatchParams(Integer sizeOfBatch,
			Integer gatewayId, Integer delayIntervalSeconds,
			Integer startAfterSeconds, Integer stopAfterSeconds,
			PriorityStrategy msgPriorityStrategy)
			throws GatewayValidationException {

		if (!ValidationUtility.checkNullAndPositiveInteger(gatewayId, true)) {
			throw new GatewayValidationException("Gateway Id must be valid ");
		}
		if (!ValidationUtility.checkNullAndPositiveInteger(sizeOfBatch, false)) {
			throw new GatewayValidationException("Size Of Batch must be valid ");
		}
		if (!ValidationUtility.checkNullAndPositiveInteger(startAfterSeconds,
				true)) {
			throw new GatewayValidationException(
					"Start After Seconds must be valid ");
		}
		if (!ValidationUtility.checkNullAndPositiveInteger(
				delayIntervalSeconds, false)) {
			throw new GatewayValidationException(
					"Delay Interval Seconds must be valid ");
		}
		if (!ValidationUtility.checkNullAndPositiveInteger(stopAfterSeconds,
				false)) {
			throw new GatewayValidationException(
					"Stop After Seconds must be valid ");
		}

		if (!ValidationUtility.checkObjNull(msgPriorityStrategy)) {
			throw new GatewayValidationException(
					"Message Priority Strategy must be valid ");
		}

		String strategyName = msgPriorityStrategy.getStrategyName();
//		StrategyType strategyTypesAry = msgPriorityStrategy.getStrategyTypes();
//		List<SortWrapper> sortWrapperList = msgPriorityStrategy
//				.getSortWrapperList();

		if (!ValidationUtility.checkStringNullAndBlank(strategyName)) {
			throw new GatewayValidationException("Strategy Name must be valid ");
		}

		// TODO validation for priority stragegy

		// TODO validation for seconds
		// if(stopAfterSeconds<startAfterSeconds)

	}

	public boolean messageValidator(MessageImpl msgToCheck)
			throws GatewayValidationException {

		if (!ValidationUtility.checkObjNull(msgToCheck)) {
			throw new GatewayValidationException("Message must be valid ");
		}

		int groupId = msgToCheck.getGroupId();
		int producerId = msgToCheck.getProducerId();
		int msgId = msgToCheck.getMsgId();
		String msgName = msgToCheck.getMsgName();
		MessageStatus msgStatus = msgToCheck.getMsgStatus(); // not sent, sent,
																// completed,
																// error enum

		if (!ValidationUtility.checkNullAndPositiveInteger(groupId, true)) {
			throw new GatewayValidationException("Group Id must be valid ");
		}

		if (!ValidationUtility.checkNullAndPositiveInteger(producerId, true)) {
			throw new GatewayValidationException("Producer Id must be valid ");
		}

		if (!ValidationUtility.checkNullAndPositiveInteger(msgId, true)) {
			throw new GatewayValidationException("Message Id must be valid ");
		}

		if (!ValidationUtility.checkStringNullAndBlank(msgName)) {
			throw new GatewayValidationException("Message Name must be valid ");
		}
		if (msgStatus != MessageStatus.NOT_SENT) {
			throw new GatewayValidationException(
					"Message Status must be NOT SENT/SENT_TO_GATEWAY/RECEIVED BY GATEWAY");
		}

		String toChkForUnique = Constants.PRODUCER + producerId
				+ Constants.SPACE + Constants.GROUP + groupId + Constants.SPACE
				+ Constants.MSGID + msgId;

		if (!chkUniqueMessageIdsInBatchList(toChkForUnique)) {
			gtwReport.addToDuplicateMsgList(msgToCheck);
			throw new GatewayValidationException("Duplicate Message Received");
		}
		String toChkForTermination = Constants.PRODUCER + producerId
				+ Constants.SPACE + Constants.GROUP + groupId;

		if (!chkTerminationMsg(toChkForTermination))
			throw new GatewayValidationException(
					"Termination Message already received for this Producer and Group, this cannot be processed");

		return true;

	}

	public boolean chkUniqueMessageIdsInBatchList(String toChkForUnique) {

		if (GatewayReport.getCompleteListOfValidMsg().contains(toChkForUnique)) {
			return false;

		} else {
			return true;
		}

	}

	public boolean chkTerminationMsg(String toChkForTermination) {
		if (GatewayReport.getTerminationGroupList().contains(
				toChkForTermination)) {
			return false;

		} else {
			return true;
		}

	}

	public void validateGatwySchParams(Integer gatewayId,
			Integer startAfterSeconds, Integer stopAfterSeconds,
			Integer delayIntervalSeconds, ArrayBlockingQueue<MessageWrapper> gtwSenderArrayBlkQue
			,Integer sendSize) throws GatewayValidationException {
		// TODO Auto-generated method stub
		if (!ValidationUtility.checkNullAndPositiveInteger(gatewayId, true)) {
			throw new GatewayValidationException("Gateway Id must be valid ");
		}

		if (!ValidationUtility.checkNullAndPositiveInteger(startAfterSeconds,
				true)) {
			throw new GatewayValidationException(
					"Start After Seconds must be valid ");
		}
		if (!ValidationUtility.checkNullAndPositiveInteger(
				delayIntervalSeconds, false)) {
			throw new GatewayValidationException(
					"Delay Interval Seconds must be valid ");
		}
		if (!ValidationUtility.checkNullAndPositiveInteger(stopAfterSeconds,
				false)) {
			throw new GatewayValidationException(
					"Stop After Seconds must be valid ");
		}
		
		if (!ValidationUtility.checkObjNull(gtwSenderArrayBlkQue)) {
			throw new GatewayValidationException("Sender Array must be valid ");
		}
		
		if (!ValidationUtility.checkNullAndPositiveInteger(sendSize,
				false)) {
			throw new GatewayValidationException(
					"Send Size must be valid ");
		}
		

	}

}
