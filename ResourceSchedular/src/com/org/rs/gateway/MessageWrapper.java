package com.org.rs.gateway;

import com.org.rs.message.MessageImpl;

public class MessageWrapper  {
	
	private MessageImpl msg;
	
	private long sendOrderNo;
	private int gatewayId;
	private int batchId;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageWrapper [msg=");
		builder.append(msg);
		builder.append(", sendOrderNo=");
		builder.append(sendOrderNo);
		builder.append(", gatewayId=");
		builder.append(gatewayId);
		builder.append(", batchId=");
		builder.append(batchId);
		builder.append("]\n");
		return builder.toString();
	}
	
	/**
	 * @return the gatewayId
	 */
	public int getGatewayId() {
		return gatewayId;
	}
	/**
	 * @param gatewayId the gatewayId to set
	 */
	public void setGatewayId(int gatewayId) {
		this.gatewayId = gatewayId;
	}
	/**
	 * @return the batchId
	 */
	public int getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}
	public MessageWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageWrapper(MessageImpl msg, long sendOrderNo) {
		super();
		this.msg = msg;
		this.sendOrderNo = sendOrderNo;
	}
	/**
	 * @return the msg
	 */
	public MessageImpl getMsg() {
		return msg;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((msg == null) ? 0 : msg.hashCode());
		result = prime * result + (int) (sendOrderNo ^ (sendOrderNo >>> 32));
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
		MessageWrapper other = (MessageWrapper) obj;
		if (msg == null) {
			if (other.msg != null) {
				return false;
			}
		} else if (!msg.equals(other.msg)) {
			return false;
		}
		if (sendOrderNo != other.sendOrderNo) {
			return false;
		}
		return true;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(MessageImpl msg) {
		this.msg = msg;
	}
	/**
	 * @return the sendOrderNo
	 */
	public long getSendOrderNo() {
		return sendOrderNo;
	}
	/**
	 * @param sendOrderNo the sendOrderNo to set
	 */
	public void setSendOrderNo(long sendOrderNo) {
		this.sendOrderNo = sendOrderNo;
	}
	
	

}
