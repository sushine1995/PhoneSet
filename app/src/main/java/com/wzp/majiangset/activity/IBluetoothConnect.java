package com.wzp.majiangset.activity;

public interface IBluetoothConnect {

	void showBtState(int state);
	
	void showToast(String info);
	
	void showToast(String info, int duration);
	
//	void onBluetoothDataReceived(byte[] recvData);
}
