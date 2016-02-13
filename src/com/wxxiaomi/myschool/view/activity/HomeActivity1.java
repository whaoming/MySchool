package com.wxxiaomi.myschool.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.wxxiaomi.myschool.ConstantValue;
import com.wxxiaomi.myschool.GlobalParams;
import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Lib_Main;
import com.wxxiaomi.myschool.bean.webpage.page.Html_Main;
import com.wxxiaomi.myschool.bean.webpage.page.Html_lib_Login;
import com.wxxiaomi.myschool.bean.webpage.request.ResponseData;
import com.wxxiaomi.myschool.engine.LibraryEngineImpl;
import com.wxxiaomi.myschool.engine.OfficeEngineImpl;
import com.wxxiaomi.myschool.view.custom.CircularImageView;
import com.wxxiaomi.myschool.view.custom.LoadingDialog;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2;
import com.wxxiaomi.myschool.view.custom.MyCodeDialog2.OkButonnListener;
import com.wxxiaomi.myschool.view.fragment.ElectiveCourseFragment;
import com.wxxiaomi.myschool.view.fragment.IndexFragment;
import com.wxxiaomi.myschool.view.fragment.LibBorrowStateFragment1;
import com.wxxiaomi.myschool.view.fragment.ScoreFragment1;
import com.wxxiaomi.myschool.view.fragment.base.BaseFragment;
import com.wxxiaomi.myschool.view.fragment.base.FragmentCallback;

public class HomeActivity1 extends AppCompatActivity implements
		FragmentCallback, OnClickListener {
	private Toolbar toolbar; // 定义toolbar
	private ActionBarDrawerToggle mDrawerToggle; // 定义toolbar左上角的弹出左侧菜单按钮
	private DrawerLayout drawer_main; // 定义左侧滑动布局，其实就是主布局
	private NavigationView navigationView;

	private Fragment contentFragment; // 记录当前正在使用的fragment
	private boolean isMenuShuffle = false;
	private CircularImageView iv_head;
	private Html_Main main;
	private MainChangeListener lis;
	private CharSequence title = "首页";
	private TextView header_tv;

	/**
	 * 图书馆登录页面bean
	 */
	private Html_lib_Login html_lib_login;
	/**
	 * 图书馆主页面bean
	 */
	private Html_Lib_Main html_lib_main;
	private LibMainChangeListener libLis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home1);
		navigationView = (NavigationView) findViewById(R.id.navigation);
		toolbar = (Toolbar) this.findViewById(R.id.toolbar);
		drawer_main = (DrawerLayout) findViewById(R.id.dl_left);
		iv_head = (CircularImageView) navigationView.getHeaderView(0)
				.findViewById(R.id.iv_head);
		header_tv = (TextView) navigationView.getHeaderView(0).findViewById(
				R.id.header_tv);
		initFragment(savedInstanceState);
		initToolBar();
		initDrawer();
		initData();
	}

	/**
	 * 初始化侧滑栏
	 */
	private void initDrawer() {
		mDrawerToggle = new ActionBarDrawerToggle(this, drawer_main, toolbar,
				R.string.drawer_open, R.string.drawer_close);
		mDrawerToggle.syncState();
		drawer_main.setDrawerListener(mDrawerToggle);
		iv_head.setOnClickListener(this);
		initDrawerHeader();
	}

	/**
	 * 初始化侧滑栏的头部
	 */
	private void initDrawerHeader() {
		if (ConstantValue.isLogin) {
			BitmapUtils bitmapUtil = new BitmapUtils(this);
			bitmapUtil.display(iv_head, ConstantValue.LOTTERY_URI
					+ GlobalParams.gloUserInfo.userInfo.pic);
			header_tv.setText(GlobalParams.gloUserInfo.userInfo.name);
		} else {
			header_tv.setText("登录");
			header_tv.setOnClickListener(this);
		}

	}

	/**
	 * 初始化fragment
	 * 
	 * @param savedInstanceState
	 */
	private void initFragment(Bundle savedInstanceState) {
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
	}

	/**
	 * 初始化toolbar
	 */
	private void initToolBar() {
		// 标题的文字需在setSupportActionBar之前，不然会无效
		toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
		toolbar.setTitle(title);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("首页");
		getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

	}

	private void setToolBarTitle(String title) {
		getSupportActionBar().setTitle(title);
	}

	private MenuItem currentItem;

	/**
	 * 初始化数据，各种监听按钮事件
	 */
	private void initData() {
		currentItem = navigationView.getMenu().findItem(R.id.drawer_home);
		currentItem.setChecked(true);
		navigationView
				.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
					@Override
					public boolean onNavigationItemSelected(MenuItem menuItem) {
						if (currentItem.getItemId() != menuItem.getItemId()) {
							boolean isLogin = checkLogin();
							if (isLogin) {
								if (changFragmentById(menuItem)) {
									currentItem.setChecked(false);
									menuItem.setChecked(true);
									currentItem = menuItem;
								}

							}
						}
						drawer_main.closeDrawers();
						return true;
					}

				});
	}

	private boolean changFragmentById(MenuItem menuItem) {
		boolean f = true;
		switch (menuItem.getItemId()) {
		case R.id.drawer_score:
			setToolBarTitle("成绩查询");
			switchFragment(new ScoreFragment1(), 0);
			break;
		case R.id.drawer_home:
			setToolBarTitle("首页");
			switchFragment(new IndexFragment(), 0);
			break;
		case R.id.drawer_classform:
			break;
		case R.id.drawer_elective:
			setToolBarTitle("选课情况");
			switchFragment(new ElectiveCourseFragment(), 0);
			break;
		case R.id.drawer_lib_borrow_state:
			setToolBarTitle("借阅情况");
			switchFragment(new LibBorrowStateFragment1(), 0);
			break;
		case R.id.drawer_lib_search:
			Intent intent = new Intent(HomeActivity1.this,
					LibSearchActivity.class);
			startActivity(intent);
			f = false;
		default:
			break;
		}
		return f;
	}

	boolean flag;

	public boolean checkLogin() {
		if (ConstantValue.isLogin) {
			flag = true;
		} else {
			Log.i("wang", "进入checkLogin->ConstantValue.isLogin=false");
			new AlertDialog.Builder(this)
					.setMessage("当前未登录,是否现在前去登录?")
					.setPositiveButton(
							"是的",
							new android.content.DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Intent intent = new Intent(
											HomeActivity1.this,
											LoginActivity.class);
									startActivityForResult(intent,
											ConstantValue.LOGINACTIVITY);
								}
							})
					.setNegativeButton(
							"取消",
							new android.content.DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									flag = false;
								}
							}).setCancelable(false).create().show();
		}
		return flag;
	}

	/**
	 * 当fragment进行切换时，采用隐藏与显示的方法加载fragment以防止数据的重复加载
	 * 
	 * @param from
	 * @param to
	 */
	// public void switchContent(Fragment from, Fragment to) {
	// // if (isFragment != to) {
	// // isFragment = to;
	// // FragmentManager fm = getSupportFragmentManager();
	// // // 添加渐隐渐现的动画
	// // FragmentTransaction ft = fm.beginTransaction();
	// // if (!to.isAdded()) { // 先判断是否被add过
	// // ft.hide(from).add(R.id.frame_main, to).commit(); //
	// // 隐藏当前的fragment，add下一个到Activity中
	// // } else {
	// // ft.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
	// // }
	// // }
	// contentFragment = to;
	// this.getSupportFragmentManager().beginTransaction()
	// .replace(R.id.frame_main, contentFragment).commit();
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		case android.R.id.home:
			drawer_main.openDrawer(GravityCompat.START);
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		if (isMenuShuffle) {
			menu.findItem(R.id.action_send).setVisible(true);
		} else {
			menu.findItem(R.id.action_send).setVisible(false);
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public void onFragmentCallback(BaseFragment fragment, int id, Bundle args) {
		switch (id) {
		case ConstantValue.OfficeNoLogin:
			// 正方系统还未登录
			loginOfficeByNet(GlobalParams.gloUserInfo.userInfo.officeUserInfo.username,
					GlobalParams.gloUserInfo.userInfo.officeUserInfo.password);
			break;
		case 0:
			// office地址过期
			handOfficeOutTime();
			break;
		case ConstantValue.LIBNOLOGIN:
			// 图书馆未登录
			LibLoginByNet();
			break;
		default:
			break;
		}
	}

	/**
	 * 登录正方系统获取main
	 * 
	 * @param username
	 * @param password
	 */
	private void loginOfficeByNet(final String username, final String password) {
		final LoadingDialog dialog = new LoadingDialog(this).builder()
				.setMessage("正在登录..").show();
		new AsyncTask<String, Void, ResponseData<Html_Main>>() {
			@Override
			protected ResponseData<Html_Main> doInBackground(String... params) {
				OfficeEngineImpl impl = new OfficeEngineImpl();
//				Log.i("wang", "username=" + username + "--psd=" + password);
				return impl.getOfficeMainHtml2BeanByOne(username, password);
			}

			@Override
			protected void onPostExecute(ResponseData<Html_Main> result) {
				dialog.dismiss();
				if (result.isSuccess()) {

					main = result.getObj();
					lis.change(main);
				} else {
					// 获取失败
				}
				super.onPostExecute(result);
			}
		}.execute();

	}

	/**
	 * 登录图书馆获取main并利用libLis通知
	 */
	private void LibLoginByNet() {
		final LoadingDialog loodingDialog = new LoadingDialog(
				HomeActivity1.this).builder();
		loodingDialog.setMessage("正在获取数据....");
		final AlertDialog errorDialog = new AlertDialog.Builder(this)
				.setPositiveButton("确定", null).setTitle("错误").create();
		// 显示dialog
		final MyCodeDialog2 dialog2 = new MyCodeDialog2(this).builder();
		dialog2.show();
		// final MyCodeDialog dialog = new MyCodeDialog(this).builder();
		// 获取图书馆登录页面的bean,其中包括piccode
		new AsyncTask<String, Void, ResponseData<Html_lib_Login>>() {
			@Override
			protected ResponseData<Html_lib_Login> doInBackground(
					String... params) {
				LibraryEngineImpl engine = new LibraryEngineImpl();
				return engine.getLibLoginPageAndCodePic();
			}

			@Override
			protected void onPostExecute(ResponseData<Html_lib_Login> result) {
				if (result.isSuccess()) {

					html_lib_login = result.getObj();
					LibLogin();
				} else {
					// 重新获取地址失败
				}
				super.onPostExecute(result);
			}

			/**
			 * 获取完图书馆登录页面的bean后，执行登录操作
			 */
			private void LibLogin() {
				dialog2.setImage(html_lib_login.getPicCode());
				dialog2.setOnOkButtonListener(new OkButonnListener() {
					@Override
					public void onClick(String input) {
						loodingDialog.show();
						login(input);
					}

					private void login(final String input) {
						new AsyncTask<String, Void, ResponseData<Html_Lib_Main>>() {

							@Override
							protected ResponseData<Html_Lib_Main> doInBackground(
									String... params) {
								LibraryEngineImpl engine = new LibraryEngineImpl();
								return engine.Login(html_lib_login,
										"131110199", "987987987", input);
							}

							@Override
							protected void onPostExecute(
									ResponseData<Html_Lib_Main> result) {
								loodingDialog.dismiss();
								if (result.isSuccess()) {
									html_lib_main = result.getObj();
									libLis.change(html_lib_main);
									dialog2.dismiss();
								} else {
									// 登录失败:原因是验证码错误或者账号密码错误
									errorDialog.setMessage(result.getError());
									errorDialog.show();
								}
								super.onPostExecute(result);
							}
						}.execute();
					}
				});

			}
		}.execute();
	}

	private void handOfficeOutTime() {
		new AsyncTask<String, Void, ResponseData<Html_Main>>() {
			@Override
			protected ResponseData<Html_Main> doInBackground(String... params) {
				OfficeEngineImpl impl = new OfficeEngineImpl();
				return impl.getOfficeMainHtml2BeanByOne(main.getUsername(),
						main.getPassword());
			}

			@Override
			protected void onPostExecute(ResponseData<Html_Main> result) {
				if (result.isSuccess()) {
					main = result.getObj();
					lis.change(main);
				} else {
					// 重新获取地址失败
				}
				super.onPostExecute(result);
			}
		}.execute();

	}

	public void switchFragment(Fragment f, int state) {
		// 修改Toolbar菜单的名字
		if (state == 0) {
			// getSupportActionBar().setTitle(f.getClass().getSimpleName());
			contentFragment = f;
			this.getSupportFragmentManager().beginTransaction()
					.replace(R.id.frame_main, contentFragment).commit();
			invalidateOptionsMenu();
			/**
			 * 关闭左侧滑出菜单
			 */
			drawer_main.closeDrawers();
		}
		// switch (menuName) {
		// case "a":
		// if (indexFragment == null) {
		// // Log.i("wang", "iFragment = new IndexFragment()");
		// indexFragment = new IndexFragment();
		// }
		// switchContent(contentFragment, indexFragment);
		// isMenuShuffle = false;
		// break;
		// case "b":
		// if (testFragment == null) {
		// testFragment = new TestFragment();
		// }
		// switchContent(contentFragment, testFragment);
		// isMenuShuffle = true;
		// break;
		// }

	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.iv_head:
			if(ConstantValue.isLogin){
				intent = new Intent(this, UserInfoActivity.class);
				startActivity(intent);
			}else{
				intent = new Intent(this, LoginActivity.class);
				startActivityForResult(intent, ConstantValue.LOGINACTIVITY);
			}
			break;
		case R.id.header_tv:
			intent = new Intent(this, LoginActivity.class);
			startActivityForResult(intent, ConstantValue.LOGINACTIVITY);
			break;
		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (resultCode) {
		case ConstantValue.LOGINACTIVITY:
			// 登录成功后回来了
			initDrawerHeader();
			// changFragmentByMenuId(currentNavigationItemid);
			break;

		default:
			break;
		}
	}

	public Html_Lib_Main getLibMain() {
		return html_lib_main;
	}

	public Html_Main getOfficeHtmlMain() {
		return main;
	}

	public interface MainChangeListener {
		void change(Html_Main main);
	}

	public void setMainChangeListener(MainChangeListener lis) {
		this.lis = lis;
	}

	public interface LibMainChangeListener {
		void change(Html_Lib_Main html_lib_main);
	}

	public void setLibMainChangeListener(LibMainChangeListener libLis) {
		this.libLis = libLis;
	}

}
