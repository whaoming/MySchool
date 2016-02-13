package com.wxxiaomi.myschool.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_News;
import com.wxxiaomi.myschool.bean.R_News.NormalNewsItem;
import com.wxxiaomi.myschool.bean.R_News.TopicNewsItem;
import com.wxxiaomi.myschool.engine.InformationEngineImpl;
import com.wxxiaomi.myschool.util.CommonUtil;
import com.wxxiaomi.myschool.view.adapter.NewsAdapter;
import com.wxxiaomi.myschool.view.custom.RollViewPager;
import com.wxxiaomi.myschool.view.custom.RollViewPager.OnPagerClickCallback;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

@SuppressLint("InflateParams")
public class InformationFragment extends BaseFragment {

	View view;
	// 放置从服务器获取到的新闻
	private List<NormalNewsItem> normalNewsList = new ArrayList<NormalNewsItem>();
	private List<TopicNewsItem> topicNewsList = new ArrayList<TopicNewsItem>();
	// 适配器 下面listview
	private NewsAdapter adapter;

	private PullToRefreshListView mPullRefreshListView;

	// 置顶新闻的标题
	@ViewInject(R.id.top_news_title)
	private TextView topNewsTitle;

	// 放置置顶新闻viewpager的一个容器
	@ViewInject(R.id.top_news_viewpager)
	private LinearLayout mViewPagerLay;

	// pager里面的点
	@ViewInject(R.id.dots_ll)
	private LinearLayout dotLl;

	// 表示这个viewpager
	private View topNewsView;

	// 加载的那个空间
	// @ViewInject(R.id.loading_view)
	// private FrameLayout loading_view;

	// 放置点的集合
	private ArrayList<View> dotList;
	// 存放置顶新闻的一个集合
	private ArrayList<String> titleList, urlList;
	private RollViewPager mViewPager;

//	private TextView nodata;

	@Override
	public View initView(LayoutInflater inflater) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_information, null);
		topNewsView = inflater.inflate(R.layout.layout_roll_view, null);
		ViewUtils.inject(this, view); // 注入view和事件
		ViewUtils.inject(this, topNewsView);
//		nodata = (TextView) view.findViewById(R.id.nodata);
		mPullRefreshListView = (PullToRefreshListView) view
				.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView
		.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewsList(0);
//				refreshView
//						.getLoadingLayoutProxy()
//						.setLastUpdatedLabel(CommonUtil.getStringDate());
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getNewsList(normalNewsList.size());

			}
		});
		mPullRefreshListView.getRefreshableView().setOnItemClickListener(
				new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
//						Intent intent = new Intent(ct, NewsDetailActivity.class);
//						String url = "";
//						NormalNewsItem newsItem;
//						if (mPullRefreshListView.getRefreshableView()
//								.getHeaderViewsCount() > 0) {
//							newsItem = normalNewsList.get(position - 2);
//						} else {
//							newsItem = normalNewsList.get(position-1);
//						}
//						url = ConstantValue.LOTTERY_URI + ConstantValue.NEWS
//								+ "&newsid=" + newsItem.id;
//						intent.putExtra("url", url);
//						ct.startActivity(intent);
					}
				});
		mPullRefreshListView.getRefreshableView().setHeaderDividersEnabled(
				false);
		return view;
	}

	

	@Override
	public void initData(Bundle savedInstanceState) {
		InformationEngineImpl engine = new InformationEngineImpl();
		R_News cache_r_news = engine.getNewsList(0, true,ct);
		if (cache_r_news != null) {
			processDataFromCache(cache_r_news);
			dismissLoadingView();
		}
		getNewsList(0);
	}
	
	protected void getNewsList(final int page) {
		new AsyncTask<String, Void, R_News>() {
			@Override
			protected R_News doInBackground(String... params) {
				InformationEngineImpl engine = new InformationEngineImpl();
				R_News list = engine.getNewsList(page, false,ct);
				return list;
			}

			@Override
			protected void onPostExecute(R_News result) {
				if (result != null && "newsgetall".equals(result.response)) {
					if (page == 0) {
						normalNewsList.clear();
						normalNewsList.addAll(result.normalNewsList);
						topicNewsList.clear();
						topicNewsList.addAll(result.topicNewsList);
						Log.i("wang", "normalNewsList.size="+normalNewsList.size()+"--topicNewsList.size="+topicNewsList.size());
					} else {
						normalNewsList.addAll(result.normalNewsList);
					}
					processData(page);
				} else {
					/**
					 * 这里要判断是连接不上服务器还是没有服务器没有多余数据了
					 */
//					DialogUtils.showToast(ct, "连接不上服务器");
//					onLoaded();
				}
//				dismissLoadingView();
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			}
		}.execute();		
		
	}



	protected void processData(int page) {
		if (page == 0) {
			initDot(topicNewsList.size());
			titleList = new ArrayList<String>();
			urlList = new ArrayList<String>();
			for (TopicNewsItem news : topicNewsList) {
				titleList.add(news.title);
				urlList.add(ConstantValue.LOTTERY_URI + news.picurl);
				Log.i("wang", "ConstantValue.LOTTERY_URI + news.picurl="+ConstantValue.LOTTERY_URI + news.picurl);
			}
			mViewPager = new RollViewPager(ct, dotList, R.drawable.dot_focus,
					R.drawable.dot_normal, new OnPagerClickCallback() {
						@Override
						public void onPagerClick(int position) {
						}
					});
			mViewPager.setLayoutParams(new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			mViewPager.setUriList(urlList);
			mViewPager.setTitle(topNewsTitle, titleList);
			mViewPager.startRoll();
			mViewPagerLay.removeAllViews();
			mViewPagerLay.addView(mViewPager);
			if (mPullRefreshListView.getRefreshableView().getHeaderViewsCount() < 2) {
				mPullRefreshListView.getRefreshableView().addHeaderView(
						topNewsView, null, true);
				mPullRefreshListView.getRefreshableView()
						.setHeaderDividersEnabled(false);
			}
		}
		if (adapter == null) {
			adapter = new NewsAdapter(ct, normalNewsList);
			mPullRefreshListView.getRefreshableView().setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
//		onLoaded();
	}
	
	
	private void processDataFromCache(R_News cacheList) {
		initDot(cacheList.topicNewsList.size());
		titleList = new ArrayList<String>();
		urlList = new ArrayList<String>();
		for (TopicNewsItem news : cacheList.topicNewsList) {
			titleList.add(news.title);
			urlList.add(ConstantValue.LOTTERY_URI + news.picurl);
			
		}
		mViewPager = new RollViewPager(ct, dotList, R.drawable.dot_focus,
				R.drawable.dot_normal, new OnPagerClickCallback() {
					@Override
					public void onPagerClick(int position) {

					}
				});
		mViewPager.setLayoutParams(new LinearLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		mViewPager.setUriList(urlList);
		mViewPager.setTitle(topNewsTitle, titleList);
		mViewPager.startRoll();
		mViewPagerLay.removeAllViews();
		mViewPagerLay.addView(mViewPager);

		if (mPullRefreshListView.getRefreshableView().getHeaderViewsCount() < 2) {
			mPullRefreshListView.getRefreshableView().addHeaderView(
					topNewsView, null, true);
			mPullRefreshListView.getRefreshableView().setHeaderDividersEnabled(
					false);
		}
		if (adapter == null) {
			normalNewsList = cacheList.normalNewsList;
			adapter = new NewsAdapter(ct, normalNewsList);
			mPullRefreshListView.getRefreshableView().setAdapter(adapter);
		} else {
			adapter.notifyDataSetChanged();
		}
//		onLoaded();
	}
	
	private void initDot(int size) {
		dotList = new ArrayList<View>();
		dotLl.removeAllViews();
		for (int i = 0; i < size; i++) {
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					CommonUtil.dip2px(ct, 6), CommonUtil.dip2px(ct, 6));
			params.setMargins(5, 0, 5, 0);
			View m = new View(ct);
			if (i == 0) {
				m.setBackgroundResource(R.drawable.dot_focus);
			} else {
				m.setBackgroundResource(R.drawable.dot_normal);
			}
			m.setLayoutParams(params);
			dotLl.addView(m);
			dotList.add(m);
		}
	}

}
