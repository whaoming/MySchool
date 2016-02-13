package com.wxxiaomi.myschool.bean.lib;

import java.io.Serializable;

public class LibUserInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 证 号： 131110199 
	 * 姓 名： 王浩明
	 *  类 型： 学生 
	 *  单 位： 计算机1305班 
	 *  当前状态 :正常 
	 *  电 话： 
	 *  手 机： 
	 *  备 注：
	 * Email地址： 
	 * 预约取书地点： 
	 * 您的预存金额为： 0.00 元
	 */
	/**
	 * 证 号
	 */
	public String number;
	/**
	 * 姓 名
	 */
	public String name;
	/**
	 * 类 型
	 */
	public String type;
	/**
	 * 单 位
	 */
	public String unit;
	/**
	 * 当前状态
	 */
	public String state;
	/**
	 * 电 话
	 */
	public String phone;
	/**
	 * 手 机
	 */
	public String mobile;
	/**
	 * Email地址
	 */
	public String email;
	/**
	 * 预约取书地点
	 */
	public String location;
	/**
	 * 您的预存金额
	 */
	public String money;

	

	public LibUserInfo(String number, String name, String type, String unit,
			String state, String phone, String mobile, String email,
			String location, String money) {
		super();
		this.number = number;
		this.name = name;
		this.type = type;
		this.unit = unit;
		this.state = state;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.location = location;
		// this.money = Double.valueOf(money);
		this.money = money;
	}

	public LibUserInfo() {
		super();
	}

	@Override
	public String toString() {
		return "LibUserInfo [number=" + number + ", name=" + name + ", type="
				+ type + ", unit=" + unit + ", state=" + state + ", phone="
				+ phone + ", mobile=" + mobile + ", email=" + email
				+ ", location=" + location + ", money=" + money + "]";
	}
}
