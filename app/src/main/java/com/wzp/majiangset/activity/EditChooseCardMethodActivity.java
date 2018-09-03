package com.wzp.majiangset.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.CheckPermissionsActivity;
import com.wzp.majiangset.adapter.ChooseCardPlayMethodListAdapter;
import com.wzp.majiangset.entity.ChooseCardMethod;
import com.wzp.majiangset.entity.ChooseCardPlayMethod;
import com.wzp.majiangset.widget.MyApplication;

import java.util.ArrayList;

import static com.wzp.majiangset.widget.MyApplication.getContext;


public class EditChooseCardMethodActivity extends CheckPermissionsActivity {

    private ImageButton ibtnBack;
    private TextView tvTitle;
    private ImageButton ibtnSave;
    private RecyclerView rvEditChooseCardPlayMethod;

    private AlertDialog dlgExit;

    private ChooseCardPlayMethodListAdapter chooseCardPlayMethodListAdapter;

    private ChooseCardMethod chooseCardMethod;

    private int playMethod; // 标识当前在修改哪一种玩法
    private int index; // 数据索引，表示当前修改的是哪一个数据


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_back:
                    onBackPressed();
                    break;

                case R.id.ibtn_save:
                    saveModification();
                    finish();
                    break;
            }
        }
    };

    @Override
    public void onBackPressed() {
        dlgExit.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_choose_card_method);

        initData();
        initWidget();
    }

    private void initData() {
        playMethod = getIntent().getIntExtra("playMethod", -1);
        index = getIntent().getIntExtra("index", -1);

        if (playMethod < 0 || playMethod > 2) {
            throw new IllegalArgumentException("playMethod只能在[0, 2]范围内");
        }
        if (index < 0) {
            throw new IllegalArgumentException("index不能小于0");
        }

        if (MyApplication.getParameterList().get(playMethod).getChooseCardParameter()
                .getMethods().size() <= index) {
            // chooseCardMethod集合包含元素的个数如果小于等于index，表示当前需要添加数据，初始化一个默认的ChooseCardMethod实例
            // 此处暂不需要添加进ChooseCardParameter中，待点击保存按钮后，再添加
            chooseCardMethod = new ChooseCardMethod();
            chooseCardMethod.setLoopTimes(0);
            chooseCardMethod.setSelected(true);
            chooseCardMethod.setMethods(new ArrayList<ChooseCardPlayMethod>());
        } else {
            try {
                chooseCardMethod = (ChooseCardMethod) MyApplication.getParameterList().get(playMethod)
                        .getChooseCardParameter().getMethods().get(index).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        chooseCardPlayMethodListAdapter = new ChooseCardPlayMethodListAdapter(this, chooseCardMethod);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ibtnSave = (ImageButton) findViewById(R.id.ibtn_save);
        rvEditChooseCardPlayMethod = (RecyclerView) findViewById(R.id.rv_editChooseCardPlayMethod);

        dlgExit = new AlertDialog.Builder(this)
                .setTitle("注意")
                .setMessage("是否保存数据？")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveModification();
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .create();


        rvEditChooseCardPlayMethod.setAdapter(chooseCardPlayMethodListAdapter);
        rvEditChooseCardPlayMethod.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));


        // 初始化控件值
        tvTitle.setText("数据" + (index + 1));

        // 设置监听器，注意：一定要放在初始化控件值的代码后面
        ibtnBack.setOnClickListener(listener);
        ibtnSave.setOnClickListener(listener);
    }

    public static void myStartActivityForResult(Context context, int playMethod, int index) {
        Intent intent = new Intent(context, EditChooseCardMethodActivity.class);
        intent.putExtra("playMethod", playMethod);
        intent.putExtra("index", index);
        context.startActivity(intent);
    }

    /**
     * 保存修改
     */
    private void saveModification() {
        showToast("保存成功");
        chooseCardMethod.setSelected(true);
        if (MyApplication.getParameterList().get(playMethod).getChooseCardParameter()
                .getMethods().size() <= index) {
            MyApplication.getParameterList().get(playMethod).getChooseCardParameter()
                    .getMethods().add(chooseCardMethod);
        } else {
            MyApplication.getParameterList().get(playMethod).getChooseCardParameter()
                    .getMethods().set(index, chooseCardMethod);
        }
    }
}
