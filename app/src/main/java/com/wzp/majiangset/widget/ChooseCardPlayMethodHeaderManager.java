package com.wzp.majiangset.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.adapter.CheckedChooseCardPlayMethodListAdapter;
import com.wzp.majiangset.entity.ChooseCardMethod;
import com.wzp.majiangset.entity.ChooseCardPlayMethod;

import java.util.List;

/**
 * 选牌方式下的玩法列表的header管理器
 */
public class ChooseCardPlayMethodHeaderManager {

    private Context context;
    private ChooseCardMethod chooseCardMethod;

    private View view;
    private ListOptionButton btnLoopTimes;
    private RecyclerView rvPlayMethod;

    private List<ChooseCardPlayMethod> checkedDataList;
    private CheckedChooseCardPlayMethodListAdapter adapter;


    public ChooseCardPlayMethodHeaderManager(Context context, ViewGroup viewGroup, ChooseCardMethod chooseCardMethod) {
        init(context, viewGroup, chooseCardMethod);
    }

    private void init(Context context, ViewGroup viewGroup, ChooseCardMethod chooseCardMethod) {
        this.context = context;
        this.chooseCardMethod = chooseCardMethod;

        view = LayoutInflater.from(context).inflate(R.layout.header_choose_card_play_method,
                viewGroup, false);

        initData();
        initWidget(view);
    }

    private void initData() {
        checkedDataList = chooseCardMethod.getMethods();
        adapter = new CheckedChooseCardPlayMethodListAdapter(context, checkedDataList);
    }

    private void initWidget(View view) {
        btnLoopTimes = (ListOptionButton) view.findViewById(R.id.btn_loopTimes);
        rvPlayMethod = (RecyclerView) view.findViewById(R.id.rv_playMethod);

        rvPlayMethod.setAdapter(adapter);
        rvPlayMethod.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));

        btnLoopTimes.setSelectedItemPosition(chooseCardMethod.getLoopTimes());
        btnLoopTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChooseCardPlayMethodHeaderManager.this.chooseCardMethod.setLoopTimes(position);
            }
        });
    }

    public View getView() {
        return view;
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }
}
