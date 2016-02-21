package com.wxxiaomi.myschool.view.activity;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.lib.BookCollectLocation;
import com.wxxiaomi.myschool.bean.lib.format.BookDetailFormat;
import com.wxxiaomi.myschool.bean.lib.format.common.LibReceiverData;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.view.activity.base.BaseActivity;

public class LibBookInfoActivity extends BaseActivity {

	private TextView tv_tv;
	private LinearLayout ll_ll;
	private BookDetailFormat info;

	// private BookInfo bookInfo;
	private String url;

	@Override
	protected void initView() {
		setContentView(R.layout.activity_library_bookinfo);
		url = new String(getIntent().getByteArrayExtra("bookurl"));
		// bookInfo = (BookInfo) getIntent().getByteArrayExtra("bookinfo");
		tv_tv = (TextView) findViewById(R.id.tv_tv);
		ll_ll = (LinearLayout) findViewById(R.id.ll_ll);
		initTitleBar();
		// initRight("书本");
	}

	@Override
	protected void initData() {
		getBookInfoByNet();
	}

	private void getBookInfoByNet() {
		// new AsyncTask<String, Void, ResponseData<Html_Lib_BookInfoDetail>>()
		// {
		// @Override
		// protected ResponseData<Html_Lib_BookInfoDetail> doInBackground(
		// String... params) {
		// LibraryEngineImpl impl = new LibraryEngineImpl();
		// return impl.getBookInfo(url);
		// }
		//
		// @Override
		// protected void onPostExecute(
		// ResponseData<Html_Lib_BookInfoDetail> result) {
		// if (result.isSuccess()) {
		// info = result.getObj();
		// processData(info);
		// } else {
		// }
		//
		// super.onPostExecute(result);
		// }
		// }.execute();
		new AsyncTask<String, Void, LibReceiverData<BookDetailFormat>>() {
			@Override
			protected LibReceiverData<BookDetailFormat> doInBackground(
					String... params) {
				LibraryEngineImpl impl = new LibraryEngineImpl();
				return impl.getBookInfoByUrl(url);
			}

			@Override
			protected void onPostExecute(
					LibReceiverData<BookDetailFormat> result) {
				if (result.state == 200) {
					info = result.infos;
					processData();
				} else {
				}

				super.onPostExecute(result);
			}
		}.execute();

	}

	protected void processData() {
		// TODO Auto-generated method stub
		// closeMingDialog();
		tv_tv.setText(info.bookInfo.getDetail());
		if (info.collectLocatList.size() > 0) {
			addLocation(info.collectLocatList);
		}
	}

	@SuppressLint("InflateParams")
	private void addLocation(List<BookCollectLocation> collectLocatList) {
		// TODO Auto-generated method stub
		for (BookCollectLocation cl : collectLocatList) {
			View view = getLayoutInflater().inflate(
					R.layout.item_lib_bookinfo_collectionlocation, null);
			TextView tv_location = (TextView) view
					.findViewById(R.id.tv_location);
			TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
			TextView tv_loginnumber = (TextView) view
					.findViewById(R.id.tv_loginnumber);
			TextView tv_state = (TextView) view.findViewById(R.id.tv_state);
			TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
			tv_location.setText(cl.getCollectionLocation());
			tv_number.setText(cl.getNumber());
			tv_loginnumber.setText(cl.getLoginNumber());
			tv_state.setText(cl.getState());
			tv_type.setText(cl.getType());
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeAllViews();
			}
			// container.addView(v);
			ll_ll.addView(view);
		}
	}

	// protected void processData(Html_Lib_BookInfoDetail info1) {
	// // closeMingDialog();
	// tv_tv.setText(info1.getBookInfo().getDetail());
	// if (info1.getBookInfo().getCollecLocations().size() > 0) {
	// addLocation(info1.getBookInfo().getCollecLocations());
	// }
	//
	// }

	// @SuppressLint("InflateParams")
	// private void addLocation(List<CollectState> collecLocations) {
	// for (CollectState cl : collecLocations) {
	// View view = getLayoutInflater().inflate(
	// R.layout.item_lib_bookinfo_collectionlocation, null);
	// TextView tv_location = (TextView) view
	// .findViewById(R.id.tv_location);
	// TextView tv_number = (TextView) view.findViewById(R.id.tv_number);
	// TextView tv_loginnumber = (TextView) view
	// .findViewById(R.id.tv_loginnumber);
	// TextView tv_state = (TextView) view.findViewById(R.id.tv_state);
	// TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
	// tv_location.setText(cl.getCollectionLocation());
	// tv_number.setText(cl.getNumber());
	// tv_loginnumber.setText(cl.getLoginNumber());
	// tv_state.setText(cl.getState());
	// tv_type.setText(cl.getType());
	// ViewGroup parent = (ViewGroup) view.getParent();
	// if (parent != null) {
	// parent.removeAllViews();
	// }
	// // container.addView(v);
	//
	// ll_ll.addView(view);
	// }
	//
	// }

	@Override
	protected void processClick(View v) {
		// TODO Auto-generated method stub

	}

}
