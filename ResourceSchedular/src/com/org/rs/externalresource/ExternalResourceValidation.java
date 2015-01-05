package com.org.rs.externalresource;

import java.util.concurrent.LinkedBlockingDeque;

import com.org.rs.message.Message;
import com.org.rs.utility.ValidationUtility;


public class ExternalResourceValidation {

	private static ExternalResourceValidation validationInstance = null;

	private ExternalResourceValidation() {
		// Exists only to defeat instantiation.
	}

	public static ExternalResourceValidation getInstance() {
		if (validationInstance == null) {
			validationInstance = new ExternalResourceValidation();
		}
		return validationInstance;
	}

	public  void validateExtRsImplParams(Integer externalResourceId, LinkedBlockingDeque<Message> messageQueue,
			Integer startAfterSeconds,Integer delayIntervalSeconds,
			Integer stopAfterSeconds		
			) throws ExternalRsValidationException {
		
		if(!ValidationUtility.checkNullAndPositiveInteger(externalResourceId, true)){
			throw new ExternalRsValidationException("External Resource Id must be valid ");
		}
		
		if(!ValidationUtility.checkObjNull(messageQueue)){
			throw new ExternalRsValidationException("Message Queue must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(startAfterSeconds, true)){
			throw new ExternalRsValidationException("Start After Seconds must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(delayIntervalSeconds, false)){
			throw new ExternalRsValidationException("Delay Interval Seconds must be valid ");
		}
		if(!ValidationUtility.checkNullAndPositiveInteger(stopAfterSeconds, false)){
			throw new ExternalRsValidationException("Stop After Seconds must be valid ");
		}
		
		// TODO validation for seconds
//		if(stopAfterSeconds<startAfterSeconds)
		
	}
}
