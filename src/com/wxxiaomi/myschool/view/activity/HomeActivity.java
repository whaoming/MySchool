package com.wxxiaomi.myschool.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.view.custom.MyMaterialDialog;
import com.wxxiaomi.myschool.view.fragment.IndexFragment;
import com.wxxiaomi.myschool.view.fragment.LeftFragment;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;
import com.wxxiaomi.myschool.view.fragment.base.FragmentCallback;

public class HomeActivity extends AppCompatActivity implements
		FragmentCallback{

	private Toolbar toolbar; // 定义toolbar
	private ActionBarDrawerToggle mDrawerToggle; // 定义toolbar左上角的弹出左侧菜单按钮
	private DrawerLayout drawer_main; // 定义左侧滑动布局，其实就是主布局


	private Fragment contentFragment; // 记录当前正在使用的fragment
	private boolean isMenuShuffle = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// tool_NavigationDrawerFragment f = new
		// tool_NavigationDrawerFragment();
		LeftFragment f = new LeftFragment();
		this.getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_left, f).commit();

		if (savedInstanceState == null) {
			contentFragment = new IndexFragment();
		} else {
			// 取出之前保存的contentFragment
			contentFragment = this.getSupportFragmentManager().getFragment(
					savedInstanceState, "contentFragment");
		}
		// 设置当前的fragment
		this.getSupportFragmentManager().beginTransaction()
				.replace(R.id.frame_main, contentFragment).commit();

		initToolbar();
		// initFragment(savedInstanceState);
		new MyMaterialDialog.Builder(this)
        .setTitle("title")
        .setMessage("message")
        .setNegativeButton("cancel",null)
        .setPositiveButton("ok",null)
//        .set
        .create()
        
        .show();
	}
	

	private void initToolbar() {
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		toolbar.setTitle("index"); // 标题的文字需在setSupportActionBar之前，不然会无效
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// 为了生成，工具栏左上角的动态图标，要使用下面的方法
		drawer_main = (DrawerLayout) findViewById(R.id.dl_left);
		mDrawerToggle = new ActionBarDrawerToggle(this, drawer_main, toolbar,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawer_main.setDrawerListener(mDrawerToggle);
	}

//	@Override
//	public void menuClick(String menuName) {
//		getSupportActionBar().setTitle(menuName); // 修改Toolbar菜单的名字
//
//		switch (menuName) {
//		case "a":
//			if (indexFragment == null) {
//				// Log.i("wang", "iFragment = new IndexFragment()");
//				indexFragment = new IndexFragment();
//			}
//			switchContent(contentFragment, indexFragment);
//			isMenuShuffle = false;
//			break;
//		case "b":
//			if (testFragment == null) {
//				testFragment = new TestFragment();
//			}
//			switchContent(contentFragment, testFragment);
//			isMenuShuffle = true;
//			break;
//		}
//		invalidateOptionsMenu();
//
//		/**
//		 * 关闭左侧滑出菜单
//		 */
//		drawer_main.closeDrawers();
//	}

	/**
	 * 当fragment进行切换时，采用隐藏与显示的方法加载fragment以防止数据的重复加载
	 * 
	 * @param from
	 * @param to
	 */
//	public void switchContent(Fragment from, Fragment to) {
//		// if (isFragment != to) {
//		// isFragment = to;
//		// FragmentManager fm = getSupportFragmentManager();
//		// // 添加渐隐渐现的动画
//		// FragmentTransaction ft = fm.beginTransaction();
//		// if (!to.isAdded()) { // 先判断是否被add过
//		// ft.hide(from).add(R.id.frame_main, to).commit(); //
//		// 隐藏当前的fragment，add下一个到Activity中
//		// } else {
//		// ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
//		// }
//		// }
//		contentFragment = to;
//		this.getSupportFragmentManager().beginTransaction()
//				.replace(R.id.frame_main, contentFragment).commit();
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_demo, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.action_collect:
			Toast.makeText(this, "action_collect", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_send:
			Toast.makeText(this, "action_send", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_copy_link:
			Toast.makeText(this, "action_copy_link", Toast.LENGTH_LONG).show();
			break;
		case R.id.action_open_by_browser:
			Toast.makeText(this, "action_open_by_browser", Toast.LENGTH_LONG)
					.show();
			break;
		// case R.id.main_toolbar_shuffle:
		// Toast.makeText(this, "main_toolbar_shuffle", Toast.LENGTH_LONG)
		// .show();
		// break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// Log.e("isMenuShuffle",isMenuShuffle + "");
		if (isMenuShuffle) {
			menu.findItem(R.id.action_send).setVisible(true);
		} else {
			menu.findItem(R.id.action_send).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {
		// TODO Auto-generated method stub
		
	}

	public void switchFragment(Fragment f, int state) {
		 // 修改Toolbar菜单的名字
		if (state == 0) {
			getSupportActionBar().setTitle(f.getClass().getSimpleName());
			contentFragment = f;
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame_main, contentFragment).commit();
			invalidateOptionsMenu();

			/**
			 * 关闭左侧滑出菜单
			 */
			drawer_main.closeDrawers();
		}
//		switch (menuName) {
//		case "a":
//			if (indexFragment == null) {
//				// Log.i("wang", "iFragment = new IndexFragment()");
//				indexFragment = new IndexFragment();
//			}
//			switchContent(contentFragment, indexFragment);
//			isMenuShuffle = false;
//			break;
//		case "b":
//			if (testFragment == null) {
//				testFragment = new TestFragment();
//			}
//			switchContent(contentFragment, testFragment);
//			isMenuShuffle = true;
//			break;
//		}
		
		
	}

}
