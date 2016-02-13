package com.wxxiaomi.myschool.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

@SuppressLint("InflateParams")
public class ClassFormFragment extends BaseFragment {

	@Override
	public View initView(LayoutInflater inflater) {
		View view = inflater.inflate(R.layout.fragment_classform, null);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

	}

}
