package com.hwf.avx.elsearch;

import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;

public class ScrollSearch extends Search{
	
	private SearchType searchType = SearchType.QUERY_THEN_FETCH;
	private TimeValue timeValue = new TimeValue(60*1000);
	private int size = 10;
	private String srollId;
	private int pageNo = 1;
	
	public SearchType getSearchType() {
		return searchType;
	}
	public ScrollSearch setSearchType(SearchType searchType) {
		this.searchType = searchType;
		return this;
	}
	public TimeValue getTimeValue() {
		return timeValue;
	}
	public ScrollSearch setTimeValue(TimeValue timeValue) {
		this.timeValue = timeValue;
		return this;
	}
	public int getSize() {
		return size;
	}
	public ScrollSearch setSize(int size) {
		this.size = size;
		return this;
	}
	public String getSrollId() {
		return srollId;
	}
	public ScrollSearch setSrollId(String srollId) {
		this.srollId = srollId;
		return this;
	}
	public int getPageNo() {
		return pageNo;
	}
	public ScrollSearch setPageNo(int pageNo) {
		this.pageNo = pageNo;
		return this;
	}
	
}
