package com.wzp.majiangset.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BaseActivity;
import com.wzp.majiangset.adapter.ShowRemoteFileListAdapter;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.constant.RemoteFileSource;

/**
 * Created by wzp on 2017/9/23.
 */

public class ShowRemoteFileDialog extends Dialog {

    private Context context;

    private TextView tvSource;
    private ListView lvRemoteFile;

    private List<File> remoteFileList;
    private ShowRemoteFileListAdapter adapter;


    public ShowRemoteFileDialog(@NonNull Context context) {
        super(context);
        initWidget(context);
    }

    public ShowRemoteFileDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        initWidget(context);
    }

    protected ShowRemoteFileDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initWidget(context);
    }

    private void initWidget(Context context) {
        this.context = context;

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_show_remote_file, null);
        tvSource = (TextView) view.findViewById(R.id.tv_source);
        lvRemoteFile = (ListView) view.findViewById(R.id.lv_remoteFile);

        remoteFileList = new ArrayList<>();
        adapter = new ShowRemoteFileListAdapter(context, remoteFileList);
        lvRemoteFile.setAdapter(adapter);

        setContentView(view);
    }

    public void setOnItemClickListener(final AdapterView.OnItemClickListener listener) {
        lvRemoteFile.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                listener.onItemClick(parent, view, position, id);
            }
        });
    }

    public List<File> getAllRemoteFiles() {
        return remoteFileList;
    }

    public void show(int source) {
        File remote = null;
        if (source == RemoteFileSource.QQ) {
            remote = new File(ProjectConstants.QQ_FILE_PATH);
        } else if (source == RemoteFileSource.WECHAT) {
            remote = new File(ProjectConstants.WECHAT_FILE_PATH);
        } else {
            throw new IllegalArgumentException("source不合法");
        }

        if (!remote.exists()) {
            if (source == RemoteFileSource.QQ) {
                ((BaseActivity) context).showToast("您尚未安装QQ或QQ默认保存的文件夹被移除！");
            } else if (source == RemoteFileSource.WECHAT) {
                ((BaseActivity) context).showToast("您尚未安装微信或微信默认保存的文件夹被移除！");
            }
        } else {
            File[] files = remote.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".mj");
                }
            });
            if (files == null || files.length == 0) {
                ((BaseActivity) context).showToast("不存在符合条件的文件！");
            } else {
                remoteFileList.clear();
                remoteFileList.addAll(Arrays.asList(files));
                adapter.notifyDataSetChanged();

                if (source == RemoteFileSource.QQ) {
                    tvSource.setText("QQ");
                } else if (source == RemoteFileSource.WECHAT) {
                    tvSource.setText("微信");
                }

                show();
            }
        }
    }
}
