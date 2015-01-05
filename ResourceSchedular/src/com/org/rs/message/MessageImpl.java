package com.org.rs.message;

import com.org.rs.gateway.GatewayReport;

public class MessageImpl implements Message{
	

	private int groupId;
	private int producerId;
	private int msgId;
	private String msgName;
	private MessageStatus msgStatus; //not sent, sent, completed, error enum
	private boolean isTerminationMsg;
	private int processedByExtRsId;
	private String errorDesc;
	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [groupId=");
		builder.append(groupId);
		builder.append(", producerId=");
		builder.append(producerId);
		builder.append(", msgId=");
		builder.append(msgId);
		builder.append(", msgName=");
		builder.append(msgName);
		builder.append(", msgStatus=");
		builder.append(msgStatus);
		builder.append(", isTerminationMsg=");
		builder.append(isTerminationMsg);
		builder.append(", processedByExtRsId=");
		builder.append(processedByExtRsId);
		builder.append(", errorDesc=");
		builder.append(errorDesc);
		builder.append("] \n");
		return builder.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + groupId;
		result = prime * result + (isTerminationMsg ? 1231 : 1237);
		result = prime * result + msgId;
		result = prime * result + ((msgName == null) ? 0 : msgName.hashCode());
		result = prime * result
				+ ((msgStatus == null) ? 0 : msgStatus.hashCode());
		result = prime * result + producerId;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		MessageImpl other = (MessageImpl) obj;
		if (groupId != other.groupId) {
			return false;
		}
		if (isTerminationMsg != other.isTerminationMsg) {
			return false;
		}
		if (msgId != other.msgId) {
			return false;
		}
		if (msgName == null) {
			if (other.msgName != null) {
				return false;
			}
		} else if (!msgName.equals(other.msgName)) {
			return false;
		}
		if (msgStatus != other.msgStatus) {
			return false;
		}
		if (producerId != other.producerId) {
			return false;
		}
		return true;
	}

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * @return the processedByExtRsId
	 */
	public int getProcessedByExtRsId() {
		return processedByExtRsId;
	}

	/**
	 * @param processedByExtRsId the processedByExtRsId to set
	 */
	public void setProcessedByExtRsId(int processedByExtRsId) {
		this.processedByExtRsId = processedByExtRsId;
	}

	public MessageImpl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageImpl(int groupId, int producerId,int msgId, String msgName,
			MessageStatus msgStatus, boolean isTerminationMsg) {
		super();
		this.groupId = groupId;
		this.msgId = msgId;
		this.msgName = msgName;
		this.msgStatus = msgStatus;
		this.isTerminationMsg = isTerminationMsg;
		this.producerId = producerId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getMsgId() {
		return msgId;
	}

	public void setMsgId(int msgId) {
		this.msgId = msgId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	public String getMsgName() {
		return msgName;
	}

	public void setMsgName(String msgName) {
		this.msgName = msgName;
	}
	

	
	/**
	 * @return the msgStatus
	 */
	public MessageStatus getMsgStatus() {
		return msgStatus;
	}



	/**
	 * @param msgStatus the msgStatus to set
	 */
	public void setMsgStatus(MessageStatus msgStatus) {
		this.msgStatus = msgStatus;
	}



	/**
	 * @return the isTerminationMsg
	 */
	public boolean isTerminationMsg() {
		return isTerminationMsg;
	}



	/**
	 * @param isTerminationMsg the isTerminationMsg to set
	 */
	public void setTerminationMsg(boolean isTerminationMsg) {
		this.isTerminationMsg = isTerminationMsg;
	}




	/**
	 * @return the producerId
	 */
	public int getProducerId() {
		return producerId;
	}

	/**
	 * @param producerId the producerId to set
	 */
	public void setProducerId(int producerId) {
		this.producerId = producerId;
	}


	@Override
	public void completed(int extRsId) {
		System.out.println("Message Completed Successfully - Message Id - "+msgId + " producer Id "+ producerId+
				" group Id" + groupId);
		this.msgStatus = MessageStatus.COMPLETED;
		this.processedByExtRsId = extRsId;
		GatewayReport.getInstance().addToCompletedMsg(this);
		
	}
	
	@Override
	public void error(int extRsId, String errorDesc) {
		System.out.println("Message Completed Successfully - Message Id - "+msgId + " producer Id "+ producerId+
				" group Id" + groupId);
		this.msgStatus = MessageStatus.ERROR;
		this.processedByExtRsId = extRsId;
		this.errorDesc = errorDesc;
		GatewayReport.getInstance().addToFailureMsg(this);
		
	}

	

}
