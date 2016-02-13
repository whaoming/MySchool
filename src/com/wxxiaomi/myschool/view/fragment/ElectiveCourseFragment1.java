package com.wxxiaomi.myschool.view.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.office.ElectiveCourseColumn;
import com.wxxiaomi.myschool.bean.office.format.common.OfficeReceiveData;
import com.wxxiaomi.myschool.bean.webpage.request.ResponseData;
import com.wxxiaomi.myschool.engine.OfficeEngineImpl;
import com.wxxiaomi.myschool.util.CommonUtil;
import com.wxxiaomi.myschool.view.activity.HomeActivity1;
import com.wxxiaomi.myschool.view.activity.HomeActivity1.MainChangeListener;
import com.wxxiaomi.myschool.view.adapter.OfficeElectiveCourseAdapter;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

// 改
public class ElectiveCourseFragment1 extends BaseFragment {

	private SwipeRefreshLayout view_refersh;
	private OfficeElectiveCourseAdapter adapter;
	private ExpandableListView lv_lv;
	LayoutInflater inflater;
	protected List<ElectiveCourseColumn> info;
	
	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_electivecourse, null);
		lv_lv = (ExpandableListView) view.findViewById(R.id.lv_lv);
		view_refersh = (SwipeRefreshLayout) view.findViewById(R.id.view_refersh);
		view_refersh.setColorSchemeResources(R.color.color1,
				R.color.color2, R.color.color3, R.color.color4);

		view_refersh.setProgressViewOffset(false, 0, CommonUtil.dip2px(ct, 24));
		view_refersh.setRefreshing(true);
		this.inflater = inflater;
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
//		if (getActivity() instanceof HomeActivity1) {
//			HomeActivity1 activity = (HomeActivity1) getActivity();
//			fragmentData = activity.getOfficeHtmlMain();
//			activity.setMainChangeListener(new MainChangeListener() {
//				@Override
//				public void change(Html_Main main) {
//					fragmentData = main;
//					getElectiveCourseByNet();
//				}
//			});
//		}
//		if(fragmentData == null){
//			interFace.onFragmentCallback(this, ConstantValue.OfficeNoLogin, null);
//		}else{
//			getElectiveCourseByNet();
//		}
	
		getElectiveCourseByServer();
	}

	/**
	 * 从服务器获取选修情况
	 */
	private void getElectiveCourseByServer() {
		new AsyncTask<String, Void, OfficeReceiveData<List<ElectiveCourseColumn>>>() {
			@Override
			protected OfficeReceiveData<List<ElectiveCourseColumn>> doInBackground(String... params) {
				OfficeEngineImpl impl = new OfficeEngineImpl();
//				return impl.getOfficeElectiveCourse2Bean(fragmentData.getElectiveCourseUrl(), fragmentData.getFromUrl());
				return null;
			}

			@Override
			protected void onPostExecute(OfficeReceiveData<List<ElectiveCourseColumn>> result) {
				if(result.state == 200){
					//获取成功
//					Log.i("wang", "fragment-result.isSuccess()=true");
					info = result.infos;
					processData(info);
				}else{
					//获取失败,登录会话过期
//					Log.i("wang", "fragment-result.isSuccess()=false");
					interFace.onFragmentCallback(ElectiveCourseFragment1.this, 0, null);
				}
				super.onPostExecute(result);
			}
		}.execute();
		
	}


	protected void processData(List<ElectiveCourseColumn> columns) {
		// TODO Auto-generated method stub
		if(adapter == null){
			adapter = new OfficeElectiveCourseAdapter(ct,inflater, info);
//			adapter = new OfficeElectiveCourseColumnAdapter(ct, info.getColumns());
			lv_lv.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
		view_refersh.setRefreshing(false);
	}

}
