package com.wzp.majiangset.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BluetoothBaseActivity;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.util.CRC16;
import com.wzp.majiangset.util.CalculateUtil;
import com.wzp.majiangset.widget.ListOptionButton;
import com.wzp.majiangset.widget.MyApplication;

public class RecognizeDirectionActivity extends BluetoothBaseActivity {

	private ImageButton ibtnBack;

	private ListOptionButton btnEmptyMode;
	private TextView tvTip;
	private Button btnNorth;
	private Button btnSouth;
	private Button btnWest;
	private Button btnEast;

	private Vibrator vibrator;

	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.ibtn_back:
					onBackPressed();
					break;

				case R.id.btn_east:
					if (MyApplication.btClientHelper.isBluetoothConnected()) {
						byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
						sendMsg[0] = (byte) 0xfe;
						sendMsg[1] = (byte) 0xd1;
						sendMsg[2] = (byte) 0x01;
						sendMsg[3] = (byte) btnEmptyMode.getSelectedItemPosition();
						CalculateUtil.analyseMessage(sendMsg);
						byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
						sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
						sendMsg[ProjectConstants.CRC_LOW] = crc[1];

						MyApplication.btClientHelper.write(sendMsg);
					}
					break;

				case R.id.btn_south:
					if (MyApplication.btClientHelper.isBluetoothConnected()) {
						byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
						sendMsg[0] = (byte) 0xfe;
						sendMsg[1] = (byte) 0xd2;
						sendMsg[2] = (byte) 0x01;
						sendMsg[3] = (byte) btnEmptyMode.getSelectedItemPosition();
						CalculateUtil.analyseMessage(sendMsg);
						byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
						sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
						sendMsg[ProjectConstants.CRC_LOW] = crc[1];

						MyApplication.btClientHelper.write(sendMsg);
					}
					break;

				case R.id.btn_west:
					if (MyApplication.btClientHelper.isBluetoothConnected()) {
						byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
						sendMsg[0] = (byte) 0xfe;
						sendMsg[1] = (byte) 0xd3;
						sendMsg[2] = (byte) 0x01;
						sendMsg[3] = (byte) btnEmptyMode.getSelectedItemPosition();
						CalculateUtil.analyseMessage(sendMsg);
						byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
						sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
						sendMsg[ProjectConstants.CRC_LOW] = crc[1];

						MyApplication.btClientHelper.write(sendMsg);
					}
					break;

				case R.id.btn_north:
					if (MyApplication.btClientHelper.isBluetoothConnected()) {
						byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
						sendMsg[0] = (byte) 0xfe;
						sendMsg[1] = (byte) 0xd4;
						sendMsg[2] = (byte) 0x01;
						sendMsg[3] = (byte) btnEmptyMode.getSelectedItemPosition();
						CalculateUtil.analyseMessage(sendMsg);
						byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
						sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
						sendMsg[ProjectConstants.CRC_LOW] = crc[1];

						MyApplication.btClientHelper.write(sendMsg);
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
		setContentView(R.layout.activity_recognize_direction);

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

		if (vibrator != null) {
			vibrator.cancel();
		}
	}

	private void initParam() {
		vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
	}

	private void initWidget() {
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);

		btnEmptyMode = (ListOptionButton) findViewById(R.id.btn_emptyMode);
		tvTip = (TextView) findViewById(R.id.tv_tip);
		btnNorth = (Button) findViewById(R.id.btn_north);
		btnSouth = (Button) findViewById(R.id.btn_south);
		btnWest = (Button) findViewById(R.id.btn_west);
		btnEast = (Button) findViewById(R.id.btn_east);

		ibtnBack.setOnClickListener(listener);

		btnNorth.setOnClickListener(listener);
		btnSouth.setOnClickListener(listener);
		btnWest.setOnClickListener(listener);
		btnEast.setOnClickListener(listener);
	}

	/**
	 * 启动Activity
	 *
	 * @param context
	 */
	public static void myStartActivity(Context context) {
		Intent intent = new Intent(context, RecognizeDirectionActivity.class);
		context.startActivity(intent);
	}


	@Override
	protected void onBluetoothDataReceived(byte[] recvData) {
		final int direction = CalculateUtil.byteToInt(recvData[1]);
		if (direction >= 0xd1 && direction <= 0xd4 && CalculateUtil.byteToInt(recvData[2]) == 0x01) {
			int state = CalculateUtil.byteToInt(recvData[3]);
			final StringBuilder strTip = new StringBuilder();
			if (state == 0x00) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						strTip.append("正在识别");
						switch (direction) {
							case 0xd1:
								strTip.append("东方位...");
								break;

							case 0xd2:
								strTip.append("南方位...");
								break;

							case 0xd3:
								strTip.append("西方位...");
								break;

							case 0xd4:
								strTip.append("北方位...");
								break;

							default:
								break;
						}
						tvTip.setText(strTip);
					}
				});
			} else if (state == 0x01) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						switch (direction) {
							case 0xd1:
								strTip.append("东方位");
								btnEast.setTextColor(getResources().getColor(R.color.red));
								btnSouth.setTextColor(getResources().getColor(R.color.white));
								btnWest.setTextColor(getResources().getColor(R.color.white));
								btnNorth.setTextColor(getResources().getColor(R.color.white));
								break;

							case 0xd2:
								strTip.append("南方位");
								btnEast.setTextColor(getResources().getColor(R.color.white));
								btnSouth.setTextColor(getResources().getColor(R.color.red));
								btnWest.setTextColor(getResources().getColor(R.color.white));
								btnNorth.setTextColor(getResources().getColor(R.color.white));
								break;

							case 0xd3:
								strTip.append("西方位");
								btnEast.setTextColor(getResources().getColor(R.color.white));
								btnSouth.setTextColor(getResources().getColor(R.color.white));
								btnWest.setTextColor(getResources().getColor(R.color.red));
								btnNorth.setTextColor(getResources().getColor(R.color.white));
								break;

							case 0xd4:
								strTip.append("北方位");
								btnEast.setTextColor(getResources().getColor(R.color.white));
								btnSouth.setTextColor(getResources().getColor(R.color.white));
								btnWest.setTextColor(getResources().getColor(R.color.white));
								btnNorth.setTextColor(getResources().getColor(R.color.red));
								break;

							default:
								break;
						}
						strTip.append("识别成功!");
						tvTip.setText(strTip);
						vibrator.vibrate(200);
					}
				});
			}
		}
	}
}
