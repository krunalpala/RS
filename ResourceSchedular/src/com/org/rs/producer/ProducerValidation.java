package com.org.rs.producer;

import java.util.concurrent.LinkedBlockingDeque;

import com.org.rs.message.Message;
import com.org.rs.utility.ValidationUtility;



public class ProducerValidation {

	private static ProducerValidation validationInstance = null;

	private ProducerValidation() {
		// Exists only to defeat instantiation.
	}

	public static ProducerValidation getInstance() {
		if (validationInstance == null) {
			validationInstance = new ProducerValidation();
		}
		return validationInstance;
	}

	public  void validateProducerWrprParams(Integer producerId, Integer groupId,
			LinkedBlockingDeque<Message> messageQueue,
			Integer cancelMsgsFromNumber,Integer startAfterSeconds,Integer delayIntervalSeconds,
			Integer stopAfterSeconds,Integer terminationMsgNumber		
			) throws ProducerValidationException {
		
		if(!ValidationUtility.checkNullAndPositiveInteger(producerId, true)){
			throw new ProducerValidationException("Producer Id must be valid ");
		}
		
		if(!ValidationUtility.checkNullAndPositiveInteger(groupId, true) ){
			throw new ProducerValidationException("Group Id must be valid ");
		}
		if(!ValidationUtility.checkObjNull(messageQueue)){
			throw new ProducerValidationException("Message Queue must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(startAfterSeconds, true)){
			throw new ProducerValidationException("Start After Seconds must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(delayIntervalSeconds, false)){
			throw new ProducerValidationException("Delay Interval Seconds must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(cancelMsgsFromNumber, false)){
			throw new ProducerValidationException("Cancel Msg From Number must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(stopAfterSeconds, false)){
			throw new ProducerValidationException("Stop After Seconds must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(terminationMsgNumber, false)){
			throw new ProducerValidationException("Termination Message Number must be valid ");
		}
		
		// TODO validation for seconds
//		if(stopAfterSeconds<startAfterSeconds)
		
	}
	

}
