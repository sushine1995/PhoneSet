package com.wzp.majiangset.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.entity.ChooseCardMethod;
import com.wzp.majiangset.entity.ChooseCardPlayMethod;
import com.wzp.majiangset.util.DensityUtil;

import java.util.List;


public class ShowChooseCardMethodListAdapter extends BaseAdapter {

    private Context context;
    private List<ChooseCardMethod> chooseCardMethodList;

    private String[] loopTimesArr;
    private String[] playMethodArr;
    private String[] basicNumArr;
    private String[] specialRuleArr;

    public ShowChooseCardMethodListAdapter(Context context, List<ChooseCardMethod> chooseCardMethodList) {
        this.context = context;
        this.chooseCardMethodList = chooseCardMethodList;

        loopTimesArr = context.getResources().getStringArray(R.array.loop_times_arr);
        playMethodArr = context.getResources().getStringArray(R.array.play_method_name_arr);
        basicNumArr = context.getResources().getStringArray(R.array.basic_num_arr);
        specialRuleArr = context.getResources().getStringArray(R.array.special_rule_arr);
    }

    @Override
    public int getCount() {
        return chooseCardMethodList.size();
    }

    @Override
    public Object getItem(int position) {
        return chooseCardMethodList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        ChooseCardMethod chooseCardMethod = (ChooseCardMethod) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_show_choose_card_method, parent, false);

            holder = new ViewHolder();
            holder.tvData = (TextView) convertView.findViewById(R.id.tv_data);
            holder.linearPlayMethodList = (LinearLayout) convertView.findViewById(R.id.linear_playMethodList);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvData.setText("数据" + (position + 1) + "(循环" + loopTimesArr[chooseCardMethod.getLoopTimes()] + "次)");
        listPlayMethod(context, holder.linearPlayMethodList, chooseCardMethod.getMethods());

        return convertView;
    }

    private void listPlayMethod(Context context, LinearLayout linearPlayMethodList, List<ChooseCardPlayMethod> methodList) {
        int childCount = linearPlayMethodList.getChildCount();
        for (int i = 0; i < childCount; i++) {
            linearPlayMethodList.removeViewAt(0);
        }

        for (ChooseCardPlayMethod method : methodList) {
            LinearLayout linearAdded = new LinearLayout(context);
            linearAdded.setOrientation(LinearLayout.HORIZONTAL);
            TextView tvName = new TextView(context);
            tvName.setTextSize(14);
            tvName.setText(playMethodArr[method.getName()]);
            LinearLayout.LayoutParams paramName = new LinearLayout.LayoutParams(0, (int) DensityUtil.dp2px(context, 30), 1);
            tvName.setLayoutParams(paramName);
            linearAdded.addView(tvName);


            LinearLayout.LayoutParams paramSeparatorLine = new LinearLayout.LayoutParams((int) DensityUtil.dp2px(context, 1), ViewGroup.LayoutParams.MATCH_PARENT);
            paramSeparatorLine.setMargins(0, (int) DensityUtil.dp2px(context, 5), 0, (int) DensityUtil.dp2px(context, 5));

            View separatorLine1 = new View(context);
            separatorLine1.setLayoutParams(paramSeparatorLine);
            separatorLine1.setBackgroundColor(context.getResources().getColor(R.color.white));
            linearAdded.addView(separatorLine1);

            TextView tvNum = new TextView(context);
            tvName.setTextSize(14);
            tvNum.setText("张数：" + basicNumArr[method.getNum()]);
            LinearLayout.LayoutParams paramNum = new LinearLayout.LayoutParams(0, (int) DensityUtil.dp2px(context, 30), 1);
            tvNum.setLayoutParams(paramNum);
            tvNum.setGravity(Gravity.CENTER);
            linearAdded.addView(tvNum);

            View separatorLine2 = new View(context);
            separatorLine2.setLayoutParams(paramSeparatorLine);
            separatorLine2.setBackgroundColor(context.getResources().getColor(R.color.white));
            linearAdded.addView(separatorLine2);

            TextView tvSpecialRule = new TextView(context);
            tvName.setTextSize(14);
            tvSpecialRule.setText(specialRuleArr[method.getSpecialRule()]);
            LinearLayout.LayoutParams paramSpecialRule = new LinearLayout.LayoutParams(0, (int) DensityUtil.dp2px(context, 30), 2);
            tvSpecialRule.setLayoutParams(paramSpecialRule);
            tvSpecialRule.setPadding((int) DensityUtil.dp2px(context, 20), 0, 0, 0);
            linearAdded.addView(tvSpecialRule);

            linearPlayMethodList.addView(linearAdded);
        }
    }

    class ViewHolder {
        TextView tvData;
        LinearLayout linearPlayMethodList;
    }

}
