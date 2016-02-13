package com.wxxiaomi.myschool.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Social;
import com.wxxiaomi.myschool.bean.R_Social.SocialItem;
import com.wxxiaomi.myschool.engine.SocialEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;
import com.wxxiaomi.myschool.view.adapter.SocialAdapter1;
import com.wxxiaomi.myschool.view.adapter.SocialAdapter1.MySocialCommentItemClickListener;

public class SocialActivity1 extends BaseActivity {

	private Toolbar toolbar;

	private SwipeRefreshLayout mSwipeRefreshWidget;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private int lastVisibleItem;
	List<SocialItem> list = new ArrayList<SocialItem>();
	private TextView nodata;
	private SocialAdapter1 adapter;
	private boolean noData;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_social1);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		nodata = (TextView) findViewById(R.id.nodata);
		mSwipeRefreshWidget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
		mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
		mSwipeRefreshWidget.setColorSchemeResources(R.color.color1,
				R.color.color2, R.color.color3, R.color.color4);

		mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
				.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
						.getDisplayMetrics()));

		mRecyclerView.setHasFixedSize(true);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());

		toolbar.setTitle("校园活动");
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initData() {
		mSwipeRefreshWidget.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
//				Log.i("wang", "onRefresh()--上拉加载更多");
				// 上拉加载更多
				noData = false;
				getDataList(0);
			}
		});
		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == adapter.getItemCount()) {
					// mSwipeRefreshWidget.setRefreshing(true);
//					Log.i("wang", "到底");
					if(!noData){
						getDataList(list.size());
					}
				}

			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			}

		});
		mSwipeRefreshWidget.setRefreshing(true);
		getDataList(0);
	}

	private void getDataList(final int page) {
		new AsyncTask<String, Void, R_Social>() {
			@Override
			protected R_Social doInBackground(String... params) {
				R_Social r_social = null;
				SocialEngineImpl engine = new SocialEngineImpl();
				r_social = engine.getSocialList(0, page, false, ct);
				return r_social;
			}

			@Override
			protected void onPostExecute(R_Social result) {
				if (result != null && result.success == 1) {
					if (page == 0) {
						list.clear();
					} else {
						// lostItemList.addAll(result.socialList);
					}
					processData(result.socialList);
				} else {
					// DialogUtils.showToast(ct, "连接不上服务器");
				}

				// mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
		}.execute();

	}

	protected void processData(List<SocialItem> socialList) {
		if (socialList.size() == 0) {
			noData = true;
		}else{
			noData = false;
			list.addAll(socialList);
			if (adapter == null) {
				Log.i("wang", "adapter == null");
				adapter = new SocialAdapter1(ct, list, null);
				mRecyclerView.setAdapter(adapter);
				adapter.setSocialCommentItemClickListener(new MySocialCommentItemClickListener() {
					@Override
					public void onClick(int position) {
						Intent intent = new Intent(ct,
								SocialDetailActivity.class);
						Bundle bund = new Bundle();
						Log.i("wang", "position="+position);
						bund.putSerializable("item", list.get(position));
						intent.putExtra("value", bund);
						startActivity(intent);
					}
				});
			} else {
//				 Log.i("wang", "lostItemList.size()="+lostItemList.size());
				adapter.notifyItemInserted(list.size() + 1);
			}
			onLoaded();
		}
		adapter.setNoData(noData);
	}

	private void onLoaded() {
		if (mSwipeRefreshWidget.isRefreshing()) {
			mSwipeRefreshWidget.setRefreshing(false);
		}
		if (list.size() == 0) {
			nodata.setText("当前并无数据");
			nodata.setVisibility(View.VISIBLE);
		} else {
			nodata.setVisibility(View.INVISIBLE);
		}

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
