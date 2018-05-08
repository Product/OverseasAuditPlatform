package com.thinvent.rules.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Page implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int startPosition;
	private int pageNumber;
	//private Class mappedClass;
	private int totalPages;
	private int resultsPerPage;
	private String orderBy;
	private boolean desc;
	private List objectList;
	private Map filters;
	private int resultsCount;
	private String defaultBy;
	private String defaultValue;
	

	
	/**
	 * @return the defaultBy
	 */
	public String getDefaultBy() {
		return defaultBy;
	}

	/**
	 * @param defaultBy the defaultBy to set
	 */
	public void setDefaultBy(String defaultBy) {
		this.defaultBy = defaultBy;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Page(int startPosition,int resultsPerPage,String orderBy,boolean desc){
		this.startPosition = startPosition;
		//this.entityName = entityName;
		this.resultsPerPage = resultsPerPage;
		this.orderBy = orderBy;
		this.desc = desc;
	}
	
	public Page(int startPosition,int resultsPerPage,String orderBy,boolean desc, String defBy, String defValue){
		this.startPosition = startPosition;
		//this.entityName = entityName;
		this.resultsPerPage = resultsPerPage;
		this.orderBy = orderBy;
		this.desc = desc;
		this.defaultBy=defBy;
		this.defaultValue=defValue;
	}

	public Page(int startPosition,int resultsPerPage){
		this.startPosition = startPosition;
		//this.entityName = entityName;
		this.resultsPerPage = resultsPerPage;
	}
	
	
	public List getObjectList() {
		return objectList;
	}
	public void setObjectList(List objectList) {
		this.objectList = objectList;
	}

	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getStartPosition() {
		return startPosition;
	}

	public void setStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}


	public int getResultsPerPage() {
		return resultsPerPage;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public boolean isDesc() {
		return desc;
	}

	public Map<String,String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String,String> filters) {
		this.filters = filters;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getResultsCount() {
		return resultsCount;
	}

	public void setResultsCount(int resultsCount) {
		this.resultsCount = resultsCount;
	}

	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
