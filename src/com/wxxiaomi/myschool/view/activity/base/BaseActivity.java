package com.wxxiaomi.myschool.view.activity.base;

import com.wxxiaomi.myschool.view.custom.LoadingDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;


public abstract class BaseActivity extends AppCompatActivity implements
		OnClickListener {

	protected Context ct;
//	@ViewInject(R.id.loading_view)
//	protected View loadingView;
//	@ViewInject(R.id.ll_load_fail)
//	protected LinearLayout loadfailView;
//	protected ImageButton rightBtn;
//	protected ImageButton leftImgBtn;
//	protected TextView leftImgBtn;
//	protected ImageButton rightImgBtn;
//	protected TextView titleTv;
//	protected TextView rightbutton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		AppManager.getAppManager().addActivity(this);
		ct = this;
		initView();
//		loadingView = findViewById(R.id.loading_view);
//		loadfailView = (LinearLayout) findViewById(R.id.ll_load_fail);
		initData();
	}

	protected void initTitleBar() {
//		leftBtn = (Button) findViewById(R.id.btn_left);
//		rightBtn = (ImageButton) findViewById(R.id.btn_right);
//		rightbutton = (TextView) findViewById(R.id.rightbutton);
//		if (leftBtn != null) {
//			leftBtn.setVisibility(View.GONE);
//		}
//		if (rightBtn != null) {
//			rightBtn.setVisibility(View.GONE);
//		}
//		leftImgBtn = (ImageButton) findViewById(R.id.imgbtn_left);
//		leftImgBtn = (TextView) findViewById(R.id.activity_selectimg_back);
//		rightImgBtn = (ImageButton) findViewById(R.id.imgbtn_right);
//		if (rightImgBtn != null) {
//			rightImgBtn.setVisibility(View.INVISIBLE);
//		}
//		if (leftImgBtn != null) {
//			leftImgBtn.setImageResource(R.drawable.back);
//		}
//		titleTv = (TextView) findViewById(R.id.txt_title);
//		if (leftImgBtn != null) {
//			leftImgBtn.setOnClickListener(this);
//		}
//		if (rightBtn != null) {
//			rightBtn.setOnClickListener(this);
////			rightbutton.setVisibility(View.GONE);
//		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		AppManager.getAppManager().finishActivity(this);
//	}

	@Override
	public void onClick(View v) {
		processClick(v);
		switch (v.getId()) {
//		case R.id.activity_selectimg_back:
//			this.finish();
//			break;
		default:
			break;
		}
	}

	LoadingDialog dialog;
	protected void showLoadingDialog(String content){
		dialog = new LoadingDialog(ct).builder().setMessage(content);
		dialog.show();
	}
	protected void closeLoadingDialog(){
		if(dialog != null){
			dialog.dismiss();
		}
	}
	
	AlertDialog msgDialog ;
	protected void showMsgDialog(String content){
		msgDialog = new AlertDialog.Builder(ct).setMessage(content).setPositiveButton("确定", null).create();
//		msgDialog.set
		msgDialog.show();
	}
	
	protected void closeMsgDialog(){
		if(msgDialog != null){
			msgDialog.dismiss();
		}
	}
		
//	MingEditDialog pDialog;
//	protected void showMingProcessDialog(String content){
//		pDialog = new MingEditDialog(this,
//				 MingEditDialog.PROGRESS).setTitleText(content);
//			pDialog.show();
//			pDialog.setCancelable(false);
//	}
//	protected void closeMingDialog(){
//		if(pDialog!=null){
//			pDialog.dismiss();
//		}
//	}
//	protected void showErrorDialog(String error){
//		if(pDialog!=null){
//			pDialog.setTitleText(error)
//			.setConfirmText("确定")
//			.changeAlertType(MingEditDialog.ERROR);
//		}else{
//			pDialog = new MingEditDialog(ct, MingEditDialog.ERROR);
//			pDialog.setTitleText(error);
//			pDialog.show();
//		}
//	}

	
//	protected CustomProgressDialog dialog;
//
//	protected void showProgressDialog(String content) {
//		if (dialog == null && ct != null) {
//			dialog = (CustomProgressDialog) DialogUtils.createProgressDialog(ct,
//					content);
//		}
//		dialog.show();
//	}
//
//	protected void closeProgressDialog() {
//		if (dialog != null)
//			dialog.dismiss();
//	}

//	public void showLoadingView() {
//		if (loadingView != null)
//			loadingView.setVisibility(View.VISIBLE);
//	}
//
//	public void dismissLoadingView() {
//		if (loadingView != null)
//			loadingView.setVisibility(View.INVISIBLE);
//	}
//
//	public void showLoadFailView() {
//		if (loadingView != null) {
//			loadingView.setVisibility(View.VISIBLE);
//			loadfailView.setVisibility(View.VISIBLE);
//		}
//
//	}
//
//	public void dismissLoadFailView() {
//		if (loadingView != null)
//			loadfailView.setVisibility(View.INVISIBLE);
//	}

	protected abstract void initView();

	protected abstract void initData();

	protected abstract void processClick(View v);


	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
	}
	
//	public void initRight(String a){
//		rightbutton.setOnClickListener(this);
//		rightbutton.setVisibility(View.VISIBLE);
//		rightbutton.setText(a);
//	}

	
}
