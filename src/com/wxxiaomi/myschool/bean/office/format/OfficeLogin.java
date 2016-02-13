package com.wxxiaomi.myschool.bean.office.format;

import java.util.HashMap;
import java.util.Map;

import com.wxxiaomi.myschool.bean.office.OfficeBehave;
import com.wxxiaomi.myschool.bean.office.UserInfo;

/**
 * 登录操作服务器和移动端的数据格式
 * @author Administrator
 *
 */
public class OfficeLogin {

	public Map<String, OfficeBehave> behaves = new HashMap<>();

	public UserInfo userinfo;
	
	
}
