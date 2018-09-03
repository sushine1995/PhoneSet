package com.wzp.majiangset.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wzp.majiangset.R;


public class ShowFunctionTipImageView extends android.support.v7.widget.AppCompatImageView {

    private ShowFunctionTipPopupWindow pwShowFunTip;


    public ShowFunctionTipImageView(Context context) {
        super(context);
        init(context);
    }

    public ShowFunctionTipImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ShowFunctionTipImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context) {
        pwShowFunTip = new ShowFunctionTipPopupWindow(context);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pwShowFunTip.showAsDropDown((View) (ShowFunctionTipImageView.this.getParent()), 0, 0);
            }
        });
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.showFunctionTipTextView);
        int funTipId = typedArray.getResourceId(R.styleable.showFunctionTipTextView_funTip, -1);

        if (funTipId != -1) {
            pwShowFunTip.setFunTip(funTipId);
        }
    }

    public void setFunTip(int funTipId) {
        pwShowFunTip.setFunTip(funTipId);
    }

    public void setFunTip(String funTip) {
        pwShowFunTip.setFunTip(funTip);
    }
}
