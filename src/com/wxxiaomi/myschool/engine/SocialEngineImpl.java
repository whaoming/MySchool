package com.wxxiaomi.myschool.engine;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.bean.R_Comment;
import com.wxxiaomi.myschool.bean.R_Social;
import com.wxxiaomi.myschool.net.HttpClientUtil;
import com.wxxiaomi.myschool.util.SharePrefUtil;

public class SocialEngineImpl {

	public R_Comment getSocialItemCommentList(int page,int domainid){
		//type = 1
		String url = ConstantValue.LOTTERY_URI.concat(ConstantValue.GETSOCIALITEMCOMMENTLIST)
				+"&type=1&page="+page+"&domainid="+domainid;
		String json = HttpClientUtil.doGet(url);
		try {
			Log.i("wang", "获取评论的json=" + json);
			Gson gson = new Gson();
			R_Comment r_comment = gson.fromJson(json, R_Comment.class);
			return r_comment;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 4数据持久化
		return null;
	}
	
	/**
	 * 获取当前活动列表
	 */
	public R_Social getSocialList(int type, int page, boolean isCache,
			Context ct) {
		String url;
		// if (GlobalParams.userInfo != null) {
		// url = ConstantValue.LOTTERY_URI.concat(ConstantValue.GETSOCIALLIST)
		// + "&type=" + type + "&page=" + page + "&userid="
		// + GlobalParams.userInfo.id;
		// } else {
		url = ConstantValue.LOTTERY_URI.concat(ConstantValue.GETSOCIALLIST)
				+ "&type=" + type + "&page=" + page;
		// }

		// 这个userid有问题，因为不知道用户是否有登陆，如果登陆就有，没登陆就会空指针异常
		if (isCache) {
			// String result = SharePrefUtil
			// .getString(ct, "sociallist" + type, "");
			String result = SharePrefUtil.getString(ct, url, "");
			if (result != "") {
				// List<Social> cachelist = JSON.parseArray(result,
				// Social.class);
				// if (usersocial != "") {
				// GlobalParams.socials = JSON.parseArray(usersocial,
				// Integer.class);
				// }
				// return cachelist;
				Gson gson = new Gson();
				R_Social r_social = gson.fromJson(result, R_Social.class);
				return r_social;
			} else {
				return null;
			}
		} else {
			String json = HttpClientUtil.doGet(url);
			try {
				// JSONObject object = new JSONObject(json);
				// String responselist = object.getString("sociallist");
				// /**
				// * 只有page=0才有获取参加的东西，因为page不等于0就是下啦刷新，下啦刷新只要
				// * activity不销毁，他的那个activity那个成员变量usersocial是不会销毁的,
				// * 然后只要销毁的时候根据这个值有没有变化就可以判断要不要发送给服务器东西了
				// */
				// if (page == 0) {
				// if (GlobalParams.user != null) {
				// String usersocial = object.getString("usersocial");
				// if (usersocial != null) {
				// SharePrefUtil.saveString(ct, "usersocial", usersocial);
				// GlobalParams.socials = JSON.parseArray(usersocial,
				// Integer.class);
				// }
				// } else {// 进来这里的 ，就是又没缓存，又没有登陆的
				// SharePrefUtil.saveString(ct, "usersocial", "");
				// }
				//
				// SharePrefUtil.saveString(ct, "sociallist" + type,
				// responselist);
				// }
				// List<Social> socials = JSON.parseArray(responselist,
				// Social.class);
				// return socials;
				Log.i("wang", "json=" + json);
				SharePrefUtil.saveString(ct, url, json);
				Gson gson = new Gson();
				R_Social r_social = gson.fromJson(json, R_Social.class);
				return r_social;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 4数据持久化
			return null;
		}
	}
}
