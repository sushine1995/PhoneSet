package com.wzp.majiangset.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BaseActivity;
import com.wzp.majiangset.entity.ChooseCardMethod;
import com.wzp.majiangset.entity.ChooseCardPlayMethod;
import com.wzp.majiangset.widget.ChooseCardPlayMethodHeaderManager;
import com.wzp.majiangset.widget.ListOptionButton;

import java.util.List;

/**
 * 选牌方式下的玩法列表
 */
public class ChooseCardPlayMethodListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ChooseCardMethod chooseCardMethod;
    private List<ChooseCardPlayMethod> checkedDataList;
    private String[] playMethodNameArr;

    private ChooseCardPlayMethodHeaderManager headerManager;

    private static final int MAX_NUM = 6; // 选牌玩法的最大数量

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;


    public ChooseCardPlayMethodListAdapter(Context context, ChooseCardMethod chooseCardMethod) {
        this.context = context;
        this.chooseCardMethod = chooseCardMethod;

        checkedDataList = chooseCardMethod.getMethods();
        playMethodNameArr = context.getResources().getStringArray(R.array.play_method_name_arr);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            if (headerManager == null) {
                headerManager = new ChooseCardPlayMethodHeaderManager(context, viewGroup, chooseCardMethod);
            }
            return new HeaderViewHolder(headerManager.getView());
        } else if (viewType == TYPE_NORMAL) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.listitem_choose_card_play_method, viewGroup, false));
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int i) {
        if(getItemViewType(i) == TYPE_NORMAL){
            final MyViewHolder myViewHolder = (MyViewHolder) holder;
            final int playMethodIndex =  i - 1;

            /*
            先清空监听器，避免item复用造成的异常
             */
            myViewHolder.cbName.setOnCheckedChangeListener(null);
            myViewHolder.btnNum.setOnItemClickListener(null);
            myViewHolder.btnSpecialRule.setOnItemClickListener(null);

            /*
            为item中的元素设置初值
             */
            myViewHolder.cbName.setText(playMethodNameArr[playMethodIndex]);
            int checkedIndex = indexOfCheckedList(playMethodIndex);
            if (checkedIndex != -1) {
                myViewHolder.cbName.setChecked(true);
                myViewHolder.btnNum.setSelectedItemPosition(checkedDataList.get(checkedIndex).getNum());
                myViewHolder.btnSpecialRule.setSelectedItemPosition(checkedDataList.get(checkedIndex).getSpecialRule());
            } else {
                myViewHolder.cbName.setChecked(false);
                myViewHolder.btnNum.setSelectedItemPosition(10); // 基本张数默认10张，index为10
                myViewHolder.btnSpecialRule.setSelectedItemPosition(0); // 特殊规则默认无，index为0
            }

            /*
            重新设置监听器
             */
            myViewHolder.cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ChooseCardPlayMethod method = new ChooseCardPlayMethod();
                    method.setName(playMethodIndex);
                    method.setNum(myViewHolder.btnNum.getSelectedItemPosition());
                    method.setSpecialRule(myViewHolder.btnSpecialRule.getSelectedItemPosition());

                    if (isChecked) {
                        if (checkedDataList.contains(method)) {
                            return;
                        }
                        if (checkedDataList.size() < MAX_NUM) {
                            checkedDataList.add(method);
                        } else {
                            ((BaseActivity) context).showToast("最多只能添加6条数据！");
                            myViewHolder.cbName.setChecked(!isChecked);
                            return;
                        }
                    } else {
                        checkedDataList.remove(method);
                    }

                    headerManager.notifyDataSetChanged();
                }
            });
            myViewHolder.btnNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (myViewHolder.cbName.isChecked()) {
                        int checkedIndex = indexOfCheckedList(playMethodIndex);
                        checkedDataList.get(checkedIndex)
                                .setNum(myViewHolder.btnNum.getSelectedItemPosition());

                        headerManager.notifyDataSetChanged();
                    }
                }
            });
            myViewHolder.btnSpecialRule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (myViewHolder.cbName.isChecked()) {
                        int checkedIndex = indexOfCheckedList(playMethodIndex);
                        checkedDataList.get(checkedIndex)
                                .setSpecialRule(myViewHolder.btnSpecialRule.getSelectedItemPosition());

                        headerManager.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return playMethodNameArr.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbName;
        ListOptionButton btnNum;
        ListOptionButton btnSpecialRule;

        public MyViewHolder(View view) {
            super(view);
            cbName = (CheckBox) view.findViewById(R.id.cb_name);
            btnNum = (ListOptionButton) view.findViewById(R.id.btn_num);
            btnSpecialRule = (ListOptionButton) view.findViewById(R.id.btn_specialRule);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }

    private int indexOfCheckedList(int index) {
        ChooseCardPlayMethod chooseCardPlayMethod = new ChooseCardPlayMethod();
        chooseCardPlayMethod.setName(index);
        return checkedDataList.indexOf(chooseCardPlayMethod);
    }
}
