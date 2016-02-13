package com.wxxiaomi.myschool.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.webpage.BookInfo;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Lib_Search_Main;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Lib_Search_Result;
import com.wxxiaomi.myschool.bean.webpage.request.ResponseData;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;
import com.wxxiaomi.myschool.view.adapter.LibSearchResultAdapter;
import com.wxxiaomi.myschool.view.adapter.LibSearchResultAdapter.OnResultClickListener;
import com.wxxiaomi.myschool.view.custom.LoadingDialog;

public class LibSearchActivity extends BaseActivity {

	private Toolbar toolbar;
	SearchView searchView;
	private RecyclerView mRecyclerView;
	private LibSearchResultAdapter adapter;
	private Html_Lib_Search_Main main;
	private Html_Lib_Search_Result info;
	private LinearLayoutManager mLayoutManager;
	private int lastVisibleItem = 0;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_lib_search);
		mRecyclerView = (RecyclerView) findViewById(android.R.id.list);
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setItemAnimator(new DefaultItemAnimator());
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		// 标题的文字需在setSupportActionBar之前，不然会无效
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	protected void initData() {
		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == adapter.getItemCount()) {
//					if(!noData){
//						getDataList(lostItemList.size());
//					}
					Log.i("wang", "到底了");
					getNextPageByNet();
				}

			}
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			}
		});
		
		getMainByNet();
	}

	/**
	 * 获取搜索结果
	 */
	private void getMainByNet() {
//		final AlertDialog loddingDialog = new AlertDialog.Builder(ct).setMessage("正在加载中..").create();
		final LoadingDialog loddingDialog = new LoadingDialog(ct).builder().setMessage("正在初始化");
		loddingDialog.show();
		new AsyncTask<String, Void, ResponseData<Html_Lib_Search_Main>>() {
			@Override
			protected ResponseData<Html_Lib_Search_Main> doInBackground(
					String... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				return impl.getLibSearchMain();
			}
			@Override
			protected void onPostExecute(
					ResponseData<Html_Lib_Search_Main> result) {
				if (result.isSuccess()) {
					main = result.getObj();
					loddingDialog.dismiss();
//					processData();
//					closeMingDialog();
				} else {
					
				}
				super.onPostExecute(result);
			}
		}.execute();
	}
	
	private void searchByNet(final String content) {
		new AsyncTask<Html_Lib_Search_Main, Void, ResponseData<Html_Lib_Search_Result>>() {
			

			@Override
			protected ResponseData<Html_Lib_Search_Result> doInBackground(
					Html_Lib_Search_Main... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				return impl.getSearchResult(main, content);
			}

			@Override
			protected void onPostExecute(
					ResponseData<Html_Lib_Search_Result> result) {
				if (result.isSuccess()) {
//					Log.i("wamg", "进入搜索结果success中");
					info = result.getObj();
					processData();
				} else {

				}
				super.onPostExecute(result);
			}

		}.execute();

	}
	
	protected void getNextPageByNet() {
		new AsyncTask<Html_Lib_Search_Main, Void, ResponseData<Html_Lib_Search_Result>>() {
			@Override
			protected ResponseData<Html_Lib_Search_Result> doInBackground(
					Html_Lib_Search_Main... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				return impl.getNextPage(info.getPageUrl(),
						info.getCurrentPage(), info.getUrl());
			}

			@Override
			protected void onPostExecute(
					ResponseData<Html_Lib_Search_Result> result) {
				if (result.isSuccess()) {
					processNextPageData(result.getObj());
				} else {

				}
				super.onPostExecute(result);
			}

		}.execute();

	}

	protected void processNextPageData(Html_Lib_Search_Result nextInfo) {
		info.getColumns().addAll(nextInfo.getColumns());
		info.setCurrentPage((info.getCurrentPage() + 1) + "");
//		lmtv.setNormalText("已加载" + info.getCurrentPage() + "页，总共有"
//				+ info.getPageCount() + "页");
		adapter.notifyItemChanged(info.getColumns().size());
		
	}

	protected void processData() {
		if (info.getColumns().size() == 0) {
			
		} else {
			if (adapter == null) {
				adapter = new LibSearchResultAdapter(ct, info.getColumns());
				mRecyclerView.setAdapter(adapter);
				adapter.setOnResultClickListener(new OnResultClickListener() {
					@Override
					public void click(int position) {
						BookInfo bookInfo = info.getColumns().get(position);
						Intent intent = new Intent(ct, LibBookInfoActivity.class);
						intent.putExtra("bookurl", bookInfo.getUrl().getBytes());
						startActivity(intent);
					}
				});
			} else {
				adapter.notifyItemChanged(info.getColumns().size());
			}
		}
	}

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_lib_search, menu);
		searchView = (SearchView) MenuItemCompat
				.getActionView( menu.findItem(R.id.action_search));
//		searchView.setIconified(false);
//		searchView.setIconifiedByDefault(false);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String arg0) {
				Log.i("wang", "searchView中提交的文字是:"+arg0);
				searchByNet(arg0);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String arg0) {
//				Log.i("wang", "searchView中改变的文字是:"+arg0);
				
				return false;
			}
		});
		return true;
	}

}
