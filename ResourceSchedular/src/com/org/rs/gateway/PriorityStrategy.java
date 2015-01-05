package com.org.rs.gateway;

import java.util.List;

// TODO chk for serialization
public class PriorityStrategy {

	
	private String strategyName;
	private StrategyType strategyTypes;
	private List<SortWrapper> sortWrapperList;
	
	
	
	public PriorityStrategy() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PriorityStrategy(String strategyName, StrategyType strategyTypesAry,
			List<SortWrapper> sortWrapperList) {
		super();
		this.strategyName = strategyName;
		this.strategyTypes = strategyTypesAry;
		this.sortWrapperList = sortWrapperList;
	}
	/**
	 * @return the strategyName
	 */
	public String getStrategyName() {
		return strategyName;
	}
	/**
	 * @param strategyName the strategyName to set
	 */
	public void setStrategyName(String strategyName) {
		this.strategyName = strategyName;
	}
	/**
	 * @return the strategyTypesAry
	 */
	public StrategyType getStrategyTypes() {
		return strategyTypes;
	}
	/**
	 * @param strategyTypesAry the strategyTypesAry to set
	 */
	public void setStrategyTypes(StrategyType strategyTypes) {
		this.strategyTypes = strategyTypes;
	}
	/**
	 * @return the sortWrapperList
	 */
	public List<SortWrapper> getSortWrapperList() {
		return sortWrapperList;
	}
	/**
	 * @param sortWrapperList the sortWrapperList to set
	 */
	public void setSortWrapperList(List<SortWrapper> sortWrapperList) {
		this.sortWrapperList = sortWrapperList;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sortWrapperList == null) ? 0 : sortWrapperList.hashCode());
		result = prime * result
				+ ((strategyName == null) ? 0 : strategyName.hashCode());
		result = prime
				* result
				+ ((strategyTypes == null) ? 0 : strategyTypes.hashCode());
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
		PriorityStrategy other = (PriorityStrategy) obj;
		if (sortWrapperList == null) {
			if (other.sortWrapperList != null) {
				return false;
			}
		} else if (!sortWrapperList.equals(other.sortWrapperList)) {
			return false;
		}
		if (strategyName == null) {
			if (other.strategyName != null) {
				return false;
			}
		} else if (!strategyName.equals(other.strategyName)) {
			return false;
		}
		if (strategyTypes != other.strategyTypes) {
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
		builder.append("PriorityStrategy [strategyName=");
		builder.append(strategyName);
		builder.append(", strategyTypes=");
		builder.append(strategyTypes);
		builder.append(", sortWrapperList=");
		builder.append(sortWrapperList);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
	
	
	
	
	
}
