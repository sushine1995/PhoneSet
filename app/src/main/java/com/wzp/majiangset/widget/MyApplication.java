package com.wzp.majiangset.widget;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.wzp.majiangset.entity.PlayMethodParameter;
import com.wzp.majiangset.exception.DefUncaughtExceptionHandler;
import com.wzp.majiangset.util.BluetoothClientHelper;
import com.wzp.majiangset.util.MySharedPreferences;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 提供一些全局的Application属性
 * @author Zippen
 *
 */
public class MyApplication extends Application {

	private static Context context;
	public static BluetoothClientHelper btClientHelper;

	// 报文队列
	private static Queue<byte[]> messageQueue = new LinkedList<>();

	private static MySharedPreferences spPlayMethod;
	private static MySharedPreferences spSetting;

	public static List<PlayMethodParameter> parameterList = new ArrayList<>();
	
	private static final String LOG_TAG = "MyApplication";
	

	@Override
	public void onCreate() {
		Log.i(LOG_TAG, "MyApplication --> onCreate");
		
		context = getApplicationContext();

		spPlayMethod = new MySharedPreferences(context, "play_method_prefs");
		spSetting = new MySharedPreferences(context, "setting_prefs");

		// 程序未捕获的异常统一在DefUncaughtExceptionHandler中处理
		DefUncaughtExceptionHandler.getInstance().init(this);
	}

	public static Context getContext() {
		return context;
	}

	public static MySharedPreferences getSpPlayMethod() {
		return spPlayMethod;
	}

	public static MySharedPreferences getSpSetting() {
		return spSetting;
	}

	public static List<PlayMethodParameter> getParameterList() {
		return parameterList;
	}

	public static Queue<byte[]> getMessageQueue() {
		return messageQueue;
	}
}
