package com.wxxiaomi.myschool.view.custom;

import com.wxxiaomi.myschool.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

public class LoadingDialog {

	private AlertDialog dialog;
	private Context ct;

	public LoadingDialog(Context ct) {
		super();
		this.ct = ct;
	}

	@SuppressLint("InflateParams")
	public LoadingDialog builder() {
		View view = LayoutInflater.from(ct).inflate(R.layout.dialog_loading,
				null);
		dialog = new AlertDialog.Builder(ct).setView(view).setCancelable(false).create();
		return this;
	}
	
	public LoadingDialog show(){
		dialog.show();
		return this;
	}
	public void dismiss(){
		dialog.dismiss();
	}
	public LoadingDialog setMessage(String msg){
		dialog.setMessage(msg);
		return this;
	}
}
