package com.wzp.majiangset.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import com.wzp.majiangset.R;


public class ShowRemoteFileListAdapter extends BaseAdapter {

    private Context context;
    private List<File> dataList;


    public ShowRemoteFileListAdapter(Context context, List<File> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final File file = (File) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_show_remote_file, parent, false);

            holder = new ViewHolder();
            holder.tvFilename = (TextView) convertView.findViewById(R.id.tv_filename);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String filename = file.getName().substring(0, file.getName().lastIndexOf('.'));
        holder.tvFilename.setText(filename);

        return convertView;
    }

    class ViewHolder {
        TextView tvFilename;
    }

}
