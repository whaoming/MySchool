package com.wxxiaomi.myschool.view.custom;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.wxxiaomi.myschool.R;
import com.wxxiaomi.myschool.util.CommonUtil;

/**
 * 验证码dialog
 * 传入图片地址，内部封装并加载图片
 * 暴露接口
 * @author Administrator
 *
 */
public class MyCodeDialog{

	Context ct;
	private Dialog dialog;
	private Button btn_ok;
	private SwipeRefreshLayout mSwipeRefreshWidget;
	private ImageView iv_pic;
	private EditText et_input;
	private OkButonnListener lis;
	
	public MyCodeDialog(Context context) {
		this.ct = context;
	}
	
	@SuppressLint("InflateParams")
	public MyCodeDialog builder(){
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
		mSwipeRefreshWidget.setEnabled(false);
		
		dialog = new Dialog(ct); 
//		dialog.
//		dialog = new AlertDialog.Builder(ct).create(); 
//		dialog.setView(view);
		dialog.show();
//		Window window = dialog.getWindow();
//		window.setContentView(view);
		dialog.setContentView(view);
		
//		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0)); 
		
		
		dialog.setCanceledOnTouchOutside(false);
		mSwipeRefreshWidget.setRefreshing(true);
		return this;
		
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
