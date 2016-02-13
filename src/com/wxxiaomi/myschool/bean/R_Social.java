package com.wxxiaomi.myschool.bean;

import java.io.Serializable;
import java.util.List;

import com.wxxiaomi.myschool.bean.office.UserInfo.UserCommonInfo;


public class R_Social extends R_Base {

	public List<SocialItem> socialList;
	
	public static class SocialItem implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public int id;
		public int userid;
		public String title;
		public int likes;
		public String pic;
		public String detail;
		public String date;
		public UserCommonInfo userInfo;
	}
	
}
