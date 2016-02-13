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
import com.wxxiaomi.myschool.bean.R_Social.SocialItem;
import com.wxxiaomi.myschool.view.custom.CircularImageView;

public class SocialAdapter extends BaseAdapter {

	private List<SocialItem> list;
	private Context ct;
	BitmapUtils bitmapUtil;
	List<Integer> hasDot;

	public SocialAdapter(Context ct, List<SocialItem> list,List<Integer> hasDot) {
		this.ct = ct;
		this.list = list;
		bitmapUtil = new BitmapUtils(ct);
		this.hasDot = hasDot;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final Holder hold;
		final SocialItem social = list.get(position);
		if (convertView == null) {
			hold = new Holder();
			convertView = View.inflate(ct, R.layout.item_social, null);
			hold.iv_head = (CircularImageView) convertView.findViewById(R.id.iv_head);
			hold.iv_pic = (ImageView) convertView.findViewById(R.id.iv_pic);
			hold.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			hold.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
			hold.tv_likecount = (TextView) convertView.findViewById(R.id.tv_likecount);
			convertView.setTag(hold);
		} else {
			hold = (Holder) convertView.getTag();
		}
		hold.tv_name.setText(social.userInfo.name);
		hold.tv_title.setText(social.title);
		hold.tv_likecount.setText(social.likes+"");
		bitmapUtil.display(hold.iv_pic,
				ConstantValue.LOTTERY_URI + social.pic);
		bitmapUtil.display(hold.iv_head,
				ConstantValue.LOTTERY_URI + social.userInfo.pic);
		return convertView;
	}

	static class Holder {
		CircularImageView iv_head;
		ImageView iv_pic;
		TextView tv_name;
		TextView tv_title;
		TextView tv_likecount;
	}

}
