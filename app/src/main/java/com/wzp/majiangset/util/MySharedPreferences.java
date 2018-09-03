package com.wzp.majiangset.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 自定义的SharedPreferences
 * 
 * @author WZP
 *
 */
public class MySharedPreferences {

	private SharedPreferences mSharedPreferences = null;

	
	public MySharedPreferences(Context context, String fileName) {
		mSharedPreferences = context.getSharedPreferences(fileName,
				Context.MODE_PRIVATE);
	}
	
	public SharedPreferences getSharedPreferences() {
		return mSharedPreferences;
	}

	public void removeKey(String key) {
		Editor editor = mSharedPreferences.edit();
		editor.remove(key);
		editor.commit();
		editor = null;
	}

	public void removeAll() {
		Editor editor = mSharedPreferences.edit();
		editor.clear();
		editor.commit();
		editor = null;
	}

	public void commitString(String key, String value) {
		Editor editor = mSharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
		editor = null;
	}

	public String getString(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	public void commitInt(String key, int value) {
		Editor editor = mSharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
		editor = null;
	}

	public int getInt(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}

	public void commitLong(String key, long value) {
		Editor editor = mSharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
		editor = null;
	}

	public long getLong(String key, long defaultValue) {
		return mSharedPreferences.getLong(key, defaultValue);
	}

	public void commitBoolean(String key, boolean value) {
		Editor editor = mSharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
		editor = null;
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}
	
	public void commitFloat(String key, float value) {
		Editor editor = mSharedPreferences.edit();
		editor.putFloat(key, value);
		editor.commit();
		editor = null;
	}

	public float getFloat(String key, float defaultValue) {
		return mSharedPreferences.getFloat(key, defaultValue);
	}
}
