package com.wxxiaomi.myschool.engine;

import com.google.gson.Gson;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.bean.R_News;
import com.wxxiaomi.myschool.net.HttpClientUtil;
import com.wxxiaomi.myschool.util.SharePrefUtil;

import android.content.Context;
import android.util.Log;


public class InformationEngineImpl {
	public R_News getNewsList(int page, boolean isCache,Context ct) {
		String url = ConstantValue.LOTTERY_URI.concat(ConstantValue.NEWSGET)
				+ "&page=" + page;
		if (isCache) {
			String result = SharePrefUtil.getString(ct, url, "");
			if(result!=""){
				Gson gson = new Gson();
				try{
					R_News r_news = gson.fromJson(result, R_News.class);
					return r_news;
				}catch(Exception e){
					
				}
				return null;
				
			}else{
				return null;
			}
		} else {
			String json = HttpClientUtil.doGet(url);
			Log.i("wang", "json="+json);
			try {
				if(page == 0){
					SharePrefUtil.saveString(ct, url, json);
				}
				Gson gson = new Gson();
				R_News r_news = gson.fromJson(json, R_News.class);
				return r_news;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 4数据持久化
		return null;
	}

}
