package com.wxxiaomi.myschool.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

public class UserSchoolCardInfoFragment extends BaseFragment {

	View view;
	
	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_userinfo_schoolcard, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

}
