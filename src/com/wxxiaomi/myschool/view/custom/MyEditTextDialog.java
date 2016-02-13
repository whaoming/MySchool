package com.wxxiaomi.myschool.view.custom;

import com.wxxiaomi.myschool.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MyEditTextDialog {

	private AlertDialog dialog;
	private Context ct;
	OnMyClickListener lis;
	private EditText et_input;
	

	public MyEditTextDialog(Context ct,OnMyClickListener lis) {
		super();
		this.ct = ct;
		this.lis = lis;
	}

	@SuppressLint("InflateParams")
	public MyEditTextDialog builder() {
		View view = LayoutInflater.from(ct).inflate(R.layout.dialog_test, null);
		et_input = (EditText) view.findViewById(R.id.et_input);
		dialog = new AlertDialog.Builder(ct)
				.setView(view)
				.setPositiveButton("ok",
						new android.content.DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								lis.click(et_input.getText().toString());
							}
						}).create();
		

		return this;
	}
	
	public void show(){
		dialog.show();
	}
	public interface OnMyClickListener{
		void click(String input);
	}
}
