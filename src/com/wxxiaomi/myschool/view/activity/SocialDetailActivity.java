package com.wxxiaomi.myschool.view.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_Comment;
import com.wxxiaomi.myschool.bean.R_Comment.CommentItem;
import com.wxxiaomi.myschool.bean.R_Social.SocialItem;
import com.wxxiaomi.myschool.engine.SocialEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;
import com.wxxiaomi.myschool.view.adapter.SocialDetailAdapter;

public class SocialDetailActivity extends BaseActivity {
	
	private Toolbar toolbar;
	private SocialItem item;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private int lastVisibleItem;
	private SocialDetailAdapter adapter;
	List<CommentItem> list = new ArrayList<CommentItem>();

	@Override
	protected void initView() {
		setContentView(R.layout.activity_list);
		mRecyclerView = (RecyclerView) findViewById(R.id.listview);
		toolbar = (Toolbar) findViewById(R.id.toolbar);
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
		
		Intent intent = getIntent();
		Bundle bund = intent.getBundleExtra("value");
		item = (SocialItem) bund.getSerializable("item");
		mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView,
					int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE
						&& lastVisibleItem + 1 == adapter.getItemCount()) {
					Log.i("wang", "到尾啦");
//					if(!noData){
//						getDataList(lostItemList.size());
//					}
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			}

		});
		adapter = new SocialDetailAdapter(ct, list,item);
		mRecyclerView.setAdapter(adapter);
		getCommentList(0);
	}

	private void getCommentList(final int page) {
		new AsyncTask<String, Void, R_Comment>() {
			@Override
			protected R_Comment doInBackground(String... params) {
//				LostItemEngine engine = BeanFactory.getInstance().getImpl(
//						LostItemEngine.class);
//				R_Lost r_lost =  null;
//				if (!isMe) {
//				LostItemEngineImpl engine = new LostItemEngineImpl();
//				r_lost = engine.getItemList(page, false, ct);
//				} else {
//					r_lost = engine.getMyItemList(page, false, ct);
//				}
				SocialEngineImpl engine = new SocialEngineImpl();
				R_Comment socialItemCommentList = engine.getSocialItemCommentList(page, item.id);
				return socialItemCommentList;
			}

			@Override
			protected void onPostExecute(R_Comment result) {
				if (result != null && result.success == 1) {
					if (page == 0) {
						list.clear();
//						list.addAll(result.commentList);
					} else {
//						list.addAll(result.commentList);
					}
					processData(result.commentList);
				} else {
//					DialogUtils.showToast(ct, "连接不上服务器");
				}
//				processData();
				super.onPostExecute(result);
			}
		}.execute();
	}

	protected void processData(List<CommentItem> commentList) {
		if(commentList.size() == 0){
			if(list.size() == 0){
				//没有评论
				Log.i("wang", "没有数据");
				adapter.setNoData(true);
//				adapter.notifyItemRemoved(1);
//				Log.i("wang", "adapter.notifyItemRemoved(1)后，item数="+adapter.getItemCount());
//				adapter.notifyItemInserted(3);
//				Log.i("wang", "adapter.notifyItemRemoved(1)后，item数="+adapter.getItemCount());
			}
			//没有更多的评论
		}else{
			list.addAll(commentList);
			adapter.notifyItemInserted(list.size() + 1);
		}
		Log.i("wang", "list.size() + 1="+list.size() + 1);
	
//		list.addAll(commentList);
//		if (adapter == null) {
//			adapter = new SocialDetailAdapter(ct, list,item);
//			mRecyclerView.setAdapter(adapter);
//		} else {
//			adapter.notifyItemInserted(list.size() + 1);
//		}
		
		onLoaded();
	}
	private void onLoaded() {
//		if (list.size() == 0) {
//			nodata.setText("当前并无数据");
//			nodata.setVisibility(View.VISIBLE);
//		} else {
//			nodata.setVisibility(View.INVISIBLE);
//		}
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
