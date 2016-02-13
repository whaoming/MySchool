package com.wxxiaomi.myschool.bean.office;


public class UserInfo {
	public int id;
	public String description;
	public int libid;
	public String name;
	public String pic;
	public int officeid;
	public OfficeUserInfo officeUserInfo;
	public LibUserInfo libUserInfo;
	
	public static class OfficeUserInfo{
		public int id;
		public String username;
		public String password;
		public String sex;
		public String tname;
	}
	
	public static class LibUserInfo{
		public int id;
		public String username;
		public String password;
	}
	
	public static class UserCommonInfo{
		public String description;
		public String name;
		public String pic;
		public int officeId;
	}
}
