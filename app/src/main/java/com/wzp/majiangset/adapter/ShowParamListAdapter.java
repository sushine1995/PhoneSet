package com.wzp.majiangset.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.wzp.majiangset.R;


public class ShowParamListAdapter extends BaseAdapter {

    private Context context;
    private List<String> datas;


    public ShowParamListAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_param, parent, false);

            holder = new ViewHolder();
            holder.tvParamName = (TextView) convertView.findViewById(R.id.tv_paramName);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvParamName.setText(datas.get(position));

        return convertView;
    }

    class ViewHolder {
        TextView tvParamName;
    }

}
