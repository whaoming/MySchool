package com.wxxiaomi.myschool.bean.lib;

import java.util.HashMap;
import java.util.Map;


/**
 * 全局图书馆相关信息
 * @author Administrator
 *
 */
public class LibInfomation {

	public String username;
	public String password;
	/**
	 * 服务器收到的回复,里面包括跟服务器打交道的信息
	 */
//	public R_LibMain receiveInfo;
	/**
	 * cookie
	 */
	public String cookie;
	/**
	 * 参数集合
	 */
	public Map<String,Behave> behaves = new HashMap<String, Behave>();
	
	/**
	 * 用户账号信息
	 */
	public LibUserInfo userinfo;
	
	public static class Behave{
		public String pars;
		public String url;
		
	}
}
