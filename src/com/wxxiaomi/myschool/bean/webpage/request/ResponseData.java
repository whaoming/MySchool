package com.wxxiaomi.myschool.bean.webpage.request;


public class ResponseData<T>{

	private boolean success;
	private String error;
	private T obj;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
