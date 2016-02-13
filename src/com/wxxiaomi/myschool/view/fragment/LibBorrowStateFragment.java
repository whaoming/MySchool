package com.wxxiaomi.myschool.view.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.webpage.BookBorrowedState;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Lib_BorrowedState;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Lib_Main;
import com.wxxiaomi.myschool.bean.webpage.request.ResponseData;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.util.CommonUtil;
import com.wxxiaomi.myschool.view.activity.HomeActivity1;
import com.wxxiaomi.myschool.view.activity.HomeActivity1.LibMainChangeListener;
import com.wxxiaomi.myschool.view.adapter.LibBorrowStateColumnAdapter;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

public class LibBorrowStateFragment extends BaseFragment {

	View view;
	private Html_Lib_Main fragmentData;
	private Html_Lib_BorrowedState currentPageBean;
	private SwipeRefreshLayout v_refersh;
	private ListView lv_lv;
	private LibBorrowStateColumnAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_borrowstate, null);
		lv_lv = (ListView) view.findViewById(R.id.lv_lv);
		v_refersh = (SwipeRefreshLayout) view.findViewById(R.id.v_refersh);
		v_refersh.setColorSchemeResources(R.color.color1,
				R.color.color2, R.color.color3, R.color.color4);
		v_refersh.setProgressViewOffset(false, 0, CommonUtil.dip2px(ct, 24));
		v_refersh.setRefreshing(true);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		if (getActivity() instanceof HomeActivity1) {
			HomeActivity1 activity = (HomeActivity1) getActivity();
			fragmentData = activity.getLibMain();
			activity.setLibMainChangeListener(new LibMainChangeListener() {
				@Override
				public void change(Html_Lib_Main mains) {
					// ConstantValue.isLibLogin = true;
					fragmentData = mains;
					getBorrowStateByNet();
				}
			});
		}
		if (fragmentData == null) {
			// 图书馆未登录
			interFace.onFragmentCallback(this, ConstantValue.LIBNOLOGIN,
					null);
		}else
		{
			getBorrowStateByNet();
		}
	}

	/** 
	 * 获取借阅状态
	 */
	private void getBorrowStateByNet() {
		new AsyncTask<String, Void, ResponseData<Html_Lib_BorrowedState>>() {
			@Override
			protected ResponseData<Html_Lib_BorrowedState> doInBackground(
					String... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				ResponseData<Html_Lib_BorrowedState> borrowedState = impl
						.getBorrowedState(fragmentData.getCookie(),
								fragmentData.getBookBorrowedUrl(), "");
				return borrowedState;
			}

			@Override
			protected void onPostExecute(
					ResponseData<Html_Lib_BorrowedState> result) {
				if (result.isSuccess()) {
					// 获取成功
					currentPageBean = result.getObj();
					processData(currentPageBean.getColumns());
				} else {
					Log.i("wang", "获取失败,登录会话过期");
					// 获取失败,登录会话过期
					interFace.onFragmentCallback(LibBorrowStateFragment.this,
							ConstantValue.STATE_LIBOUTTIME, null);
				}
				super.onPostExecute(result);
			}
		}.execute();
	}

	protected void processData(List<BookBorrowedState> columns) {
		if(adapter == null){
			adapter = new LibBorrowStateColumnAdapter(ct, currentPageBean.getColumns());
			lv_lv.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
		v_refersh.setRefreshing(false);
	}

}
