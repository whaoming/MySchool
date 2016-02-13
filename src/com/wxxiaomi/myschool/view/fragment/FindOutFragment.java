package com.wxxiaomi.myschool.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.activity.LostActivity2;
import com.wxxiaomi.myschool.view.activity.SocialActivity1;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

public class FindOutFragment extends BaseFragment implements OnClickListener {
	private View view;
	private CardView ll_lost;
	private CardView ll_social;
//	private TextView tv_repair;
//	private TextView tv_express;
	
	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_findout, null);
		ll_lost = (CardView) view.findViewById(R.id.ll_lost);
		ll_social = (CardView) view.findViewById(R.id.ll_social);
//		tv_repair = (TextView) view.findViewById(R.id.tv_repair);
//		tv_express = (TextView) view.findViewById(R.id.tv_express);
		ll_lost.setOnClickListener(this);
		ll_social.setOnClickListener(this);
//		tv_repair.setOnClickListener(this);
//		tv_express.setOnClickListener(this);
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_lost:
			Intent intent = new Intent(ct,LostActivity2.class);
			startActivity(intent);
			break;
		case R.id.ll_social:
			Intent intent1 = new Intent(getActivity(),SocialActivity1.class);
			startActivity(intent1);
			break;
//		case R.id.ll_repair:
//			break;
		case R.id.ll_express:
			break;

		default:
			break;
		}
		
	}

}
