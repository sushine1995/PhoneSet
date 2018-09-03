package com.wzp.majiangset.activity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.adapter.BluetoothDeviceAdapter;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.widget.MyApplication;

import java.util.ArrayList;
import java.util.List;

public class DeviceListActivity extends Activity {

	private TextView tvTitle;
	private ProgressBar pbSearch;
	private View vSplitLine;
	private ListView lvNewDevice;
	private Button btnScan;

	private List<BluetoothDevice> newDeviceList = new ArrayList<>();

	private BluetoothDeviceAdapter newDeviceAdapter;
	
	private BluetoothAdapter mBtAdapter;
	
	private static final String TAG = "DeviceListActivity";

	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.d(TAG, action);

			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				// 搜索到一个蓝牙设备
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (!newDeviceList.contains(device)) {
					newDeviceList.add(device);
					newDeviceAdapter.notifyDataSetChanged();
				}
				vSplitLine.setVisibility(View.VISIBLE);
			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				// 蓝牙搜索结束(两种情况：1.调用了cancelDiscovery()方法；2.搜索蓝牙的时间到了)
				pbSearch.setVisibility(View.GONE);
				if (newDeviceList.size() == 0) {
					tvTitle.setText("未搜索到蓝牙设备");
				} else {
					tvTitle.setText("请选择蓝牙设备");
				}
				btnScan.setVisibility(View.VISIBLE);
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_list);

		/*
		设置搜索页面的宽度
		 */
		android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
		DisplayMetrics dm = getResources().getDisplayMetrics();
		p.width = (int) (dm.widthPixels * 0.75); // 宽度设置为屏幕的0.75
		getWindow().setAttributes(p);

		// 返回值默认为RESULT_CANCELED
		setResult(Activity.RESULT_CANCELED);

		initParam();
		initWidget();
		registerBluetoothSearchBroadcast();
		doDiscovery();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Make sure we're not doing discovery anymore
		if (mBtAdapter != null) {
			mBtAdapter.cancelDiscovery();
		}

		// Unregister broadcast listeners
		this.unregisterReceiver(mReceiver);
	}
	
	private void initParam() {
		mBtAdapter = BluetoothAdapter.getDefaultAdapter();

		newDeviceAdapter = new BluetoothDeviceAdapter(this,
				R.layout.listitem_device_info, newDeviceList);
	}
	
	private void initWidget() {
		tvTitle = (TextView) findViewById(R.id.tv_title);
		pbSearch = (ProgressBar) findViewById(R.id.pb_search);
		vSplitLine = findViewById(R.id.v_splitLine);
		lvNewDevice = (ListView) findViewById(R.id.lv_newDevice);
		btnScan = (Button) findViewById(R.id.btn_scan);
		
		lvNewDevice.setAdapter(newDeviceAdapter);
		lvNewDevice.setOnItemClickListener(new DeviceClickListener(newDeviceList));
		
		btnScan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				doDiscovery();
				v.setVisibility(View.GONE);
				tvTitle.setText("蓝牙搜索中...");
				pbSearch.setVisibility(View.VISIBLE);
			}
		});
	}
	
	/**
	 * 注册蓝牙搜索广播
	 */
	private void registerBluetoothSearchBroadcast() {
		// Register for broadcasts when a device is discovered and discovery has finished
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		this.registerReceiver(mReceiver, filter);
	}

	/**
	 * 搜索蓝牙设备
	 */
	private void doDiscovery() {
		Log.d(TAG, "doDiscovery()");
		
		// If we're already discovering, stop it
		if (mBtAdapter.isDiscovering()) {
			mBtAdapter.cancelDiscovery();
		}else if(mBtAdapter.getState()==BluetoothAdapter.STATE_ON)
		{
			Log.e(TAG,"is connecting");
			MyApplication.btClientHelper.stop();
			Log.e("DailActivity", " MyApplication.btClientHelper.stop();");
		}


		//TODO
		//mBtAdapter.
		//else if(mBtAdapter.startDiscovery()

		// Request discover from BluetoothAdapter
		mBtAdapter.startDiscovery();
	}
	
	private class DeviceClickListener implements OnItemClickListener {
		private List<BluetoothDevice> deviceList;
		
		public DeviceClickListener(List<BluetoothDevice> deviceList) {
			this.deviceList = deviceList;
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
			mBtAdapter.cancelDiscovery();

			String address = deviceList.get(position).getAddress();

			// Create the result Intent and include the MAC address
			Intent intent = new Intent();
			intent.putExtra(ProjectConstants.EXTRA_DEVICE_ADDRESS, address);

			// Set result and finish this Activity
			setResult(Activity.RESULT_OK, intent);
			finish();		
		}
	}
}
