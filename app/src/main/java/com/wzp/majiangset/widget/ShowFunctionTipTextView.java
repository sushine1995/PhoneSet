package com.wzp.majiangset.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wzp.majiangset.R;


public class ShowFunctionTipTextView extends android.support.v7.widget.AppCompatTextView {

    private ShowFunctionTipPopupWindow pwShowFunTip;


    public ShowFunctionTipTextView(Context context) {
        super(context);
        init(context);
    }

    public ShowFunctionTipTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShowFunctionTipTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context) {
        pwShowFunTip = new ShowFunctionTipPopupWindow(context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pwShowFunTip.showAsDropDown(ShowFunctionTipTextView.this);
            }
        });
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.showFunctionTipTextView);
        int funTipId = typedArray.getResourceId(R.styleable.showFunctionTipTextView_funTip, -1);

        pwShowFunTip = new ShowFunctionTipPopupWindow(context);
        if (funTipId != -1) {
            pwShowFunTip.setFunTip(funTipId);
        }

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pwShowFunTip.showAsDropDown(ShowFunctionTipTextView.this);
            }
        });
    }

    public void setFunTip(int funTipId) {
        pwShowFunTip.setFunTip(funTipId);
    }

    public void setFunTip(String funTip) {
        pwShowFunTip.setFunTip(funTip);
    }
}
