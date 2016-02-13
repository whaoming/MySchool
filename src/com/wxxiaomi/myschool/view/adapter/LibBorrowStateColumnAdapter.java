package com.wxxiaomi.myschool.view.adapter;


import java.util.List;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.webpage.BookBorrowedState;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class LibBorrowStateColumnAdapter extends BaseAdapter {

	private Context context;
	private List<BookBorrowedState> list;

	public LibBorrowStateColumnAdapter(Context ct,List<BookBorrowedState> list) {
		this.context = ct;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
//		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		BookBorrowedState column = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(context,
					R.layout.item_lib_borrow_state, null);
			holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_latesttime = (TextView) convertView.findViewById(R.id.tv_latesttime);
			holder.tv_borrowtime = (TextView) convertView.findViewById(R.id.tv_borrowtime);
			holder.tv_loginnumber = (TextView) convertView.findViewById(R.id.tv_loginnumber);
			holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(column.name);
		holder.tv_latesttime.setText("最迟应还期："+column.latestReturn);
		holder.tv_borrowtime.setText("借期："+column.borrowedTime);
		holder.tv_loginnumber.setText("登录号："+column.loginNumber);
		holder.tv_type.setText("图书类型："+column.type);
		return convertView;
	}

	public class ViewHolder {
		TextView tv_name;
		TextView tv_latesttime;
		TextView tv_borrowtime;
		TextView tv_loginnumber;
		TextView tv_type;
	}

}
