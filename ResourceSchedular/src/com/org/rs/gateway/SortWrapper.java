package com.org.rs.gateway;

public class SortWrapper {

	private MsgFieldNames msgFieldName;
	private SortType sortType;
	
	public SortWrapper(MsgFieldNames msgFieldName, SortType sortType) {
		super();
		this.msgFieldName = msgFieldName;
		this.sortType = sortType;
	}
	public SortWrapper() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the msgFieldName
	 */
	public MsgFieldNames getMsgFieldName() {
		return msgFieldName;
	}
	/**
	 * @param msgFieldName the msgFieldName to set
	 */
	public void setMsgFieldName(MsgFieldNames msgFieldName) {
		this.msgFieldName = msgFieldName;
	}
	/**
	 * @return the sortType
	 */
	public SortType getSortType() {
		return sortType;
	}
	/**
	 * @param sortType the sortType to set
	 */
	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((msgFieldName == null) ? 0 : msgFieldName.hashCode());
		result = prime * result
				+ ((sortType == null) ? 0 : sortType.hashCode());
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
		SortWrapper other = (SortWrapper) obj;
		if (msgFieldName != other.msgFieldName) {
			return false;
		}
		if (sortType != other.sortType) {
			return false;
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SortWrapper [msgFieldName=");
		builder.append(msgFieldName);
		builder.append(", sortType=");
		builder.append(sortType);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
