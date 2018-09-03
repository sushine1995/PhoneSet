package com.wzp.majiangset.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.CheckPermissionsActivity;
import com.wzp.majiangset.adapter.ShowFileListAdapter;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.constant.RemoteFileSource;
import com.wzp.majiangset.util.DensityUtil;
import com.wzp.majiangset.widget.ShowRemoteFileDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wzp on 2017/9/21.
 */

public class ReceiveSendFileActivity extends CheckPermissionsActivity {

    private ImageButton ibtnBack;
    private Button btnRecvSendMethod;
    private TextView tvReadRemoteFile;

    private ShowRemoteFileDialog dlgShowRemoteFile;

    private PopupWindow pwRecvSendMethod;
    private View vRecvSendMethod;
    private TextView tvQQ;
    private TextView tvWeChat;

    private ListView lvFile;

    private int remoteSource = RemoteFileSource.WECHAT;

    private List<File> fileList;
    private ShowFileListAdapter showFileListAdapter;



    public int getRemoteSource() {
        return remoteSource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_send_file);

        initData();
        initWidget();
    }

    private void initData() {
        File file = new File(ProjectConstants.BASE_FILE_PATH);
        File[] files = file.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mj");
            }
        });
        fileList = new ArrayList<>();
        fileList.addAll(Arrays.asList(files));

        showFileListAdapter = new ShowFileListAdapter(this, fileList);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        btnRecvSendMethod = (Button) findViewById(R.id.btn_recvSendMethod);
        tvReadRemoteFile = (TextView) findViewById(R.id.tv_readRemoteFile);
        tvReadRemoteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgShowRemoteFile.show(remoteSource);
            }
        });
        lvFile = (ListView) findViewById(R.id.lv_file);

        dlgShowRemoteFile = new ShowRemoteFileDialog(this);
        dlgShowRemoteFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File src = dlgShowRemoteFile.getAllRemoteFiles().get(position);
                copyFile(src);
            }
        });

        vRecvSendMethod = getLayoutInflater().inflate(R.layout.pop_win_recv_send_method, null);
        tvQQ = (TextView) vRecvSendMethod.findViewById(R.id.tv_qq);
        tvWeChat = (TextView) vRecvSendMethod.findViewById(R.id.tv_weChat);
        tvQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwRecvSendMethod.dismiss();
                btnRecvSendMethod.setText("QQ");
                remoteSource = RemoteFileSource.QQ;
            }
        });
        tvWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwRecvSendMethod.dismiss();
                btnRecvSendMethod.setText("微信");
                remoteSource = RemoteFileSource.WECHAT;
            }
        });

        pwRecvSendMethod = new PopupWindow(vRecvSendMethod, (int) DensityUtil.dp2px(this, 60), LinearLayout.LayoutParams.WRAP_CONTENT);
        pwRecvSendMethod.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.popupwindow_bg)));
        pwRecvSendMethod.setFocusable(true);

        lvFile.setAdapter(showFileListAdapter);


        ibtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnRecvSendMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwRecvSendMethod.showAsDropDown(v, 0,
                        DensityUtil.dp2px(ReceiveSendFileActivity.this, 1));
            }
        });
    }

    private void copyFile(File src) {
        File dest = new File(ProjectConstants.BASE_FILE_PATH);
        if (Arrays.asList(dest.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mj");
            }
        })).contains(src.getName())) {
            // 远程文件和本地文件重名
            showToast("本地目录中包含同名文件，请先删除后重试");
        } else {
            // 远程文件和本地文件不重名，直接将文件从远程复制到本地
            FileInputStream fis = null;
            FileOutputStream fos = null;
            byte[] content = new byte[512];
            int readByteNum = 0;

            try {
                fis = new FileInputStream(src);
                fos = new FileOutputStream(dest.getAbsolutePath() + "/" + src.getName());

                while ((readByteNum = fis.read(content)) > 0) {
                    fos.write(content, 0, readByteNum);
                }

                fileList.clear();
                fileList.addAll(Arrays.asList(dest.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.endsWith(".mj");
                    }
                })));
                showFileListAdapter.notifyDataSetChanged();
            } catch (FileNotFoundException e) {
                Log.e(LOG_TAG, Log.getStackTraceString(e));
                showToast("文件夹不存在！");
            } catch (IOException e) {
                Log.e(LOG_TAG, Log.getStackTraceString(e));
                showToast("文件操作失败！");
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    Log.e(LOG_TAG, Log.getStackTraceString(e));
                }
            }
        }
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, ReceiveSendFileActivity.class);
        context.startActivity(intent);
    }

    public static void myStartActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ReceiveSendFileActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}
