package com.wxxiaomi.myschool.view.activity;

import java.util.List;

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
import com.wxxiaomi.myschool.bean.lib.BookInfo;
import com.wxxiaomi.myschool.bean.lib.format.LibSearchResultFormat;
import com.wxxiaomi.myschool.bean.lib.format.common.LibReceiverData;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;
import com.wxxiaomi.myschool.view.adapter.LibSearchResultAdapter;
import com.wxxiaomi.myschool.view.adapter.LibSearchResultAdapter.OnResultClickListener;

public class LibSearchActivity extends BaseActivity {

	private Toolbar toolbar;
	SearchView searchView;
	private RecyclerView mRecyclerView;
	private LibSearchResultAdapter adapter;
	private LinearLayoutManager mLayoutManager;
	private int lastVisibleItem = 0;
	private List<BookInfo> bookList;
	private LibSearchResultFormat currentPageItem;
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
	}

	
	private void searchByNet(final String content) {
		new AsyncTask<String, Void, LibReceiverData<LibSearchResultFormat>>() {
			@Override
			protected LibReceiverData<LibSearchResultFormat> doInBackground(
					String... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				return impl.getSearchResult(content);
			}

			@Override
			protected void onPostExecute(
					LibReceiverData<LibSearchResultFormat> result) {
				if (result.state == 200) {
					processData(result.infos);
				} else {

				}
				super.onPostExecute(result);
			}

		}.execute();

	}
	
	protected void getNextPageByNet() {
		new AsyncTask<String, Void, LibReceiverData<LibSearchResultFormat>>() {
			@Override
			protected LibReceiverData<LibSearchResultFormat> doInBackground(
					String... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				return impl.getSearchResultNextPage(currentPageItem.nextPageUrl, currentPageItem.currentPage+1);
			}

			@Override
			protected void onPostExecute(
					LibReceiverData<LibSearchResultFormat> result) {
				if(result.state == 200){
					bookList.addAll(result.infos.list);
					currentPageItem.currentPage = result.infos.currentPage;
					processNextPageData();
				}
				super.onPostExecute(result);
			}

		}.execute();

	}

	protected void processNextPageData() {
		Log.i("wang", "currentPageItem.currentPage="+currentPageItem.currentPage);
		adapter.notifyItemChanged(bookList.size());
	}

	protected void processData(LibSearchResultFormat infos) {
		currentPageItem = infos;
		if(bookList == null){
			bookList = infos.list;
		}else{
			bookList.addAll(infos.list);
		}
		if (adapter == null) {
			adapter = new LibSearchResultAdapter(ct, bookList);
			mRecyclerView.setAdapter(adapter);
			adapter.setOnResultClickListener(new OnResultClickListener() {
				@Override
				public void click(int position) {
					BookInfo bookInfo = bookList.get(position);
					Intent intent = new Intent(ct, LibBookInfoActivity.class);
					intent.putExtra("bookurl", bookInfo.getUrl().getBytes());
					startActivity(intent);
				}
			});
		}else {
			adapter.notifyItemChanged(infos.list.size());
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
