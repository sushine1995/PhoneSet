package com.wzp.majiangset.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.wzp.majiangset.R;

/**
 * Created by wzp on 2017/12/10.
 */

public class DailManualActivity extends AppCompatActivity {

    private ImageButton ibtnBack;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dail_manual);

        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, DailManualActivity.class);
        context.startActivity(intent);
    }
}
