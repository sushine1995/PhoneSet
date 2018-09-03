package com.wzp.majiangset.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.EditPlayMethodActivity;
import com.wzp.majiangset.entity.DiceParameter;
import com.wzp.majiangset.widget.ListOptionButton;

/**
 * Created by wzp on 2017/8/29.
 */

public class SetDiceFragment extends Fragment {

    private EditPlayMethodActivity activity;

    private ListOptionButton btnDiceNum;
    private ListOptionButton btnUseDiceTimes;
    private ListOptionButton btnUseDiceMethod;
    private ListOptionButton btnStartCardMethod;
    private ListOptionButton btnStartCardSupplementFlowerMethod;
    private ListOptionButton btnStartCardReserve1;
    private ListOptionButton btnStartCardReserve2;
    private CheckBox cbOneFiveNineGetCard;
    private CheckBox cbEastSouthWestNorthAsColorCard;
    private CheckBox cbZhongFaBaiAsColorCard;
    private CheckBox cbAllWindCardAsColorCard;
    private CheckBox cbBankerAndLastPlayerChangePosition;
    private CheckBox cbBaidaAsFlowerCard;
    private CheckBox cbDabaipiAsFlowerCard;
    private RadioGroup rgWealthGodMode;
    private LinearLayout linearWealthGodMode;
    private ListOptionButton btnWealthGodStartCardMethod;
    private ListOptionButton btnWealthGodUseDiceMethod;
    private ListOptionButton btnWealthGodCondition;
    private ListOptionButton btnWindCardWealthGodLoopMethod;
    private ListOptionButton btnFixedWealthGod;
    private ListOptionButton btnWealthGodLastBlockNum;
    private ListOptionButton btnWealthGodStartCardPosition;
    private ListOptionButton btnWealthGodPrecedenceNum;
    private ListOptionButton btnWealthGodReserve;
    private CheckBox cbZhongAsFixedWealthGod;
    private CheckBox cbColorCardAsFixedWealthGod;
    private CheckBox cbYiTiaoAsFixedWealthGod;
    private CheckBox cbBaiBanAsFixedWealthGod;
    private CheckBox cbYaojiufeng;
    private CheckBox cbYaojiufengSuanGan;
    private CheckBox cbBaibanAsWealthGodSubstitute;
    private CheckBox cbFanpaifengpaiAsWealthGod;
    private CheckBox cb13579;
    private CheckBox cbEastSouthWestNorthOrZhongFaBaiBusuandacha;
    private CheckBox cbWealthGodIsEastWind;
    private CheckBox cbRedFlowerAsFixedWealthGod;
    private CheckBox cbBlackFlowerAsFixedWealthGod;
    private CheckBox cbBaidaAsFixedWealthGod;
    private CheckBox cbDabaipiAsFixedWealthGod;
    private CheckBox cbGetZhongFaBaiWealthGodAsEastWind;

    private DiceParameter diceParameter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (EditPlayMethodActivity) context;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_dice, container, false);

        initData();
        initWidget(view);

        return view;
    }

    private void initData() {
        diceParameter = activity.getDiceParameter();
    }

    private void initWidget(View view) {
        btnDiceNum = (ListOptionButton) view.findViewById(R.id.btn_diceNum);
        btnUseDiceTimes = (ListOptionButton) view.findViewById(R.id.btn_useDiceTimes);
        btnUseDiceMethod = (ListOptionButton) view.findViewById(R.id.btn_useDiceMethod);
        btnStartCardMethod = (ListOptionButton) view.findViewById(R.id.btn_startCardMethod);
        btnStartCardSupplementFlowerMethod = (ListOptionButton) view.findViewById(R.id.btn_startCardSupplementFlowerMethod);
        btnStartCardReserve1 = (ListOptionButton) view.findViewById(R.id.btn_startCardReserve1);
        btnStartCardReserve2 = (ListOptionButton) view.findViewById(R.id.btn_startCardReserve2);
        cbOneFiveNineGetCard = (CheckBox) view.findViewById(R.id.cb_oneFiveNineGetCard);
        cbEastSouthWestNorthAsColorCard = (CheckBox) view.findViewById(R.id.cb_eastSouthWestNorthAsColorCard);
        cbZhongFaBaiAsColorCard = (CheckBox) view.findViewById(R.id.cb_zhongFaBaiAsColorCard);
        cbAllWindCardAsColorCard = (CheckBox) view.findViewById(R.id.cb_allWindCardAsColorCard);
        cbBankerAndLastPlayerChangePosition = (CheckBox) view.findViewById(R.id.cb_bankerAndLastPlayerChangePosition);
        cbBaidaAsFlowerCard = (CheckBox) view.findViewById(R.id.cb_baidaAsFlowerCard);
        cbDabaipiAsFlowerCard = (CheckBox) view.findViewById(R.id.cb_dabaipiAsFlowerCard);
        rgWealthGodMode = (RadioGroup) view.findViewById(R.id.rg_wealthGodMode);
        linearWealthGodMode = (LinearLayout) view.findViewById(R.id.linear_wealthGodMode);
        btnWealthGodStartCardMethod = (ListOptionButton) view.findViewById(R.id.btn_wealthGodStartCardMethod);
        btnWealthGodUseDiceMethod = (ListOptionButton) view.findViewById(R.id.btn_wealthGodUseDiceMethod);
        btnWealthGodCondition = (ListOptionButton) view.findViewById(R.id.btn_wealthGodCondition);
        btnWindCardWealthGodLoopMethod = (ListOptionButton) view.findViewById(R.id.btn_windCardWealthGodLoopMethod);
        btnFixedWealthGod = (ListOptionButton) view.findViewById(R.id.btn_fixedWealthGod);
        btnWealthGodLastBlockNum = (ListOptionButton) view.findViewById(R.id.btn_wealthGodLastBlockNum);
        btnWealthGodStartCardPosition = (ListOptionButton) view.findViewById(R.id.btn_wealthGodStartCardPosition);
        btnWealthGodPrecedenceNum = (ListOptionButton) view.findViewById(R.id.btn_wealthGodPrecedenceNum);
        btnWealthGodReserve = (ListOptionButton) view.findViewById(R.id.btn_wealthGodReserve);
        cbZhongAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_zhongAsFixedWealthGod);
        cbColorCardAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_colorCardAsFixedWealthGod);
        cbYiTiaoAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_yiTiaoAsFixedWealthGod);
        cbBaiBanAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_baiBanAsFixedWealthGod);
        cbYaojiufeng = (CheckBox) view.findViewById(R.id.cb_yaojiufeng);
        cbYaojiufengSuanGan = (CheckBox) view.findViewById(R.id.cb_yaojiufengSuanGan);
        cbBaibanAsWealthGodSubstitute = (CheckBox) view.findViewById(R.id.cb_baibanAsWealthGodSubstitute);
        cbFanpaifengpaiAsWealthGod = (CheckBox) view.findViewById(R.id.cb_fanpaifengpaiAsWealthGod);
        cb13579 = (CheckBox) view.findViewById(R.id.cb_13579);
        cbEastSouthWestNorthOrZhongFaBaiBusuandacha = (CheckBox) view.findViewById(R.id.cb_eastSouthWestNorthOrZhongFaBaiBusuandacha);
        cbWealthGodIsEastWind = (CheckBox) view.findViewById(R.id.cb_wealthGodIsEastWind);
        cbRedFlowerAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_redFlowerAsFixedWealthGod);
        cbBlackFlowerAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_blackFlowerAsFixedWealthGod);
        cbBaidaAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_baiDaAsFixedWealthGod);
        cbBaidaAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_baiDaAsFixedWealthGod);
        cbDabaipiAsFixedWealthGod = (CheckBox) view.findViewById(R.id.cb_daBaiPiAsFixedWealthGod);
        cbGetZhongFaBaiWealthGodAsEastWind = (CheckBox) view.findViewById(R.id.cb_getZhongFaBaiWealthGodAsEastWind);


        btnDiceNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setDiceNum(position);
            }
        });
        btnUseDiceTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setUseDiceTimes(position);
            }
        });
        btnUseDiceMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setUseDiceMethod(position);
            }
        });
        btnStartCardMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setStartCardMethod(position);
            }
        });
        btnStartCardSupplementFlowerMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setStartCardSupplementFlowerMethod(position);
            }
        });
        btnStartCardReserve1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setStartCardReserve1(position);
            }
        });
        btnStartCardReserve2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setStartCardReserve2(position);
            }
        });
        cbOneFiveNineGetCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setOneFiveNineGetCard(isChecked);
            }
        });
        cbEastSouthWestNorthAsColorCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setEastSouthWestNorthAsColorCard(isChecked);
            }
        });
        cbZhongFaBaiAsColorCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setZhongFaBaiAsColorCard(isChecked);
            }
        });
        cbAllWindCardAsColorCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setAllWindCardAsColorCard(isChecked);
            }
        });
        cbBankerAndLastPlayerChangePosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setBankerAndLastPlayerChangePosition(isChecked);
            }
        });
        cbBaidaAsFlowerCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setBaidaAsFlowerCard(isChecked);
            }
        });
        cbDabaipiAsFlowerCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setDabaipiAsFlowerCard(isChecked);
            }
        });
        rgWealthGodMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_open) {
                    linearWealthGodMode.setVisibility(View.VISIBLE);
                    diceParameter.setOpenWealthGodMode(true);
                } else {
                    linearWealthGodMode.setVisibility(View.GONE);
                    diceParameter.setOpenWealthGodMode(false);
                }
            }
        });
        btnWealthGodStartCardMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWealthGodStartCardMethod(position);
            }
        });
        btnWealthGodUseDiceMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWealthGodUseDiceMethod(position);
            }
        });
        btnWealthGodCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWealthGodCondition(position);
            }
        });
        btnWindCardWealthGodLoopMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWindCardWealthGodLoopMethod(position);
            }
        });
        btnFixedWealthGod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setFixedWealthGod(position);
            }
        });
        btnWealthGodLastBlockNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWealthGodLastBlockNum(position);
            }
        });
        btnWealthGodStartCardPosition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWealthGodStartCardPosition(position);
            }
        });
        btnWealthGodPrecedenceNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWealthGodPrecedenceNum(position);
            }
        });
        btnWealthGodReserve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                diceParameter.setWealthGodReserve(position);
            }
        });
        cbZhongAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setZhongAsFixedWealthGod(isChecked);
            }
        });
        cbColorCardAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setColorCardAsFixedWealthGod(isChecked);
            }
        });
        cbYiTiaoAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setYiTiaoAsFixedWealthGod(isChecked);
            }
        });
        cbBaiBanAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setBaiBanAsFixedWealthGod(isChecked);
            }
        });
        cbYaojiufeng.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setYaojiufeng(isChecked);
            }
        });
        cbYaojiufengSuanGan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setYaojiufengSuanGan(isChecked);
            }
        });
        cbBaibanAsWealthGodSubstitute.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setBaibanAsWealthGodSubstitute(isChecked);
            }
        });
        cbFanpaifengpaiAsWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setFanpaifengpaiAsWealthGod(isChecked);
            }
        });
        cb13579.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setIs13579(isChecked);
            }
        });
        cbEastSouthWestNorthOrZhongFaBaiBusuandacha.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setEastSouthWestNorthOrZhongFaBaiBusuandacha(isChecked);
            }
        });
        cbWealthGodIsEastWind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setWealthGodIsEastWind(isChecked);
            }
        });
        cbRedFlowerAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setRedFlowerAsFixedWealthGod(isChecked);
            }
        });
        cbBlackFlowerAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setBlackFlowerAsFixedWealthGod(isChecked);
            }
        });
        cbBaidaAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setBaidaAsFixedWealthGod(isChecked);
            }
        });
        cbDabaipiAsFixedWealthGod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setDabaipiAsFixedWealthGod(isChecked);
            }
        });
        cbGetZhongFaBaiWealthGodAsEastWind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                diceParameter.setZhongFaBaiWealthGodAsEastWind(isChecked);
            }
        });


        // 初始化控件值
        btnDiceNum.setSelectedItemPosition(diceParameter.getDiceNum());
        btnUseDiceTimes.setSelectedItemPosition(diceParameter.getUseDiceTimes());
        btnUseDiceMethod.setSelectedItemPosition(diceParameter.getUseDiceMethod());
        btnStartCardMethod.setSelectedItemPosition(diceParameter.getStartCardMethod());
        btnStartCardSupplementFlowerMethod.setSelectedItemPosition(diceParameter.getStartCardSupplementFlowerMethod());
        btnStartCardReserve1.setSelectedItemPosition(diceParameter.getStartCardReserve1());
        btnStartCardReserve2.setSelectedItemPosition(diceParameter.getStartCardReserve2());
        cbOneFiveNineGetCard.setChecked(diceParameter.isOneFiveNineGetCard());
        cbEastSouthWestNorthAsColorCard.setChecked(diceParameter.isEastSouthWestNorthAsColorCard());
        cbZhongFaBaiAsColorCard.setChecked(diceParameter.isZhongFaBaiAsColorCard());
        cbAllWindCardAsColorCard.setChecked(diceParameter.isAllWindCardAsColorCard());
        cbBankerAndLastPlayerChangePosition.setChecked(diceParameter.isBankerAndLastPlayerChangePosition());
        cbBaidaAsFlowerCard.setChecked(diceParameter.isBaidaAsFlowerCard());
        cbDabaipiAsFlowerCard.setChecked(diceParameter.isDabaipiAsFlowerCard());
        rgWealthGodMode.check(diceParameter.isOpenWealthGodMode() ? R.id.rb_open : R.id.rb_close);
        btnWealthGodStartCardMethod.setSelectedItemPosition(diceParameter.getWealthGodStartCardMethod());
        btnWealthGodUseDiceMethod.setSelectedItemPosition(diceParameter.getWealthGodUseDiceMethod());
        btnWealthGodCondition.setSelectedItemPosition(diceParameter.getWealthGodCondition());
        btnWindCardWealthGodLoopMethod.setSelectedItemPosition(diceParameter.getWindCardWealthGodLoopMethod());
        btnFixedWealthGod.setSelectedItemPosition(diceParameter.getFixedWealthGod());
        btnWealthGodLastBlockNum.setSelectedItemPosition(diceParameter.getWealthGodLastBlockNum());
        btnWealthGodStartCardPosition.setSelectedItemPosition(diceParameter.getWealthGodStartCardPosition());
        btnWealthGodPrecedenceNum.setSelectedItemPosition(diceParameter.getWealthGodPrecedenceNum());
        btnWealthGodReserve.setSelectedItemPosition(diceParameter.getWealthGodReserve());
        cbZhongAsFixedWealthGod.setChecked(diceParameter.isZhongAsFixedWealthGod());
        cbColorCardAsFixedWealthGod.setChecked(diceParameter.isColorCardAsFixedWealthGod());
        cbYiTiaoAsFixedWealthGod.setChecked(diceParameter.isYiTiaoAsFixedWealthGod());
        cbBaiBanAsFixedWealthGod.setChecked(diceParameter.isBaiBanAsFixedWealthGod());
        cbYaojiufeng.setChecked(diceParameter.isYaojiufeng());
        cbYaojiufengSuanGan.setChecked(diceParameter.isYaojiufengSuanGan());
        cbBaibanAsWealthGodSubstitute.setChecked(diceParameter.isBaibanAsWealthGodSubstitute());
        cbFanpaifengpaiAsWealthGod.setChecked(diceParameter.isFanpaifengpaiAsWealthGod());
        cb13579.setChecked(diceParameter.is13579());
        cbEastSouthWestNorthOrZhongFaBaiBusuandacha.setChecked(diceParameter.isEastSouthWestNorthOrZhongFaBaiBusuandacha());
        cbWealthGodIsEastWind.setChecked(diceParameter.isWealthGodIsEastWind());
        cbRedFlowerAsFixedWealthGod.setChecked(diceParameter.isRedFlowerAsFixedWealthGod());
        cbBlackFlowerAsFixedWealthGod.setChecked(diceParameter.isBlackFlowerAsFixedWealthGod());
        cbBaidaAsFixedWealthGod.setChecked(diceParameter.isBaidaAsFixedWealthGod());
        cbDabaipiAsFixedWealthGod.setChecked(diceParameter.isDabaipiAsFixedWealthGod());
        cbGetZhongFaBaiWealthGodAsEastWind.setChecked(diceParameter.isZhongFaBaiWealthGodAsEastWind());
    }

}
