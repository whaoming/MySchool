package com.wxxiaomi.myschool.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Social;
import com.wxxiaomi.myschool.bean.R_Social.SocialItem;
import com.wxxiaomi.myschool.engine.SocialEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;
import com.wxxiaomi.myschool.view.adapter.SocialAdapter;

public class SocialActivity extends BaseActivity {

	private Toolbar toolbar;

	private PullToRefreshListView mPullRefreshListView;

	List<SocialItem> lostItemList = new ArrayList<SocialItem>();

	private TextView nodata;

	private SocialAdapter adapter;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_social);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		nodata = (TextView) findViewById(R.id.nodata);
		mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initData() {
		toolbar.setTitle("校园活动");
		mPullRefreshListView
				.setOnRefreshListener(new OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// getLostList(0);
						// refreshView
						// .getLoadingLayoutProxy()
						// .setLastUpdatedLabel(CommonUtil.getStringDate());
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// getLostList(lostItemList.size());

					}
				});
		mPullRefreshListView.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// Intent intent = new Intent(ct,
						// LostFoundDetailActivity2.class);
						// Bundle bund = new Bundle();
						// bund.putSerializable("LostItem",
						// lostItemList.get(position - 1));
						// intent.putExtra("value", bund);
						// startActivity(intent);

					}
				});
		getDataList(0);
	}

	private void getDataList(final int page) {
		new AsyncTask<String, Void, R_Social>() {
			@Override
			protected R_Social doInBackground(String... params) {
				R_Social r_social =  null;
//				LostItemEngineImpl engine = new LostItemEngineImpl();
				SocialEngineImpl engine = new SocialEngineImpl();
				r_social = engine.getSocialList(0, page, false, ct);
				return r_social;
			}

			@Override
			protected void onPostExecute(R_Social result) {
				if (result != null && result.success == 1) {
					if (page == 0) {
						lostItemList.clear();
						lostItemList.addAll(result.socialList);
					} else {
						lostItemList.addAll(result.socialList);
					}

				} else {
//					DialogUtils.showToast(ct, "连接不上服务器");
				}
				processData();
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
		}.execute();
		
	}

	protected void processData() {
		if (adapter == null) {
			adapter = new SocialAdapter(ct, lostItemList, null);
			mPullRefreshListView.getRefreshableView().setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
		
		onLoaded();
		
	}
	private void onLoaded() {
		if (lostItemList.size() == 0) {
			nodata.setText("当前并无数据");
			nodata.setVisibility(View.VISIBLE);
		} else {
			nodata.setVisibility(View.INVISIBLE);
		}
//		dismissLoadingView();
	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
