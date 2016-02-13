package com.wxxiaomi.myschool.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.R_News.NormalNewsItem;

public class NewsAdapter extends BaseAdapter {

	BitmapUtils bitmapUtil;
	private Context context;
	private List<NormalNewsItem> list;

	public NewsAdapter(Context context, List<NormalNewsItem> list) {
		this.context = context;
		bitmapUtil = new BitmapUtils(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position
				;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		NormalNewsItem news = list.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View
					.inflate(context, R.layout.item_news, null);
			holder.iv = (ImageView) convertView.findViewById(R.id.iv_img);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.pub_date = (TextView) convertView
					.findViewById(R.id.tv_pub_date);
			holder.comment_count = (TextView) convertView
					.findViewById(R.id.tv_comment_count);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(news.title);
		holder.pub_date.setText(news.data);
		holder.comment_count.setText("");
		// Log.i("NewsAdapter", "news.getType()=="+news.getType());
		if (news.picurl.length()>8) {
			holder.iv.setVisibility(View.VISIBLE);
			bitmapUtil.display(holder.iv,
					ConstantValue.LOTTERY_URI + news.picurl);
		} else {
			holder.iv.setVisibility(View.GONE);
		}

		return convertView;
	}

	public class ViewHolder {
		ImageView iv;
		TextView title;
		TextView pub_date;
		TextView comment_count;
	}

}
