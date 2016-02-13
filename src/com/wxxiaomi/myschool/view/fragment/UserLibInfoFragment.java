package com.wxxiaomi.myschool.view.fragment;

import java.io.ByteArrayInputStream;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.lib.format.R_LibLoginCode;
import com.wxxiaomi.myschool.bean.lib.format.R_LibMain;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.view.custom.LoadingDialog;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2.OkButonnListener;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

public class UserLibInfoFragment extends BaseFragment implements
		OnClickListener {

	View view;
	private EditText et_username;
	private EditText et_password;
	private Button btn_ok;
	private R_LibLoginCode info;
//	private TextView tv_test;

	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_userinfo_library, null);
		et_username = (EditText) view.findViewById(R.id.et_username);
		et_password = (EditText) view.findViewById(R.id.et_password);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(this);
//		tv_test = (TextView) view.findViewById(R.id.tv_test);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
//		 getPicCodeAndCookieFromServer();

	}

	private void getPicCodeAndCookieFromServer(final MyCodeDialog2 dialog2) {
		new AsyncTask<String, Void, R_LibLoginCode>() {
			@Override
			protected R_LibLoginCode doInBackground(String... params) {
				R_LibLoginCode r = null;
				LibraryEngineImpl engine = new LibraryEngineImpl();
				r = engine.getPicCodeAndCookieFromServer();
				return r;
			}

			@Override
			protected void onPostExecute(R_LibLoginCode result) {
				// if (result != null && result.success == 1) {
				info = result;
				processData(dialog2);

				// } else {

				// } 
				super.onPostExecute(result);
			}
		}.execute();
	}

	protected void processData(MyCodeDialog2 dialog2) {
		ByteArrayInputStream is = new ByteArrayInputStream(
				info.picByte);
		// image.setImageBitmap(BitmapFactory.decodeStream(is));
		dialog2.setImage(BitmapFactory.decodeStream(is));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_ok:
			getCode();
			break;

		default:
			break;
		}

	}
	 LoadingDialog loodingDialog ;
	 MyCodeDialog2 codeDialog ;
	private void getCode() {
		loodingDialog = new LoadingDialog(ct).builder();
		codeDialog = new MyCodeDialog2(ct).builder();
		loodingDialog.setMessage("正在获取数据....");
		codeDialog.show();
		getPicCodeAndCookieFromServer(codeDialog);
		codeDialog.setOnOkButtonListener(new OkButonnListener() {
			@Override
			public void onClick(String input) {
				loodingDialog.show();
				Login(input);
			}
		});

	}

	/**
	 * 封装用户名，密码，验证码，cookie发送给web服务器进行登录操作
	 * @param input 
	 */
	protected void Login(final String input) {
		final String username = et_username.getText().toString().trim();
		final String password = et_password.getText().toString().trim();
		new AsyncTask<String, Void, R_LibMain>() {
			@Override
			protected R_LibMain doInBackground(String... params) {
				LibraryEngineImpl engine = new LibraryEngineImpl();
				R_LibMain loginFromServer = engine.LoginFromServer(username,password,input,info.cookie);
				return loginFromServer;
			}

			@Override
			protected void onPostExecute(R_LibMain result) {
//				if(result.success == 1){
//					loodingDialog.dismiss();
//					codeDialog.dismiss();
//					tv_test.setText(result.userinfo.toString());
//				}else {
//					loodingDialog.dismiss();
//					showMsgDialog(result.error);
//				}
				super.onPostExecute(result);
			}
		}.execute();
	}

}
