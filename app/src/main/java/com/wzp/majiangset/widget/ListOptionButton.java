package com.wzp.majiangset.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

import com.wzp.majiangset.R;

/**
 * Created by wzp on 2017/9/2.
 */

public class ListOptionButton extends android.support.v7.widget.AppCompatButton {

    private ListOptionPopupWindow pwListOption;
    private String[] options;
    private int selectedItemPosition;

    private AdapterView.OnItemClickListener listener;

    public ListOptionButton(Context context) {
        super(context);
        init(context);
    }

    public ListOptionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ListOptionButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context) {
        pwListOption = new ListOptionPopupWindow(context);

        pwListOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setText(options[position]);
                selectedItemPosition = position;
                pwListOption.dismiss();

                if (listener != null) {
                    listener.onItemClick(parent, view, position, id);
                }
            }
        });

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                pwListOption.showAsDropDown(ListOptionButton.this);
            }
        });
    }

    private void init(Context context, AttributeSet attrs) {
        init(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.listOptionButton);
        int optionsId = typedArray.getResourceId(R.styleable.listOptionButton_options, -1);

        if (optionsId != -1) {
            setListViewItems(optionsId);
        }

        int selectedIndex = typedArray.getInt(R.styleable.listOptionButton_selectedIndex, 0);
        setSelectedItemPosition(selectedIndex);
    }

    public void setListViewItems(String[] options) {
        this.options = options;
        pwListOption.setListViewItems(options);
        setText(options[0]);
    }

    public void setListViewItems(int optionsId) {
        setListViewItems(getResources().getStringArray(optionsId));
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
        if (options != null && selectedItemPosition < options.length) {
            setText(options[selectedItemPosition]);
        }
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public void setOnItemClickListener(final AdapterView.OnItemClickListener listener) {
        this.listener = listener;
    }
}
