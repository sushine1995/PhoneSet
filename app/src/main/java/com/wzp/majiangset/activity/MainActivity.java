package com.wzp.majiangset.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.CheckPermissionsActivity;

public class MainActivity extends CheckPermissionsActivity {

	private ImageButton ibtnBack;

	private Button btnAboveTable;
	private Button btnBelowTable;
	private Button btnHand;
	private Button btnBaopai;
	private Button btnRecoDire; // 识别方位


	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ibtn_back:
					onBackPressed();
					break;

				case R.id.ibtn_aboveTable:
					ShowMajiangActivity.myStartActivity(MainActivity.this, true);
					break;

				case R.id.ibtn_belowTable:
					ShowMajiangActivity.myStartActivity(MainActivity.this, false);
					break;

				case R.id.ibtn_hand:
					ShowHandMajiangActivity.myStartActivity(MainActivity.this);
					break;

				case R.id.ibtn_baopai:
					ShowBaopaiActivity.myStartActivity(MainActivity.this);
					break;

				case R.id.ibtn_recoDire:
					RecognizeDirectionActivity.myStartActivity(MainActivity.this);
					break;

				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initParam();
		initWidget();
	}

	private void initParam() {

	}

	private void initWidget() {
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);

		btnAboveTable = (Button) findViewById(R.id.ibtn_aboveTable);
		btnBelowTable = (Button) findViewById(R.id.ibtn_belowTable);
		btnHand = (Button) findViewById(R.id.ibtn_hand);
		btnBaopai = (Button) findViewById(R.id.ibtn_baopai);
		btnRecoDire = (Button) findViewById(R.id.ibtn_recoDire);

		ibtnBack.setOnClickListener(listener);
		btnAboveTable.setOnClickListener(listener);
		btnBelowTable.setOnClickListener(listener);
		btnHand.setOnClickListener(listener);
		btnBaopai.setOnClickListener(listener);
		btnRecoDire.setOnClickListener(listener);
	}

	public static void myStartActivity(Context context) {
		Intent intent = new Intent(context, MainActivity.class);
		context.startActivity(intent);
	}
}
