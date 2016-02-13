package com.wxxiaomi.myschool.bean.lib.format;

import java.util.HashMap;
import java.util.Map;

import com.wxxiaomi.myschool.bean.lib.LibInfomation.Behave;
import com.wxxiaomi.myschool.bean.lib.LibUserInfo;

/**
 * 做登录操作的时候从服务器接收的数据的对应实体
 * @author Administrator
 *
 */
public class R_LibMain{
//	public String cookie;
	/**
	 * 参数集合
	 */
	public Map<String,Behave> behaves = new HashMap<String, Behave>();
	
	public LibUserInfo userinfo;
	/**
	 * 状态码
	 */
//	public int state;
	/**
	 * 如果
	 */
//	public byte[] picByte;
	
	
}
