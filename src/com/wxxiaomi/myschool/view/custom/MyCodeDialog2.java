package com.wxxiaomi.myschool.view.custom;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.util.CommonUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MyCodeDialog2 {

	private Context ct;
	private AlertDialog dialog;
	private Button btn_ok;
	private SwipeRefreshLayout mSwipeRefreshWidget;
	private ImageView iv_pic;
	private EditText et_input;
	private OkButonnListener lis;
	public MyCodeDialog2(Context context) {
		this.ct = context;
	}
	
	@SuppressLint("InflateParams")
	public MyCodeDialog2 builder(){
		View view = LayoutInflater.from(ct).inflate(R.layout.dialog_code, null);
		et_input = (EditText) view.findViewById(R.id.et_input);
		iv_pic = (ImageView) view.findViewById(R.id.iv_pic);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mSwipeRefreshWidget.setRefreshing(false);
				lis.onClick(et_input.getText().toString().trim());
			}
		});
		
		mSwipeRefreshWidget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
		mSwipeRefreshWidget.setColorSchemeResources(R.color.color1,
				R.color.color2, R.color.color3, R.color.color4);
		mSwipeRefreshWidget.setProgressViewOffset(false, 0, CommonUtil.dip2px(ct, 24));
		mSwipeRefreshWidget.setEnabled(true);
		
		dialog = new AlertDialog.Builder(ct)
		.setNegativeButton("取消", null)
		.setPositiveButton("确定", null).setView(view).create(); 
		
		
		mSwipeRefreshWidget.setRefreshing(true);
		return this;
		
	}
	
	public void show(){
		dialog.show();
	}
	
	public void setOnOkButtonListener(OkButonnListener lis){
		this.lis = lis;
	}

	public void setImage(Bitmap pic){
		mSwipeRefreshWidget.setRefreshing(false);
		iv_pic.setImageBitmap(pic);
	}
	public void dismiss(){
		dialog.dismiss();
	}
	
	public interface OkButonnListener{
		void onClick(String input);
	}
}
