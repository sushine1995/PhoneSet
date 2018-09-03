package com.wzp.majiangset.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wzp.majiangset.R;

import java.util.List;

public class BluetoothDeviceAdapter extends ArrayAdapter<BluetoothDevice> {
	
	private int resource;
	

	public BluetoothDeviceAdapter(Context context, int resource, List<BluetoothDevice> objects) {
		super(context, resource, objects);
		this.resource = resource;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		BluetoothDevice device = getItem(position);
		final SubViewHolder subViewHolder;
		
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
			
			subViewHolder = new SubViewHolder();
			subViewHolder.tvDevName = (TextView) convertView.findViewById(R.id.tv_devName);
			subViewHolder.tvDevMacAddr = (TextView) convertView.findViewById(R.id.tv_devMacAddr);
			
			convertView.setTag(subViewHolder);
		} else {
			subViewHolder = (SubViewHolder) convertView.getTag();
		}
		
		subViewHolder.tvDevName.setText(device.getName());
		if (!TextUtils.isEmpty(device.getName())) {
			subViewHolder.tvDevName.setText(device.getName());
		} else {
			subViewHolder.tvDevName.setText("未命名");
		}
		subViewHolder.tvDevMacAddr.setText(device.getAddress());
		
		return convertView;
	}
	
	
	/**
	 * 子控件
	 * @author Zippen
	 *
	 */
	private class SubViewHolder {
		TextView tvDevName;
		TextView tvDevMacAddr;
	}
}
