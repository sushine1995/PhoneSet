package com.wzp.majiangset.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wzp.majiangset.R;

public class AddSubWidget extends LinearLayout implements View.OnClickListener, TextWatcher {

	private Context context;

	private ImageButton ibtnSub;
	private EditText edtNum;
	private ImageButton ibtnAdd;

	private int curVal; // 当前数
	private int maxValue = DEF_MAX_VALUE; // 最大值

	public static final int DEF_MAX_VALUE = 36; // 默认最大值
	public static final int MIN_VALUE = 0; // 最小值


	public AddSubWidget(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	public AddSubWidget(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		this.context = context;
		// 由于要调用inflate方法，因此必须令该控件继承于布局文件，才能给第二个参数赋值this，
		// 如果赋值为null，该自定义控件无法正常显示
		LayoutInflater.from(context).inflate(R.layout.widget_add_sub, this);

		ibtnSub = (ImageButton) findViewById(R.id.ibtn_sub);
		edtNum = (EditText) findViewById(R.id.edt_num);
		ibtnAdd = (ImageButton) findViewById(R.id.ibtn_add);

		ibtnSub.setOnClickListener(this);
		ibtnAdd.setOnClickListener(this);
		edtNum.addTextChangedListener(this);

		// 获取初始化参数值
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.addSubWidget);
		int initVal = typedArray.getInt(R.styleable.addSubWidget_initVal, DEF_MAX_VALUE);
		int maxVal = typedArray.getInt(R.styleable.addSubWidget_maxVal, DEF_MAX_VALUE);

		setMaxValue(maxVal);
		setCurValue(initVal);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.ibtn_sub:
				sub();
				break;

			case  R.id.ibtn_add:
				add();
				break;

			default:
				break;
		}
	}

	private void add() {
		if (curVal < maxValue) {
			curVal++;
			edtNum.setText(String.valueOf(curVal));
			edtNum.setSelection(edtNum.getText().toString().trim().length());
		}
	}

	private void sub() {
		if (curVal > MIN_VALUE) {
			curVal--;
			edtNum.setText(String.valueOf(curVal));
			edtNum.setSelection(edtNum.getText().toString().trim().length());
		}
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public void setCurValue(int val) {
		if (val >= MIN_VALUE && val <= maxValue) {
			curVal = val;
		} else if (val > maxValue) {
			curVal = maxValue;
		} else {
			curVal = MIN_VALUE;
		}
		edtNum.setText(String.valueOf(curVal));
	}

	public int getCurVal() {
		return curVal;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}

	@Override
	public void afterTextChanged(Editable s) {
		boolean isUpdateCurNum = false; // 是否要更新当前数

		if (!TextUtils.isEmpty(s)) {
			int value = Integer.valueOf(s.toString());
			if (value < MIN_VALUE) {
				value = MIN_VALUE;
				isUpdateCurNum = true;
				Toast.makeText(context, "不能少于" + MIN_VALUE, Toast.LENGTH_SHORT).show();
			} else if (value > maxValue) {
				value = maxValue;
				isUpdateCurNum = true;
				Toast.makeText(context, "不能超过" + maxValue, Toast.LENGTH_SHORT).show();
			}
			curVal = value; // 更新当前值
		} else {
			curVal = DEF_MAX_VALUE;
			Toast.makeText(context, "不能为空", Toast.LENGTH_SHORT).show();
		}

		if (isUpdateCurNum) {
			// 此处必须先移除TextChange监听器，才能修改EditText中的内容，否则会报异常
			edtNum.removeTextChangedListener(this);
			edtNum.setText(String.valueOf(curVal));
			edtNum.setSelection(edtNum.getEditableText().toString().length());
			edtNum.addTextChangedListener(this);
		}
	}
}
