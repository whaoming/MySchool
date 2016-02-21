package com.wxxiaomi.myschool.bean.lib;


public class BookCollectLocation {
	/**
	 * 卷期
	 */
	private String juanqi;

	/**
	 * 年代
	 */
	private String year;

	private String pointState;

	/**
	 * 索取号
	 */
	private String number;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 状态
	 */
	private String state;

	/**
	 * 登录号
	 */
	private String loginNumber;

	/**
	 * 馆藏地
	 */
	private String collectionLocation;

	

	public String getJuanqi() {
		return juanqi;
	}

	public void setJuanqi(String juanqi) {
		this.juanqi = juanqi;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getPointState() {
		return pointState;
	}

	public void setPointState(String pointState) {
		this.pointState = pointState;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLoginNumber() {
		return loginNumber;
	}

	public void setLoginNumber(String loginNumber) {
		this.loginNumber = loginNumber;
	}

	public String getCollectionLocation() {
		return collectionLocation;
	}

	public void setCollectionLocation(String collectionLocation) {
		this.collectionLocation = collectionLocation;
	}

	public BookCollectLocation(String collectionLocation,String number, String loginNumber,String juanqi, String year, String state, 
			 String type) {
		super();
		this.juanqi = juanqi;
		this.year = year;
		this.number = number;
		this.type = type;
		this.state = state;
		this.loginNumber = loginNumber;
		this.collectionLocation = collectionLocation;
	}
	
	
}
