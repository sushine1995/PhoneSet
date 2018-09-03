package com.wzp.majiangset.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.wzp.majiangset.activity.IBluetoothConnect;
import com.wzp.majiangset.constant.BluetoothState;
import com.wzp.majiangset.widget.MyApplication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

import static com.wzp.majiangset.constant.ProjectConstants.CRC_HIGH;
import static com.wzp.majiangset.constant.ProjectConstants.CRC_LOW;
import static com.wzp.majiangset.constant.ProjectConstants.DATA_LENGTH;

/**
 * 蓝牙客户端帮助类
 */
public class BluetoothClientHelper {
	// 和普通蓝牙设备通信，UUID必须设为该值
	private static final UUID MY_UUID = UUID
			.fromString("00001101-0000-1000-8000-00805F9B34FB");

	private BluetoothAdapter bluetoothAdapter;
	private IBluetoothConnect bluetoothConnect;

	private ConnectThread connectThread;
	private CommunicationThread communicationThread;

	private int curBtState; 	// 当前蓝牙状态
	private String remoteDevName; // 远程蓝牙设备名称

	private int reConnectTimes; // 重连次数
	
	private static final String TAG = "BluetoothClientHelper";

	
	public BluetoothClientHelper(IBluetoothConnect bluetoothConnect) {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		curBtState = BluetoothState.STATE_NONE;
		this.bluetoothConnect = bluetoothConnect;
	}

	public BluetoothClientHelper() {
		bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		curBtState = BluetoothState.STATE_NONE;
	}

	public void setBluetoothConnect(IBluetoothConnect bluetoothConnect) {
		this.bluetoothConnect = bluetoothConnect;
	}

	public IBluetoothConnect getBluetoothConnect() {
		return bluetoothConnect;
	}

	/**
	 * 设置蓝牙状态
	 * @param state
	 */
	private synchronized void setState(int state) {
		curBtState = state;
		bluetoothConnect.showBtState(state);
	}

	/**
	 * 蓝牙是否连接
	 *
	 * @return
	 */
	public boolean isBluetoothConnected() {
		boolean result = false;
		if (curBtState == BluetoothState.STATE_CONNECTED) {
			result = true;
		}
		return result;
	}

	/**
	 * 获取已经连上的远端蓝牙设备名称
	 *
	 * @return
	 */
	public synchronized String getRemoteDevName() {
		return remoteDevName;
	}

	/**
	 * 启动蓝牙连接线程
	 *
	 * @param device
	 */
	public synchronized void connect(BluetoothDevice device) {
		if (curBtState == BluetoothState.STATE_CONNECTING) {
			if (connectThread != null) {
				connectThread.cancel();
				connectThread = null;
			}
		}

		if (communicationThread != null) {
			communicationThread.cancel();
			communicationThread = null;
		}

		connectThread = new ConnectThread(device);
		connectThread.start();
		
		setState(BluetoothState.STATE_CONNECTING);
	}

	/**
	 * 启动通讯线程
	 *
	 * @param socket
	 * @param device
	 */
	public synchronized void communicate(BluetoothSocket socket, BluetoothDevice device) {
		if (connectThread != null) {
			connectThread.cancel();
			connectThread = null;
		}

		if (communicationThread != null) {
			communicationThread.cancel();
			communicationThread = null;
		}

		reConnectTimes = 0;

		communicationThread = new CommunicationThread(socket);
		communicationThread.start();
		
		bluetoothConnect.showToast("已连接：" + device.getName());

		setState(BluetoothState.STATE_CONNECTED);
	}

	/**
	 * 停用蓝牙客户端帮助类
	 */
	public synchronized void stop() {
		if (connectThread != null) {
			connectThread.cancel();
			connectThread = null;
		}

		if (communicationThread != null) {
			communicationThread.cancel();
			communicationThread = null;
		}
	}

	/**
	 * 发送数据
	 *
	 * @param out 字节数组
	 */
	public void write(byte[] out) {
		// Create temporary object
		CommunicationThread r;
		// Synchronize a copy of the ConnectedThread
		synchronized (this) {
			if (curBtState != BluetoothState.STATE_CONNECTED
					|| communicationThread == null) {
				return;
			}
			r = communicationThread;
		}
		// Perform the write unsynchronized
		r.write(out);
	}

	/**
	 * 连接失败（试图连接蓝牙设备，但是失败了）
	 */
	private void connectionFailed() {
		bluetoothConnect.showToast("无法连接至蓝牙设备");
		setState(BluetoothState.STATE_NONE);
	}

	/**
	 * 连接断开（之前已经连接成功，处在通信状态）
	 */
	private void connectionInterrupt() {
		bluetoothConnect.showToast("蓝牙连接断开！");
		setState(BluetoothState.STATE_NONE);
	}

	/**
	 * 打印异常信息，用于测试
	 *
	 * @param data
	 */
	protected void printExceptionData(byte[] data) {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			res.append(String.format("%02x", data[i]));
			res.append(" ");
		}
		Log.e(TAG, res.toString());
	}

	/**
	 * 断开重连
	 */
	private void reConnect(boolean reCount) {
		if (reCount) {
			reConnectTimes = 5;
		}

		if (reConnectTimes == 0) {
			return;
		}

		String macAddr = MyApplication.getSpSetting().getString("macAddress", null);
		if (macAddr != null) {
			BluetoothDevice device = bluetoothAdapter.getRemoteDevice(macAddr);
			connect(device);
		}
		reConnectTimes--;
	}
	
	/**
	 * 蓝牙连接线程
	 * 
	 * @author Zippen
	 */
	private class ConnectThread extends Thread {
		
		private final BluetoothSocket mmSocket;
		private final BluetoothDevice mmDevice;
		private boolean activeClose; // 是否主动关闭连接线程

		public ConnectThread(BluetoothDevice device) {
			mmDevice = device;
			
			BluetoothSocket tmp = null;
			try {
				tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
			} catch (IOException e) {
				// 需要添加异常处理

			}
			mmSocket = tmp;
		}

		public void run() {
			setName("ConnectThread");

			bluetoothAdapter.cancelDiscovery();

			try {
				mmSocket.connect();
			} catch (IOException e) {
				if (!activeClose) {
					// 如果不是主动关闭连接线程，就应该打印并提示异常信息
					Log.e(TAG, Log.getStackTraceString(e));
					connectionFailed();
					
					try {
						if (mmSocket != null) {
							mmSocket.close();						
						}
					} catch (IOException e2) {
						Log.e(TAG, "BluetoothSocket关闭异常");
						Log.e(TAG, Log.getStackTraceString(e));
					}

					reConnect(false);
				}

				return;
			}

			synchronized (BluetoothClientHelper.this) {
				connectThread = null;
			}

			// 蓝牙连接成功，获取远程蓝牙设备名称
			remoteDevName = mmDevice.getName();
			communicate(mmSocket, mmDevice);
		}

		public void cancel() {
			activeClose = true;

			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "BluetoothSocket关闭异常");
				Log.e(TAG, Log.getStackTraceString(e));
			}
		}
	}

	/**
	 * 蓝牙通信线程
	 * 
	 * @author Zippen
	 */
	private class CommunicationThread extends Thread {
		private final BluetoothSocket mmSocket;
		private final InputStream mmInStream;
		private final OutputStream mmOutStream;
		private volatile boolean activeClose; // 是否主动关闭
		
		// 每次蓝牙读取出来的数据
		private byte[] recvData = new byte[256];
		// 每次读取到的字节数
		private int len;
		// 缓冲区List，所有接收到的数据都存放在该缓冲区中
		private LinkedList<Byte> bufList = new LinkedList<>();

		public CommunicationThread(BluetoothSocket socket) {
			mmSocket = socket;
			InputStream tmpIn = null;
			OutputStream tmpOut = null;

			try {
				tmpIn = socket.getInputStream();
				tmpOut = socket.getOutputStream();
			} catch (IOException e) {
				Log.e(TAG, Log.getStackTraceString(e));
			}

			mmInStream = tmpIn;
			mmOutStream = tmpOut;
		}

		public void run() {
			setName("CommunicationThread");

			while (true) {
				try {
					len = mmInStream.read(recvData);

					if (len > 0) {
						// 打印接收到的数据
						StringBuilder res = new StringBuilder();
						res.append("接收到" + len + "个字节" +
								": ");
						for (int i = 0; i < len; i++) {
							res.append(String.format("%02x", recvData[i]));
							res.append(" ");
						}
						Log.d(TAG, res.toString());

						// 将每次从串口中读取到的数据都放入缓冲区bufList中
						for (int i = 0; i < len; i++) {
							bufList.add(recvData[i]);
						}

						Log.d(TAG, "缓冲区字节总数：" + bufList.size());

						/*
						 * 若缓冲区中的数据个数超过DATA_LENGTH，则取出缓冲区中前DATA_LENGTH个数据，
						 * 并将剩余的数据前移
						 */
						DealDataWhile:
						while (bufList.size() >= DATA_LENGTH) {
							// 取出前DATA_LENGTH个数据
							byte[] singleData = new byte[DATA_LENGTH] ;
							Iterator<Byte> iterator = bufList.iterator();
							for (int i = 0; i < DATA_LENGTH; i++) {
								singleData[i] = iterator.next();
							}

							/*
							 * 开始校验
							 */

							/*
							 * 检查报文头
							 */
							if (CalculateUtil.byteToInt(singleData[0]) != 0xfe) {
								Log.e(TAG, "报文头异常");
								printExceptionData(singleData);

								do {
									bufList.remove();		// 移除缓冲区中的第一个元素

								} while (bufList.size() > 0
										&& CalculateUtil.byteToInt(bufList.getFirst()) != 0xfe);

								// 若缓冲区中的元素个数不足一条报文的长度，则跳出循环，
								// 继续从串口读取数据
								if (bufList.size() < DATA_LENGTH) {
									break DealDataWhile;
								}

								// 若缓冲区中元素个数充足，取出fe开头的，DATA_LENGTH数量的
								// 字节作为一条报文，继续进行crc校验；
								Arrays.fill(singleData, (byte) 0);
								iterator = bufList.iterator();
								for (int i = 0; i < DATA_LENGTH; i++) {
									singleData[i] = iterator.next();
								}
							}

							/*
							 * CRC校验
							 *
							 * 不断进行CRC校验，直到出现如下两种情况才停止校验：
							 * 1、bufList中元素个数少于DATA_LENGTH，跳出循环，继续从串口读数据；
							 * 2、找到一条完整的报文，并且该报文通过报文头校验、CRC校验，触发
							 * onDataReceived()方法，对报文进行处理；
							 */
							while (true) {
								byte[] crc = CRC16.getCrc16(singleData, singleData.length - 2);

								if (singleData[CRC_HIGH] != crc[0]
										|| singleData[CRC_LOW] != crc[1]) {
									Log.e(TAG, "CRC校验失败");
									printExceptionData(singleData);

									do {
										bufList.remove();		// 移除缓冲区中的第一个元素
									} while (bufList.size() > 0
											&& CalculateUtil.byteToInt(bufList.getFirst()) != 0xfe);

									// 若缓冲区中的元素个数不足一条报文的长度，则跳出循环，
									// 继续从串口读取数据
									if (bufList.size() < DATA_LENGTH) {
										break DealDataWhile;
									}

									// 若缓冲区中元素个数充足，取出fe开头的，DATA_LENGTH数量的
									// 字节作为一条报文，进行crc校验；
									Arrays.fill(singleData, (byte) 0);
									iterator = bufList.iterator();
									for (int i = 0; i < DATA_LENGTH; i++) {
										singleData[i] = iterator.next();
									}
								} else {
									// 打印一条完整的报文
									res.delete(0, res.length());
									res.append("有效报文：" );
									for (int i = 0; i < singleData.length; i++) {
										res.append(String.format("%02x", singleData[i]));
										res.append(" ");
									}
									Log.i(TAG, res.toString());

									synchronized (MyApplication.getMessageQueue()) {
										CalculateUtil.analyseMessage(singleData);
										MyApplication.getMessageQueue().add(singleData);
									}

									break;
								}
							}
							/*
							 * 结束校验
							 */

							for (int i = 0; i < DATA_LENGTH; i++) {
								bufList.remove();
							}
						}
					}
				} catch (IOException e) {
					if (!activeClose) {
						// 如果不是主动关闭通讯线程，就应该打印并提示异常信息
						Log.e(TAG, Log.getStackTraceString(e));
						connectionInterrupt(); // 蓝牙连接断开

						if (mmSocket != null) {
							try {
								mmSocket.close();
							} catch (IOException e2) {
								Log.e(TAG, "BluetoothSocket关闭异常");
								Log.e(TAG, Log.getStackTraceString(e));
							}
						}

						// 重连
						reConnect(true);
					} else {
						setState(BluetoothState.STATE_NONE);
					}

					break;
				}
			}
		}

		public void write(byte[] buffer) {
			try {
				mmOutStream.write(buffer);
			} catch (IOException e) {
				Log.e(TAG, "蓝牙发送数据失败", e);
			}
		}

		public void cancel() {
			activeClose = true;
			
			try {
				mmSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "BluetoothSocket关闭失败", e);
			}
		}
	}
}
