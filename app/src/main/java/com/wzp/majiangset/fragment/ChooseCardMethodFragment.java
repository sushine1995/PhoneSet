package com.wzp.majiangset.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.EditChooseCardMethodActivity;
import com.wzp.majiangset.activity.EditPlayMethodActivity;
import com.wzp.majiangset.adapter.ChooseCardMethodListAdapter;
import com.wzp.majiangset.entity.ChooseCardMethod;
import com.wzp.majiangset.entity.ChooseCardParameter;

import java.util.List;

/**
 * 选牌方式
 */
public class ChooseCardMethodFragment extends Fragment {

    private EditPlayMethodActivity activity;

    private Button btnEdit;
    private ListView lvPlayMethod;

    private List<ChooseCardMethod> methodList;
    private ChooseCardMethodListAdapter chooseCardMethodListAdapter;

    private ChooseCardParameter chooseCardParameter;

    public static final int REQUEST_EDIT_CHOOSE_CARD_METHOD = 0x01;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (EditPlayMethodActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_majiang_method, container, false);

        initData();
        initWidget(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        chooseCardMethodListAdapter.notifyDataSetChanged();
    }

    private void initData() {
        chooseCardParameter = activity.getChooseCardParameter();
        methodList = chooseCardParameter.getMethods();
        chooseCardMethodListAdapter = new ChooseCardMethodListAdapter(getContext(), this, methodList);
    }


    private void initWidget(View view) {
        lvPlayMethod = (ListView) view.findViewById(R.id.lv_play_method);

        lvPlayMethod.setAdapter(chooseCardMethodListAdapter);

        Button btnAddData = (Button) LayoutInflater.from(getContext()).inflate(R.layout.view_add_data, null);
        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (methodList.size() >= 6) {
                    activity.showToast("最多只能包含6条数据！");
                } else {
                    EditChooseCardMethodActivity.myStartActivityForResult(getContext(),
                            activity.getPlayMethod(), chooseCardParameter.getMethods().size());
                }
            }
        });
        lvPlayMethod.addFooterView(btnAddData);
    }

}
