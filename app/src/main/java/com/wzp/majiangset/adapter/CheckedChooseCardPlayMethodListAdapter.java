package com.wzp.majiangset.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.entity.ChooseCardPlayMethod;

import java.util.List;

/**
 * 被选中的选牌方式下的玩法列表
 */
public class CheckedChooseCardPlayMethodListAdapter extends RecyclerView.Adapter<CheckedChooseCardPlayMethodListAdapter.MyViewHolder> {

    private Context mContext;
    private List<ChooseCardPlayMethod> datas;
    private OnItemClickLitener mOnItemClickLitener;
    private String[] playMethodNameArr;


    public CheckedChooseCardPlayMethodListAdapter(Context context, List<ChooseCardPlayMethod> datas) {
        mContext = context;
        this.datas = datas;
        playMethodNameArr = context.getResources().getStringArray(R.array.play_method_name_arr);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.listitem_checked_choose_card_play_method, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int i) {
        holder.tvName.setText(playMethodNameArr[datas.get(i).getName()]);
        holder.tvNum.setText("张数：" + mContext.getResources().getStringArray(R.array.basic_num_arr)[datas.get(i).getNum()]);
        holder.tvSpecialRule.setText(mContext.getResources().getStringArray(R.array.special_rule_arr)[datas.get(i).getSpecialRule()]);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvNum;
        TextView tvSpecialRule;

        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvNum = (TextView) view.findViewById(R.id.tv_num);
            tvSpecialRule = (TextView) view.findViewById(R.id.tv_specialRule);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
}
