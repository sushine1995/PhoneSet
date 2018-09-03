package com.wzp.majiangset.exception;

import android.content.Context;
import android.util.Log;

import com.wzp.majiangset.constant.ProjectConstants;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Date;

public class DefUncaughtExceptionHandler implements UncaughtExceptionHandler {

	private static final DefUncaughtExceptionHandler INSTANCE = new DefUncaughtExceptionHandler(); // 单例
	private Context context;
	
	private static final String LOG_TAG = "DefaultExceptionHandler";
	
	private DefUncaughtExceptionHandler() {}

	public synchronized static DefUncaughtExceptionHandler getInstance(){
        return INSTANCE;
    }

	public void init(Context context) { 
		// 初始化，把当前对象设置成UncaughtExceptionHandler处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
		this.context = context;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 当有未处理的异常发生时，就会来到这里。。
		Log.e(LOG_TAG, Log.getStackTraceString(ex));

		try {
			ex.printStackTrace(new PrintStream(ProjectConstants.UNCAUGHT_EXCEPTION_LOG_PATH + "/log_"
					+ ProjectConstants.SDF.format(new Date()) + ".exp"));
		} catch (FileNotFoundException e) {
			Log.e(LOG_TAG, Log.getStackTraceString(e));
		}
	}
}
