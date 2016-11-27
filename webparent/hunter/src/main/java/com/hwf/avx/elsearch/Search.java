package com.hwf.avx.elsearch;

public class Search {
	private String index = "avmo.pw(new)";
	private String type = "movie";
	private String queryJSON;
	public String getIndex() {
		return index;
	}
	public Search setIndex(String index) {
		this.index = index;
		return this;
	}
	public String getType() {
		return type;
	}
	public Search setType(String type) {
		this.type = type;
		return this;
	}
	public String getQueryJSON() {
		return queryJSON;
	}
	public Search setQueryJSON(String queryJSON) {
		this.queryJSON = queryJSON;
		return this;
	}
	
	
}
