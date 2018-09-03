package com.wzp.majiangset.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.wzp.majiangset.R;


/**
 * 自定义进度对话框
 * @author WZP
 *
 */
public class MyProgressDialog extends Dialog {
	
	private Context context;
	private View view;
	private TextView tvTip;
	

	public MyProgressDialog(Context context) {
		super(context, R.style.loading_dialog);
		this.context = context;
		init();
	}

	public void setMessage(String msg) {
		tvTip.setText(msg);
	}

	public void show(String msg) {
		setMessage(msg);
		super.show();
	}

	private void init() {
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.dialog_progress, null);
		tvTip = (TextView) view.findViewById(R.id.tv_tip);

		setContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

}
