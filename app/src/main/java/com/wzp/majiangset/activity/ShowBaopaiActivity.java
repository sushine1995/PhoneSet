package com.wzp.majiangset.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BluetoothBaseActivity;
import com.wzp.majiangset.util.CalculateUtil;
import com.wzp.majiangset.widget.MyApplication;

public class ShowBaopaiActivity extends BluetoothBaseActivity {

	private ImageButton ibtnBack;
	private ImageButton ibtnMoreFun;

	private TextView tvId;
	private LinearLayout linearMajiang;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_baopai);

		initParam();
		initWidget();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (MyApplication.btClientHelper == null
				|| !MyApplication.btClientHelper.isBluetoothConnected()) {
			showToast("蓝牙设备尚未连接");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initParam() {

	}

	private void initWidget() {
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
		ibtnMoreFun = (ImageButton) findViewById(R.id.ibtn_moreFun);

		tvId = (TextView) findViewById(R.id.tv_id);
		linearMajiang = (LinearLayout) findViewById(R.id.linear_majiang);

		ibtnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
	}

	/**
	 * 启动Activity
	 *
	 * @param context
	 */
	public static void myStartActivity(Context context) {
		Intent intent = new Intent(context, ShowBaopaiActivity.class);
		context.startActivity(intent);
	}

	/**
	 * 更新麻将牌
	 *
	 * @param recvData
	 */
	private void updateMajiang(byte[] recvData) {
		String strCode;
		int code = CalculateUtil.byteToInt(recvData[1]);
		if (code == 0xc1) {
			strCode = "多牌";
		} else if (code == 0xc2) {
			strCode = "少牌";
		} else {
			strCode = Integer.toHexString(code);
		}
		tvId.setText(strCode);

		int num = CalculateUtil.byteToInt(recvData[2]);
		// num最多不能超过17
		if (num > 19) {
			Log.e(LOG_TAG, "麻将牌数目异常：" + num);
			return;
		}

		if(num > linearMajiang.getChildCount()) {
			int addedChildCount = num - linearMajiang.getChildCount();

//					LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//							LinearLayout.LayoutParams.WRAP_CONTENT,
//							LinearLayout.LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					(int) getResources().getDimensionPixelSize(R.dimen.a_majiang_width),
					(int) getResources().getDimensionPixelSize(R.dimen.a_majiang_height));
//					params.setMargins(2, 0, 0, 0);
			ImageView ivMajiang = null;
			for (int i = 0; i < addedChildCount; i++) {
				ivMajiang = new ImageView(ShowBaopaiActivity.this);
				ivMajiang.setLayoutParams(params);

				linearMajiang.addView(ivMajiang);
			}
		} else {
			int delChidCount = linearMajiang.getChildCount() - num;
			for (int i = 0; i < delChidCount; i++) {
				linearMajiang.removeViewAt(linearMajiang.getChildCount() - 1);
			}
		}

		Bitmap majiangBitmap = null;
		int imageId; // 麻将图片资源ID
		for (int i = 0; i < num; i++) {
			imageId = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[3 + i]));
			if (imageId != -1) {
				// 正常显示麻将图片
				majiangBitmap = BitmapFactory.decodeResource(getResources(), imageId);
				((ImageView) linearMajiang.getChildAt(i)).setImageBitmap(majiangBitmap);
				linearMajiang.getChildAt(i).setVisibility(View.VISIBLE);
			} else {
				// 当前位置不显示麻将
				linearMajiang.getChildAt(i).setVisibility(View.INVISIBLE);
			}
		}
	}


	@Override
	protected void onBluetoothDataReceived(byte[] recvData) {
		int funCode = CalculateUtil.byteToInt(recvData[1]);
		if (funCode >= 0xc1 && funCode <= 0xcf) {
			updateMajiang(recvData);
		}
	}
}
