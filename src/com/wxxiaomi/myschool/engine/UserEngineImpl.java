package com.wxxiaomi.myschool.engine;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.GlobalParams;
import com.wxxiaomi.myschool.bean.R_User;
import com.wxxiaomi.myschool.bean.office.format.OfficeLogin;
import com.wxxiaomi.myschool.bean.office.format.common.OfficeReceiveData;
import com.wxxiaomi.myschool.bean.office.local.GloUserInfo;
import com.wxxiaomi.myschool.net.HttpClientUtil;

public class UserEngineImpl {
	
	@Deprecated
	public R_User getUserLoginInfoByList(boolean isCache, String username,
			String password, Context ct) {
		// 联网获取数据
		// 1.设置参数
		if (isCache) {
			return null;
		} else {
			String json = HttpClientUtil.doPost(
					ConstantValue.LOTTERY_URI.concat(ConstantValue.LOGIN),
					"username="+username+"&password="+password);
			try {
//				Log.i("wang", "json="+json);
				Gson gson = new Gson();
				R_User fromJson = gson.fromJson(json, R_User.class);
				return fromJson;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 4数据持久化
		}
		return null;
	}
	
	public OfficeReceiveData<OfficeLogin> LoginByServer(String username,String password){
		String url = ConstantValue.LOTTERY_URI+"/UserServlet?action=login&username="+username+"&password="+password;
		String json = HttpClientUtil.doGet(url);
		try{
			Log.i("wang", "登录操作的json="+json);
			Gson gson = new Gson();
			OfficeReceiveData<OfficeLogin> fromJson = gson.fromJson(json, new TypeToken<OfficeReceiveData<OfficeLogin>>(){}.getType());
			
			if(fromJson.state == 200){
				Log.i("wang", "username="+fromJson.infos.userinfo.officeUserInfo.username);
				GloUserInfo gloUserInfo  = new GloUserInfo();
				gloUserInfo.userInfo = fromJson.infos.userinfo;
				gloUserInfo.tempUrl = fromJson.tempUrl;
				GlobalParams.gloUserInfo = gloUserInfo;
				ConstantValue.isLogin = true;
			}
			return fromJson;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
}
