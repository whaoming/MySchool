package com.wxxiaomi.myschool.engine;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.bean.R_Lost;
import com.wxxiaomi.myschool.net.HttpClientUtil;
import com.wxxiaomi.myschool.util.SharePrefUtil;

public class LostItemEngineImpl {

	public R_Lost getItemList(int page, boolean isCache, Context ct) {
		String url = ConstantValue.LOTTERY_URI
				.concat(ConstantValue.GETLOSTFOUNDITEMLIST) + "&page=" + page;
		if (isCache) {
			String result = SharePrefUtil.getString(ct, url, "");
			if (result != "") {
				Gson gson = new Gson();
				R_Lost r_lost = gson.fromJson(result, R_Lost.class);
				return r_lost;
			} else {
				return null;
			}
		} else {
			// 2.访问网络
//			HttpClientUtil util = new HttpClientUtil(); 
			String json = HttpClientUtil.doGet(url);
			Log.i("wang", "json="+json);
			try {
				if (page == 0) {
					SharePrefUtil.saveString(ct, url, json);
				}
				Gson gson = new Gson();
				R_Lost r_lost = gson.fromJson(json, R_Lost.class);
				return r_lost;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 4数据持久化
			return null;
		}

	}
}
