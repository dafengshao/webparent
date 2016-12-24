package com.github.nfs.model;


public class ResultMessage {
	public static final ResultMessage OK = new ResultMessage(true);
	private boolean success;
	private String code;
	private Object content;
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getContent() {
		return (T) content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	ResultMessage(boolean success){
		
	}
	ResultMessage(boolean success,String code){
		
	}
	ResultMessage(boolean success,String code,Object content){
		
	}
}

