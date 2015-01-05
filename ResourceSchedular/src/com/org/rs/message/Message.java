/**
 * 
 */
package com.org.rs.message;


public interface Message {

	public void completed(int extRsId);

	void error(int extRsId, String errorDesc);
}
