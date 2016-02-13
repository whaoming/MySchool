package com.wxxiaomi.myschool.bean;

import java.util.List;

import com.wxxiaomi.myschool.bean.office.UserInfo.UserCommonInfo;



public class R_Comment extends R_Base{

	public List<CommentItem> commentList;
//	public int replyCount;
	
	public static class CommentItem{
		/**
		 * id, content, date, reply, floor
		 */
		public int userid;
		public int id;
//		public int domainid;
//		public int delete;
		public String date;
		public String content;
		public int reply;
		public int floor;
		public UserCommonInfo userInfo = new UserCommonInfo();
		public Lost_Reply_CommentItem replyComment = new Lost_Reply_CommentItem();
		
		//无用的字段
//		public int domainid;
		
	}
	public static class Lost_Reply_CommentItem{
		public int floor;
		public String content;
		public UserCommonInfo userInfo = new UserCommonInfo();
	}
	
}
