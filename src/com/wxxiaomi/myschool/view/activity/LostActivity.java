package com.wxxiaomi.myschool.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Lost;
import com.wxxiaomi.myschool.bean.R_Lost.LostItem;
import com.wxxiaomi.myschool.engine.LostItemEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;
import com.wxxiaomi.myschool.view.adapter.LostItemAdapter;

public class LostActivity extends BaseActivity {

	private Toolbar toolbar;

	private PullToRefreshListView mPullRefreshListView;
	List<LostItem> lostItemList = new ArrayList<LostItem>();

	private LostItemAdapter adapter;

	private TextView nodata;

	/**
	 * 是否为查看自己发表的页面
	 */
	private boolean isMe;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_lost);
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
		toolbar.setTitle("寻物启事");
		isMe = getIntent().getBooleanExtra("isMe", false);
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
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
		}.execute();
	}

	protected void processData() {
		if (adapter == null) {
			adapter = new LostItemAdapter(ct, lostItemList, isMe);
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
