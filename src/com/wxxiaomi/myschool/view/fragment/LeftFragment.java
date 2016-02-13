package com.wxxiaomi.myschool.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.activity.HomeActivity;
import com.wxxiaomi.myschool.view.activity.LoginActivity;

public class LeftFragment extends Fragment  implements OnClickListener{
	private TextView tv_index;
	private TextView tv_test;
	private TextView tv_login;
	
	int currentId = 0;
	public interface leftClickListener {
		void menuClick(String menuName);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_left, container,
				false);
		tv_index = (TextView) view.findViewById(R.id.tv_index);
		tv_index.setOnClickListener(this);
		tv_test = (TextView) view.findViewById(R.id.tv_test);
		tv_test.setOnClickListener(this);
		tv_login = (TextView) view.findViewById(R.id.tv_login);
		tv_login.setOnClickListener(this);
		
		return view;
	}


	@Override
	public void onClick(View v) {
		Fragment f = null;
		int state = 0;
		if (v.getId() != currentId) {
			switch (v.getId()) {
			case R.id.tv_index:
				f = new IndexFragment();
				break;
			case R.id.tv_test:
				f = new TestFragment();
				break;
			case R.id.tv_login:
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
				state = 1;
				break;
			default:
				break;
			}
			currentId = v.getId();
			switchFragment(f, state);
		}
	}

	private void switchFragment(Fragment f, int state) {
		if (getActivity() instanceof HomeActivity) {
			HomeActivity act = (HomeActivity) getActivity();
			act.switchFragment(f, state);
		}
		
	}

}
