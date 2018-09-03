package com.wzp.majiangset.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BluetoothBaseActivity;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.util.CRC16;
import com.wzp.majiangset.util.CalculateUtil;
import com.wzp.majiangset.widget.AddSubWidget;
import com.wzp.majiangset.widget.MyApplication;

public class SettingActivity extends BluetoothBaseActivity {

	private ImageButton ibtnBack;

	private Spinner spBaopaizhangshu; // 报牌张数
	private Spinner spPaishu; // 牌数
	private Spinner spMeishouzhangpai; // 每手张牌
	private Spinner spPlayerNum; // 人数
	private Spinner spTiaopai; // 跳牌类型
	private CheckBox cbType; // 牌类型（是否为ID牌）
	private AddSubWidget asEast; // 东方位牌数
	private AddSubWidget asSouth; // 南方位牌数
	private AddSubWidget asWest; // 西方位牌数
	private AddSubWidget asNorth; // 北方位牌数
	private Button btnConfirm;

	private SharedPreferences pref;

	private Integer[] baopaizhangshuArray = new Integer[] {3, 4, 5, 6, 7, 8};
	private Integer[] paishuArray = new Integer[] {13, 16};
	private Integer[] meishouzhangpaiArray = new Integer[] {2, 4, 6, 12};
	private Integer[] playerNumArray = new Integer[] {4, 3, 2};
	private String[] tiaopaiArray = new String[] {"隔跳", "上下跳", "平跳"};


	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ibtn_back:
					onBackPressed();
					break;

				case R.id.btn_confirm:
					if (MyApplication.btClientHelper.isBluetoothConnected()) {
						asEast.setCurValue(asEast.getCurVal());
						asSouth.setCurValue(asSouth.getCurVal());
						asWest.setCurValue(asWest.getCurVal());
						asNorth.setCurValue(asNorth.getCurVal());

						byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
						sendMsg[0] = (byte) 0xfe;
						sendMsg[1] = (byte) 0x01;
						sendMsg[2] = ((Integer)spBaopaizhangshu.getSelectedItem()).byteValue();
						sendMsg[3] = ((Integer)spPaishu.getSelectedItem()).byteValue();
						sendMsg[4] = ((Integer)spMeishouzhangpai.getSelectedItem()).byteValue();
						sendMsg[5] = ((Integer)spPlayerNum.getSelectedItem()).byteValue();
						sendMsg[6] = (byte) spTiaopai.getSelectedItemPosition();
						sendMsg[7] = (byte) (cbType.isChecked() ? 1 : 0);
						sendMsg[8] = (byte) asEast.getCurVal();
						sendMsg[9] = (byte) asSouth.getCurVal();
						sendMsg[10] = (byte) asWest.getCurVal();
						sendMsg[11] = (byte) asNorth.getCurVal();
						byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
						sendMsg[ProjectConstants.SEND_MSG_LENGTH - 2] = crc[0];
						sendMsg[ProjectConstants.SEND_MSG_LENGTH - 1] = crc[1];

						MyApplication.btClientHelper.write(sendMsg);

						Log.d(LOG_TAG, "报牌张数：" + spBaopaizhangshu.getSelectedItem()
								+ "跳牌类型：" + spTiaopai.getSelectedItemPosition());
					}

					break;

				default:
					break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		initParam();
		initWidget();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (MyApplication.btClientHelper == null
				|| !MyApplication.btClientHelper.isBluetoothConnected()) {
			showToast("蓝牙设备尚未连接");
			btnConfirm.setEnabled(false);
		}
	}

	private void initParam() {
		pref = getSharedPreferences("setting_prefs", Context.MODE_PRIVATE);
	}

	private void initWidget() {
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
		spBaopaizhangshu = (Spinner) findViewById(R.id.sp_baopaizhangshu);
		spPaishu = (Spinner) findViewById(R.id.sp_paishu);
		spMeishouzhangpai = (Spinner) findViewById(R.id.sp_meishouzhangpai);
		spPlayerNum = (Spinner) findViewById(R.id.sp_playerNum);
		spTiaopai = (Spinner) findViewById(R.id.sp_tiaopai);
		cbType = (CheckBox) findViewById(R.id.cb_type);
		asEast = (AddSubWidget) findViewById(R.id.as_east);
		asSouth = (AddSubWidget) findViewById(R.id.as_south);
		asWest = (AddSubWidget) findViewById(R.id.as_west);
		asNorth = (AddSubWidget) findViewById(R.id.as_north);
		btnConfirm = (Button) findViewById(R.id.btn_confirm);

		ibtnBack.setOnClickListener(listener);
		btnConfirm.setOnClickListener(listener);

		spBaopaizhangshu.setAdapter(new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_dropdown_item, baopaizhangshuArray));
		spPaishu.setAdapter(new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_dropdown_item, paishuArray));
		spMeishouzhangpai.setAdapter(new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_dropdown_item, meishouzhangpaiArray));
		spPlayerNum.setAdapter(new ArrayAdapter<Integer>(this,
				android.R.layout.simple_spinner_dropdown_item, playerNumArray));
		spTiaopai.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, tiaopaiArray));


		// 控件内容初始化
		spBaopaizhangshu.setSelection(pref.getInt("baopaizhangshu", 0));
		spPaishu.setSelection(pref.getInt("paishu", 0));
		spMeishouzhangpai.setSelection(pref.getInt("meishouzhangpai", 0));
		spPlayerNum.setSelection(pref.getInt("playerNum", 0));
		spTiaopai.setSelection(pref.getInt("tiaopai", 0));
		cbType.setChecked(pref.getBoolean("isIdpai", false));
		asEast.setCurValue(pref.getInt("east", ProjectConstants.MAX_MAJIANG_NUM));
		asSouth.setCurValue(pref.getInt("south", ProjectConstants.MAX_MAJIANG_NUM));
		asWest.setCurValue(pref.getInt("west", ProjectConstants.MAX_MAJIANG_NUM));
		asNorth.setCurValue(pref.getInt("north", ProjectConstants.MAX_MAJIANG_NUM));
	}



	public static void myStartActivity(Context context) {
		Intent intent = new Intent(context, SettingActivity.class);
		context.startActivity(intent);
	}

	@Override
	protected void onBluetoothDataReceived(byte[] recvData) {
		switch (CalculateUtil.byteToInt(recvData[1])) {
			case 0x01:
				showToast("参数设置成功");

				SharedPreferences.Editor editor = pref.edit();

				editor.clear();
				editor.putInt("baopaizhangshu", spBaopaizhangshu.getSelectedItemPosition());
				editor.putInt("paishu", spPaishu.getSelectedItemPosition());
				editor.putInt("meishouzhangpai", spMeishouzhangpai.getSelectedItemPosition());
				editor.putInt("playerNum", spPlayerNum.getSelectedItemPosition());
				editor.putInt("tiaopai", spTiaopai.getSelectedItemPosition());
				editor.putBoolean("isIdpai", cbType.isChecked());
				editor.putInt("east", asEast.getCurVal());
				editor.putInt("south", asSouth.getCurVal());
				editor.putInt("west", asWest.getCurVal());
				editor.putInt("north", asNorth.getCurVal());
				editor.commit();

				break;
		}
	}
}
