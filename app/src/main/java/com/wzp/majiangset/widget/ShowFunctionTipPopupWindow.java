package com.wzp.majiangset.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wzp.majiangset.R;

public class ShowFunctionTipPopupWindow extends PopupWindow {

	private TextView tvFunTip;


	@SuppressLint("InflateParams")
	public ShowFunctionTipPopupWindow(Context context) {
		super((int) (160 * context.getResources().getDisplayMetrics().density),
				ViewGroup.LayoutParams.WRAP_CONTENT);

		tvFunTip = (TextView) LayoutInflater.from(context).inflate(R.layout.pop_win_show_fun_tip, null);

		setContentView(tvFunTip);
		setBackgroundDrawable(context.getResources().getDrawable(R.drawable.pw_show_fun_tip_bg));
		setFocusable(true);
		setAnimationStyle(R.style.popWinScaleTheme);
	}

	public void setFunTip(String tip) {
		tvFunTip.setText(tip);
	}

	public void setFunTip(int tipId) {
		tvFunTip.setText(tipId);
	}

}
