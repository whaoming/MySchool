package com.wxxiaomi.myschool.view.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.adapter.IndexFragmentTabAdapter;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;

public class IndexFragment extends BaseFragment {

	View view;
	private TabLayout tabLayout;                            //定义TabLayout  
    private ViewPager viewPager;                             //定义viewPager  
    private FragmentStatePagerAdapter fAdapter;
    private List<Fragment> list_fragment; 
    private List<String> list_title;
	
	@SuppressLint("InflateParams")
	@Override
	public View initView(LayoutInflater inflater) {
		view = inflater.inflate(R.layout.fragment_index, null);
		tabLayout = (TabLayout)view.findViewById(R.id.tab_FindFragment_title);  
		viewPager = (ViewPager)view.findViewById(R.id.vp_FindFragment_pager); 
		return view;
	}

	@Override
	public void initData(Bundle savedInstanceState) {
		list_fragment = new ArrayList<Fragment>();  
		list_fragment.add(new InformationFragment());
		list_fragment.add(new FindOutFragment());
		list_title = new ArrayList<>();
		list_title.add("首页");  
        list_title.add("发现"); 
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(0)));  
        tabLayout.addTab(tabLayout.newTab().setText(list_title.get(1)));
        fAdapter = new IndexFragmentTabAdapter(getActivity().getSupportFragmentManager(),list_fragment,list_title);
        viewPager.setAdapter(fAdapter);
        viewPager.requestDisallowInterceptTouchEvent(true);
        
        tabLayout.setTabMode(TabLayout.MODE_FIXED);  
        //TabLayout加载viewpager  
        tabLayout.setupWithViewPager(viewPager);  
	}

}
