package com.wxxiaomi.myschool;

import com.wxxiaomi.myschool.bean.R_User.UserInfo;
import com.wxxiaomi.myschool.util.SharePrefUtil;
import com.wxxiaomi.myschool.view.activity.HomeActivity1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		checkParmas();
		finish();
//		checkLogin();
		
	}

	/**
	 * 检查是不是第一次使用
	 * 检查有没有记住账号，有就要取出缓存存到glo中，再进入home
	 * 没有记住账号：直接进入home
	 */
	private void checkParmas() {
		boolean isFirstUse = SharePrefUtil.getBoolean(this, "isFirstUse", false);
		if(!isFirstUse){//不是第一次使用
			Intent intent = new Intent(MainActivity.this,HomeActivity1.class);
			boolean isRemUser = SharePrefUtil.getBoolean(this, "isRemUser", false);
			if(isRemUser){//有记住账号
				UserInfo userInfo = (UserInfo) SharePrefUtil.getObj(this, "userInfo");
//				GlobalParams.userInfo = userInfo;
				if(checkUserInfo(userInfo.username,userInfo.password)){
					//验证通过的情况下
					
				}else{
					//验证不通过或者网络不通
					
				}
			}else{//没记住账号的情况下
				startActivity(intent);
			}
		}else{
			//第一次打开app的情况下
		}
		
		
	}

	/**
	 * 验证缓存中的userinfo的账号密码是否正确
	 * @param username
	 * @param password
	 */
	private boolean checkUserInfo(String username, String password) {
		return false;
	}

//	private void checkLogin() {
////		LoginByNet("131110199","987987987ww");
////		Intent intent = new Intent(this,HomeActivity1.class);
////		startActivity(intent);
//	}

//	private void LoginByNet(final String officeUserName, final String officePassword) {
//		new AsyncTask<String, Void, ResponseData<Html_Main>>() {
//			@Override
//			protected ResponseData<Html_Main> doInBackground(String... params) {
//				OfficeEngineImpl impl = new OfficeEngineImpl();
//				Log.i("wang", "doInBackground");
//				return impl.getOfficeMainHtml2BeanByOne(officeUserName,officePassword);
//			}
//
//			@Override
//			protected void onPostExecute(ResponseData<Html_Main> result) {
//				Intent intent = new Intent(MainActivity.this,HomeActivity1.class);
//				if (result.isSuccess()) {
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("info", result.getObj());
//					intent.putExtra("value", bundle);
//					intent.putExtra("isLoginSuccess", true);
////					ConstantValue.isOfficeLogin = true;
//				} else {
//					intent.putExtra("isLoginSuccess", false);
//				}
//				startActivity(intent);
//				finish();
//				super.onPostExecute(result);
//			}
//		}.execute();
//		
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
