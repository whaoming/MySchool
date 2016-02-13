package com.wxxiaomi.myschool.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Lost;
import com.wxxiaomi.myschool.bean.R_Lost.LostItem;
import com.wxxiaomi.myschool.engine.LostItemEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;
import com.wxxiaomi.myschool.view.adapter.LostItemAdapter2;

public class LostActivity2 extends BaseActivity {

	private Toolbar toolbar;

	private SwipeRefreshLayout mSwipeRefreshWidget;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private int lastVisibleItem;
	List<LostItem> lostItemList = new ArrayList<LostItem>();

	private LostItemAdapter2 adapter;

	private TextView nodata;
	private boolean noData;

	/**
	 * 是否为查看自己发表的页面
	 */
	private boolean isMe;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_lost2);
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

		toolbar.setTitle("失物招领");
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initData() {
		isMe = getIntent().getBooleanExtra("isMe", false);
		mSwipeRefreshWidget.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
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
					if(!noData){
						getDataList(lostItemList.size());
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

	/**
	 * 获取数据
	 * 
	 * @param page
	 */
	private void getDataList(final int page) {
		new AsyncTask<String, Void, R_Lost>() {
			@Override
			protected R_Lost doInBackground(String... params) {
//				LostItemEngine engine = BeanFactory.getInstance().getImpl(
//						LostItemEngine.class);
				R_Lost r_lost =  null;
//				if (!isMe) {
				LostItemEngineImpl engine = new LostItemEngineImpl();
				r_lost = engine.getItemList(page, false, ct);
//				} else {
//					r_lost = engine.getMyItemList(page, false, ct);
//				}
				return r_lost;
			}

			@Override
			protected void onPostExecute(R_Lost result) {
				if (result != null && result.success == 1) {
					if (page == 0) {
						lostItemList.clear();
						lostItemList.addAll(result.lostItemList);
					} else {
						lostItemList.addAll(result.lostItemList);
					}

				} else {
//					DialogUtils.showToast(ct, "连接不上服务器");
				}
				processData();
				super.onPostExecute(result);
			}
		}.execute();
	}

	protected void processData() {
		mSwipeRefreshWidget.setRefreshing(false);
		if (adapter == null) {
			adapter = new LostItemAdapter2(ct, lostItemList, isMe);
			mRecyclerView.setAdapter(adapter);
//			mPullRefreshListView.getRefreshableView().setAdapter(adapter);
		} else {
			adapter.notifyItemInserted(lostItemList.size() + 1);
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
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void processClick(View v) {
		switch (v.getId()) {
		default:
			break;
		}

	}

}
