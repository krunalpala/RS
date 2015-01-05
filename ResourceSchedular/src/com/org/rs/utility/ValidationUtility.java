package com.org.rs.utility;

public class ValidationUtility {
	
	
	
	public static boolean checkNullAndPositiveInteger(Integer toCheck, boolean includeZero){
		
		if(includeZero){
			if(checkObjNull(toCheck) && toCheck.intValue() >= 0){
				return true;
			} else {
				System.out.println("Value is less than 0/Negative");
				return false;
			}
		} else {
			if(checkObjNull(toCheck) && toCheck.intValue() > 0){
				return true;
			} else {
				System.out.println("Value is less than/equal to 0/Negative");
				return false;
			}
		}
		
	}
	// TODO can be made generic to include all the Number types. 
	
	public static boolean checkNullAndPositiveLong(Long toCheck, boolean includeZero){
		
		if(includeZero){
			if(checkObjNull(toCheck) && toCheck.longValue() >= 0){
				return true;
			} else {
				System.out.println("Value is less than 0/Negative");
				return false;
			}
		} else {
			if(checkObjNull(toCheck) && toCheck.longValue() > 0){
				return true;
			} else {
				System.out.println("Value is less than/equal to 0/Negative");
				return false;
			}
		}
		
	}
	
	public static boolean checkStringNullAndBlank(String toCheck){
		
		if(checkObjNull(toCheck) && !toCheck.equals(Constants.EMPTY_STRING)){
			return true;
		}
		else{
			System.out.println("String is empty");
			return false;
		}
		
	}
	
	public static boolean checkObjNull(Object toCheck){
		
		if(toCheck == null){
			System.out.println("Object is null");
			return false;
		}
		else {
			return true;
		}
	}
	
	
	

}
