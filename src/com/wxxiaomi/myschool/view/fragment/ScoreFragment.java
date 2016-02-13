package com.wxxiaomi.myschool.view.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Main;
import com.wxxiaomi.myschool.bean.webpage.page.Score;
import com.wxxiaomi.myschool.bean.webpage.request.ResponseData;
import com.wxxiaomi.myschool.engine.OfficeEngineImpl;
import com.wxxiaomi.myschool.view.activity.HomeActivity1;
import com.wxxiaomi.myschool.view.activity.HomeActivity1.MainChangeListener;
import com.wxxiaomi.myschool.view.adapter.OfficeScoreAdapter;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;
@Deprecated
public class ScoreFragment extends BaseFragment {

	private Html_Main fragmentData;
	private Score info;
	private SwipeRefreshLayout view_refersh;
	private OfficeScoreAdapter adapter;
	private ExpandableListView lv_lv;
	private LayoutInflater inflater;
	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_scoreinfo, null);
		lv_lv = (ExpandableListView) view.findViewById(R.id.lv_lv);
		lv_lv.setGroupIndicator(null);
		view_refersh = (SwipeRefreshLayout) view.findViewById(R.id.view_refersh);
		
		view_refersh.setColorSchemeResources(R.color.color1,
				R.color.color2, R.color.color3, R.color.color4);

		view_refersh.setProgressViewOffset(false, 0, (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
						.getDisplayMetrics()));
		view_refersh.setRefreshing(true);
		this.inflater = inflater;
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		if (getActivity() instanceof HomeActivity1) {
			HomeActivity1 activity = (HomeActivity1) getActivity();
			fragmentData = activity.getOfficeHtmlMain();
			activity.setMainChangeListener(new MainChangeListener() {
				@Override
				public void change(Html_Main main) {
					fragmentData = main;
					getScoreByNet();
				}
			});
		}
		if(fragmentData == null){
			interFace.onFragmentCallback(this, ConstantValue.OfficeNoLogin, null);
		}else{
			getScoreByNet();
		}
		

	}

	private void getScoreByNet() {
		new AsyncTask<String, Void, ResponseData<Score>>() {
			@Override
			protected ResponseData<Score> doInBackground(String... params) {
				OfficeEngineImpl impl = new OfficeEngineImpl();
				return impl.getScore2Bean(fragmentData.getScoreUrl(),
						fragmentData.getFromUrl());
			}

			@Override
			protected void onPostExecute(ResponseData<Score> result) {
				if (result.isSuccess()) {
					// 获取成功
					info = result.getObj();
					Log.i("wang", "info.getColumns().size="+info.getColumns().size());
					processData();
				} else {
					// 获取失败
					interFace.onFragmentCallback(ScoreFragment.this, 0, null);
				}
				super.onPostExecute(result);
			}
		}.execute();

	}

	protected void processData() {
//		Log.i("wang", "info.columns.size()=" + info.columns.size());
		if(adapter == null){
//			adapter = new OfficeScoreAdapter(info.getColumns(),inflater);
			lv_lv.setAdapter(adapter);
		}else{
			adapter.notifyDataSetChanged();
		}
		view_refersh.setRefreshing(false);
	}

}
