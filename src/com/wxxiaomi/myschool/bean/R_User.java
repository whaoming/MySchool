package com.wxxiaomi.myschool.bean;

import java.io.Serializable;

@Deprecated
public class R_User extends R_Base {
	
	public UserInfo userInfo;
	public class UserInfo{
		public int id;
		public String password;
		public String sex;
		public String tname;
		public String username;
		public UserCommonInfo info;
	}
	public static class UserCommonInfo implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public UserCommonInfo() {
			super();
		}
		public String description;
		public String name;
		public String pic;
		public int officeId;
		
	}

}
