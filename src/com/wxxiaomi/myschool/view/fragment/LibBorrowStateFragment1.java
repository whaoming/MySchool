package com.wxxiaomi.myschool.view.fragment;

import java.io.ByteArrayInputStream;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.wxxiaomi.myschool.GlobalParams;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.lib.BookBorrowedState;
import com.wxxiaomi.myschool.bean.lib.format.LibBorrowStateFormat;
import com.wxxiaomi.myschool.bean.lib.format.LibMainFormat;
import com.wxxiaomi.myschool.bean.lib.format.common.LibReceiverData;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.util.CommonUtil;
import com.wxxiaomi.myschool.view.adapter.LibBorrowStateColumnAdapter;
import com.wxxiaomi.myschool.view.custom.LoadingDialog;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2.OkButonnListener;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

public class LibBorrowStateFragment1 extends BaseFragment {

	private View view;
	private SwipeRefreshLayout v_refersh;
	private ListView lv_lv;
	private LibBorrowStateColumnAdapter adapter;
	private LoadingDialog loodingDialog;
	private MyCodeDialog2 codeDialog;
	private String tempCookie;
	private LibBorrowStateFormat currentPage;

	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_borrowstate, null);
		lv_lv = (ListView) view.findViewById(R.id.lv_lv);
		v_refersh = (SwipeRefreshLayout) view.findViewById(R.id.v_refersh);
		v_refersh.setColorSchemeResources(R.color.color1, R.color.color2,
				R.color.color3, R.color.color4);
		v_refersh.setProgressViewOffset(false, 0, CommonUtil.dip2px(ct, 24));
		v_refersh.setRefreshing(true);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		loodingDialog = new LoadingDialog(ct).builder();
		codeDialog = new MyCodeDialog2(ct).builder();
		loodingDialog.setMessage("正在获取数据....");
		codeDialog.setOnOkButtonListener(new OkButonnListener() {
			@Override
			public void onClick(String input) {
				loodingDialog.show();
				LoginByNetBeforeGetData(input);
			}
		});
		if (GlobalParams.libInfo == null) {
			// 还未执行第一次登录
			LoginAndGetData();
		} else {
			getBorrowStateByWeb();
		}
		
	}

	

	/**
	 * 从服务器端获取图片验证码并且显示
	 */
	private void LoginAndGetData() {
		// 首先获取图片验证码
		codeDialog.show();
		showCodeDialogFromWeb(codeDialog);
	}

	/**
	 * 获取完图片验证码后，当点击确定按钮，必须联网登录
	 * @param input
	 */
	protected void LoginByNetBeforeGetData(final String input) {
		new AsyncTask<String, Void, LibReceiverData<LibMainFormat>>() {
			@Override
			protected LibReceiverData<LibMainFormat> doInBackground(String... params) {
				LibraryEngineImpl engine = new LibraryEngineImpl();
				return engine.LoginFromServer1("131110199", "987987987", input, tempCookie);
			}

			@Override
			protected void onPostExecute(LibReceiverData<LibMainFormat> result) {
				loodingDialog.dismiss();
				if (result.state == 200) {
					codeDialog.dismiss();
					// 获取数据
					getBorrowStateByWeb();
				} else {
					showMsgDialog(result.error);
				}
				super.onPostExecute(result);
			}
		}.execute();
	}

	private void showCodeDialogFromWeb(final MyCodeDialog2 codeDialog) {
		new AsyncTask<String, Void, LibReceiverData<String>>() {
			@Override
			protected LibReceiverData<String> doInBackground(String... params) {
				LibraryEngineImpl engine = new LibraryEngineImpl();
				LibReceiverData<String> picCodeAndCookieFromServer1 = engine.getPicCodeAndCookieFromServer();
				return picCodeAndCookieFromServer1;
			}

			@Override
			protected void onPostExecute(LibReceiverData<String> result) {
				tempCookie = result.cookie;
				showImageToDialog(result.codeByte);
				super.onPostExecute(result);
			}
		}.execute();

	}
	private void showImageToDialog(byte[] code){
		ByteArrayInputStream is = new ByteArrayInputStream(
				code);
		codeDialog.setImage(BitmapFactory.decodeStream(is));
	}

	/**
	 * 执行登录操作后，从服务器获取借阅情况
	 */
	public void getBorrowStateByWeb() {
		new AsyncTask<String, Void,  LibReceiverData<LibBorrowStateFormat>>() {
			@Override
			protected  LibReceiverData<LibBorrowStateFormat> doInBackground(String... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				return impl.getBorrowStateByWeb();
			}

			@Override
			protected void onPostExecute( LibReceiverData<LibBorrowStateFormat> result) {
				if(result.state == 200){
					currentPage = result.infos;
					processData(currentPage.columns);
				}else if(result.state == 1){
					tempCookie = result.cookie;
					showImageToDialog(result.codeByte);
					codeDialog.show();
					
				}
				
				super.onPostExecute(result);
			}
		}.execute();
	}

	// /**
	// * 获取借阅状态
	// */
	// private void getBorrowStateByNet() {
	// new AsyncTask<String, Void, ResponseData<Html_Lib_BorrowedState>>() {
	// @Override
	// protected ResponseData<Html_Lib_BorrowedState> doInBackground(
	// String... params) {
	// LibraryEngineImpl impl = new LibraryEngineImpl();
	// ResponseData<Html_Lib_BorrowedState> borrowedState = impl
	// .getBorrowedState(fragmentData.getCookie(),
	// fragmentData.getBookBorrowedUrl(), "");
	// return borrowedState;
	// }
	//
	// @Override
	// protected void onPostExecute(
	// ResponseData<Html_Lib_BorrowedState> result) {
	// if (result.isSuccess()) {
	// // 获取成功
	// currentPageBean = result.getObj();
	// processData(currentPageBean.getColumns());
	// } else {
	// Log.i("wang", "获取失败,登录会话过期");
	// // 获取失败,登录会话过期
	// interFace.onFragmentCallback(LibBorrowStateFragment1.this,
	// ConstantValue.STATE_LIBOUTTIME, null);
	// }
	// super.onPostExecute(result);
	// }
	// }.execute();
	// }

	protected void processData(List<BookBorrowedState> columns) {
		if (adapter == null) {
			adapter = new LibBorrowStateColumnAdapter(ct, currentPage.columns);
			lv_lv.setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		v_refersh.setRefreshing(false);
	}

}
