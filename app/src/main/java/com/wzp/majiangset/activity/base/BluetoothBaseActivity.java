package com.wzp.majiangset.activity.base;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wzp.majiangset.util.CRC16;
import com.wzp.majiangset.util.CalculateUtil;
import com.wzp.majiangset.widget.MyApplication;

import static com.wzp.majiangset.constant.ProjectConstants.CRC_HIGH;
import static com.wzp.majiangset.constant.ProjectConstants.CRC_LOW;

public abstract class BluetoothBaseActivity extends CheckPermissionsActivity {

	protected ReadDataThread readDataThread;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();

		// 先清空消息队列
		MyApplication.getMessageQueue().clear();

		if (MyApplication.btClientHelper != null
				&& MyApplication.btClientHelper.isBluetoothConnected()) {
			readDataThread = new ReadDataThread();
			readDataThread.start();
		}
	}

	@Override
	protected void onPause() {
		// 关闭读数据线程
		if (readDataThread != null) {
			readDataThread.isStop = true;
			readDataThread = null;
		}

		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 发送报文
	 *
	 * @param sendData 报文
	 * @param isNeedCrc 是否需要对报文进行CRC校验；
	 * 					若为false，表示此报文已经包含CRC校验位，是一条完整的报文；
	 * 					若为true，表示此报文未包含CRC校验位，需要进行校验并添加校验结果；
	 */
	protected void sendMsg(byte[] sendData, boolean isNeedCrc) {
		if (null == sendData
				|| sendData.length < 2) {
			throw new IllegalArgumentException("sendData数组不能为空，且长度至少为2");
		}

		if (isNeedCrc) {
			byte[] crc = CRC16.getCrc16(sendData, sendData.length - 2);
			sendData[CRC_HIGH] = crc[0];
			sendData[CRC_LOW] = crc[1];
		}

		CalculateUtil.analyseMessage(sendData);
		if (MyApplication.btClientHelper != null) {
			// TODO 后期需要抛出并处理异常
			MyApplication.btClientHelper.write(sendData);
		}
	}

	/**
	 * 发送报文，默认需要进行CRC校验
	 *
	 * @param sendData 报文
	 */
	protected void sendMsg(byte[] sendData) {
		sendMsg(sendData, true);
	}

	/**
	 * 将麻将牌设为INVISIBLE
	 * @param ivMajiangArr
	 */
	protected void setMajiangInvisible(ImageView[] ivMajiangArr) {
		for (ImageView ivMajiang :
				ivMajiangArr) {
			ivMajiang.setVisibility(View.INVISIBLE);
		}
	}

	protected abstract void onBluetoothDataReceived(final byte[] recvData);

	protected class ReadDataThread extends Thread {

		public volatile boolean isStop = false;

		public ReadDataThread(String name) {
			super(name);
		}

		public ReadDataThread() {
			super(LOG_TAG + "_ReadDataThread");
		}

		@Override
		public void run() {
			while (!isStop) {
				if (MyApplication.getMessageQueue().size() > 0) {
					byte[] singleData = null;
					synchronized (MyApplication.getMessageQueue()) {
						if (MyApplication.getMessageQueue().size() > 0) {
							singleData = MyApplication.getMessageQueue().poll();
						}
					}
					if (singleData != null) {
						final byte[] recvData = singleData;
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								onBluetoothDataReceived(recvData);
							}
						});
					}
				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
