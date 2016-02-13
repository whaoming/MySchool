package com.wxxiaomi.myschool.view.activity;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.office.format.OfficeLogin;
import com.wxxiaomi.myschool.bean.office.format.common.OfficeReceiveData;
import com.wxxiaomi.myschool.engine.UserEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;

public class LoginActivity extends BaseActivity {

	private EditText et_username;
	private EditText et_password;
	private Button btn_ok;
	
	@Override
	protected void initView() {
		setContentView(R.layout.activity_login);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_ok = (Button) findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);

	}

	@Override
	protected void initData() {
		
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			showLoadingDialog("正在登录中...");
//			Login(et_username.getText().toString().trim(),
//					et_password.getText().toString().trim());
			LoginByServer(et_username.getText().toString().trim(),
					et_password.getText().toString().trim());
			break;

		default:
			break;
		}

	}

	private void LoginByServer(final String username, final String password){
		new AsyncTask<String, Void, OfficeReceiveData<OfficeLogin>>() {
			@Override
			protected OfficeReceiveData<OfficeLogin> doInBackground(String... params) {
				UserEngineImpl engine = new UserEngineImpl();
				OfficeReceiveData<OfficeLogin> loginByServer = engine.LoginByServer(username, password);
				return loginByServer;
			}
			@Override
			protected void onPostExecute(OfficeReceiveData<OfficeLogin> result) {
				closeLoadingDialog();
//				if(result != null && "login".equals(result.response)){
//					if(result.success == 1){//登陆成功
//						GlobalParams.userInfo = result.userInfo;
//						ConstantValue.isLogin = true;
//						setResult(ConstantValue.LOGINACTIVITY);
//						finish();
//					}else{//密码错误
//						showMsgDialog(result.error);
//					}
//				}else{//连接不到服务器
//					showMsgDialog("连接不到服务器");
//				}
				if(result != null){
					if(result.state == 200){
//						GlobalParams.userInfo = result.infos.userinfo;
//						ConstantValue.isLogin = true;
//						Log.i("wang", )
						ConstantValue.isLogin = true;
						setResult(ConstantValue.LOGINACTIVITY);
						finish();
					}else{
						showMsgDialog(result.error);
					}
				}else{
					showMsgDialog("连接不到服务器");
				}
				
				super.onPostExecute(result);
			}
		}.execute();
	}
//	}
	
//	@SuppressWarnings("unused")
//	@Deprecated
//	private void Login(final String username, final String password) {
//		new AsyncTask<String, Void, R_User>() {
//			@Override
//			protected R_User doInBackground(String... params) {
//				UserEngineImpl engine = new UserEngineImpl();
//				R_User result = engine.getUserLoginInfoByList(false,username,password,LoginActivity.this);
//				return result;
//			}
//			@Override
//			protected void onPostExecute(R_User result) {
//				closeLoadingDialog();
//				if(result != null && "login".equals(result.response)){
//					if(result.success == 1){//登陆成功
////						GlobalParams.userInfo = result.userInfo;
//						ConstantValue.isLogin = true;
//						setResult(ConstantValue.LOGINACTIVITY);
//						finish();
//					}else{//密码错误
//						showMsgDialog(result.error);
//					}
//				}else{//连接不到服务器
//					showMsgDialog("连接不到服务器");
//				}
//				super.onPostExecute(result);
//			}
//		}.execute();
//	}

}
