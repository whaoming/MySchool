package com.wxxiaomi.myschool.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.GlobalParams;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

public class UserInfoPageFragment extends BaseFragment {

	private View view;
	private ImageView iv_head;
	private TextView tv_name;
	private TextView tv_description;
	private TextView tv_xh;
	private TextView tv_sex;
	private TextView tv_tname;
	private BitmapUtils bitmapUtil;
	
	
	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_userinfo_personalinfo, null);
		iv_head = (ImageView) view.findViewById(R.id.iv_head);
		tv_name = (TextView) view.findViewById(R.id.tv_name);
		tv_description = (TextView) view.findViewById(R.id.tv_description);
		tv_xh = (TextView) view.findViewById(R.id.tv_xh);
		tv_sex = (TextView) view.findViewById(R.id.tv_sex);
		tv_tname = (TextView) view.findViewById(R.id.tv_tname);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		bitmapUtil = new BitmapUtils(ct);
		bitmapUtil.display(iv_head, ConstantValue.LOTTERY_URI
				+ GlobalParams.gloUserInfo.userInfo.pic);
		tv_name.setText(GlobalParams.gloUserInfo.userInfo.name);
		tv_description.setText(GlobalParams.gloUserInfo.userInfo.description);
		tv_xh.setText(GlobalParams.gloUserInfo.userInfo.officeUserInfo.username);
		tv_tname.setText(GlobalParams.gloUserInfo.userInfo.officeUserInfo.tname);
		tv_sex.setText(GlobalParams.gloUserInfo.userInfo.officeUserInfo.sex);
	}

}
