package com.wzp.majiangset.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.EditPlayMethodActivity;
import com.wzp.majiangset.activity.ShowPlayMethodActivity;
import com.wzp.majiangset.adapter.ShowChooseCardMethodListAdapter;
import com.wzp.majiangset.adapter.ShowParamListAdapter;
import com.wzp.majiangset.entity.BasicParameter;
import com.wzp.majiangset.entity.ChooseCardMethod;
import com.wzp.majiangset.entity.ChooseCardParameter;
import com.wzp.majiangset.entity.DiceParameter;
import com.wzp.majiangset.entity.PlayMethodParameter;
import com.wzp.majiangset.widget.MyApplication;
import com.wzp.majiangset.widget.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzp on 2017/8/28.
 */

public class ShowPlayMethodFragment extends Fragment {

    private TextView tvChooseCardMethodTip;
    private MyListView lvShowChooseCardMethod;

    private TextView tvPlayerNum;
    private TextView tvEveryHandCardNum;
    private TextView tvBankerCardNum;
    private TextView tvOtherPlayerCardNum;
    private TextView tvBankerSkip;
    private TextView tvGetCardMethod;
    private TextView tvProgramStartTimes;
    private TextView tvProgramStopTimes;
    private TextView tvContinuousWorkTimes;
    private TextView tvTotalRounds;
    private TextView tvBroadcastCardNum;
    private TextView tvBasicMethodReserve;
    private TextView tvLayerNum;
    private TextView tvTotalCardNum;
    private TextView tvEastTop;
    private TextView tvEastMiddle;
    private TextView tvEastBottom;
    private TextView tvSouthTop;
    private TextView tvSouthMiddle;
    private TextView tvSouthBottom;
    private TextView tvWestTop;
    private TextView tvWestMiddle;
    private TextView tvWestBottom;
    private TextView tvNorthTop;
    private TextView tvNorthMiddle;
    private TextView tvNorthBottom;
    private MyListView lvBasicParameter;

    private TextView tvDiceNum;
    private TextView tvUseDiceTimes;
    private TextView tvUseDiceMethod;
    private TextView tvStartCardMethod;
    private TextView tvStartCardSupplementFlowerMethod;
    private TextView tvStartCardReserve1;
    private TextView tvStartCardReserve2;
    private MyListView lvDiceParameter;
    private TextView tvWealthGodMode;
    private LinearLayout linearWealthGodMode;
    private TextView tvWealthGodStartCardMethod;
    private TextView tvWealthGodUseDiceMethod;
    private TextView tvWealthGodCondition;
    private TextView tvWindCardWealthGodLoopMethod;
    private TextView tvFixedWealthGod;
    private TextView tvWealthGodLastBlockNum;
    private TextView tvWealthGodStartCardPosition;
    private TextView tvWealthGodPrecedenceNum;
    private TextView tvWealthGodReserve;
    private MyListView lvWealthGodParam;

    private Button btnModifyPlayMethod;

    private int method;
    private PlayMethodParameter parameter;

    private ShowChooseCardMethodListAdapter showChooseCardMethodListAdapter;
    private List<ChooseCardMethod> chooseCardMethodList = new ArrayList<>();
    private ShowParamListAdapter showBasicParamAdapter;
    private List<String> basicParamList = new ArrayList<>();
    private ShowParamListAdapter showDiceParamAdapter;
    private List<String> diceParamList = new ArrayList<>();
    private ShowParamListAdapter showWealthGodParamAdapter;
    private List<String> wealthGodParamList = new ArrayList<>();

    private static final int REQUEST_EDIT_PLAY_METHOD = 0x01;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ShowPlayMethodFragment", "onActivityResult");
        switch (requestCode) {
            case REQUEST_EDIT_PLAY_METHOD:
                if (resultCode == Activity.RESULT_OK) {
                    updateParameter();
                }
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("ShowPlayMethodFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_show_play_method, container, false);

        initData();
        initWidget(view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("ShowPlayMethodFragment", method + "---onStart");
    }

    private void initData() {
        method = getArguments().getInt("method");
        if (method < 0 || method > 2) {
            throw new IllegalArgumentException("method只能为[0, 2]");
        }

        String playMethod = MyApplication.getSpPlayMethod().getString("playMethod" + (method + 1), "");
        try {
            parameter = JSON.parseObject(playMethod, PlayMethodParameter.class);
        } catch (JSONException e) {
            e.printStackTrace();
            parameter = null;
        }
        if (parameter == null) {
            // 设置默认的参数值
            parameter = new PlayMethodParameter();

            BasicParameter bp = new BasicParameter();
            bp.setTotalCardNum(136);
            bp.setEastTop(17);
            bp.setEastMiddle(0);
            bp.setEastBottom(17);
            bp.setNorthTop(17);
            bp.setNorthMiddle(0);
            bp.setNorthBottom(17);
            bp.setWestTop(17);
            bp.setWestMiddle(0);
            bp.setWestBottom(17);
            bp.setSouthTop(17);
            bp.setSouthMiddle(0);
            bp.setSouthBottom(17);
            parameter.setBasicParameter(bp);

            ChooseCardParameter ccp = new ChooseCardParameter();
            ccp.setMethods(new ArrayList<ChooseCardMethod>());
            parameter.setChooseCardParameter(ccp);

            DiceParameter dp = new DiceParameter();
            dp.setDiceNum(1); // 默认有两个色子
            parameter.setDiceParameter(dp);

            MyApplication.getSpPlayMethod().commitString("playMethod" + method,
                    JSON.toJSONString(parameter));
        }

        if (MyApplication.getParameterList().size() <= method) {
            MyApplication.getParameterList().add(parameter);
        } else {
            MyApplication.getParameterList().set(method, parameter);
        }
    }

    private void initWidget(View view) {
        tvChooseCardMethodTip = (TextView) view.findViewById(R.id.tv_chooseCardMethodTip);
        lvShowChooseCardMethod = (MyListView) view.findViewById(R.id.lv_showChooseCardMethod);

        tvPlayerNum = (TextView) view.findViewById(R.id.tv_playerNum);
        tvEveryHandCardNum = (TextView) view.findViewById(R.id.tv_everyHandCardNum);
        tvOtherPlayerCardNum = (TextView) view.findViewById(R.id.tv_otherPlayerCardNum);
        tvBankerCardNum = (TextView) view.findViewById(R.id.tv_bankerCardNum);
        tvBankerSkip = (TextView) view.findViewById(R.id.tv_bankerSkip);
        tvGetCardMethod = (TextView) view.findViewById(R.id.tv_getCardMethod);
        tvProgramStartTimes = (TextView) view.findViewById(R.id.tv_programStartTimes);
        tvProgramStopTimes = (TextView) view.findViewById(R.id.tv_programStopTimes);
        tvContinuousWorkTimes = (TextView) view.findViewById(R.id.tv_continuousWorkRounds);
        tvTotalRounds = (TextView) view.findViewById(R.id.tv_totalRounds);
        tvBroadcastCardNum = (TextView) view.findViewById(R.id.tv_broadcastCardNum);
        tvBasicMethodReserve = (TextView) view.findViewById(R.id.tv_basicMethodReserve);
        tvLayerNum = (TextView) view.findViewById(R.id.tv_layerNum);
        tvTotalCardNum = (TextView) view.findViewById(R.id.tv_totalCardNum);
        tvEastTop = (TextView) view.findViewById(R.id.tv_eastTop);
        tvEastMiddle = (TextView) view.findViewById(R.id.tv_eastMiddle);
        tvEastBottom = (TextView) view.findViewById(R.id.tv_eastBottom);
        tvSouthTop = (TextView) view.findViewById(R.id.tv_southTop);
        tvSouthMiddle = (TextView) view.findViewById(R.id.tv_southMiddle);
        tvSouthBottom = (TextView) view.findViewById(R.id.tv_southBottom);
        tvWestTop = (TextView) view.findViewById(R.id.tv_westTop);
        tvWestMiddle = (TextView) view.findViewById(R.id.tv_westMiddle);
        tvWestBottom = (TextView) view.findViewById(R.id.tv_westBottom);
        tvNorthTop = (TextView) view.findViewById(R.id.tv_northTop);
        tvNorthMiddle = (TextView) view.findViewById(R.id.tv_northMiddle);
        tvNorthBottom = (TextView) view.findViewById(R.id.tv_northBottom);
        lvBasicParameter = (MyListView) view.findViewById(R.id.lv_basicParameter);

        tvDiceNum = (TextView) view.findViewById(R.id.tv_diceNum);
        tvUseDiceTimes = (TextView) view.findViewById(R.id.tv_useDiceTimes);
        tvUseDiceMethod = (TextView) view.findViewById(R.id.tv_useDiceMethod);
        tvStartCardMethod = (TextView) view.findViewById(R.id.tv_startCardMethod);
        tvStartCardSupplementFlowerMethod = (TextView) view.findViewById(R.id.tv_startCardSupplementFlowerMethod);
        tvStartCardReserve1 = (TextView) view.findViewById(R.id.tv_startCardReserve1);
        tvStartCardReserve2 = (TextView) view.findViewById(R.id.tv_startCardReserve2);
        lvDiceParameter = (MyListView) view.findViewById(R.id.lv_diceParameter);
        tvWealthGodMode = (TextView) view.findViewById(R.id.tv_wealthGodMode);
        linearWealthGodMode = (LinearLayout) view.findViewById(R.id.linear_wealthGodMode);
        tvWealthGodStartCardMethod = (TextView) view.findViewById(R.id.tv_wealthGodStartCardMethod);
        tvWealthGodUseDiceMethod = (TextView) view.findViewById(R.id.tv_wealthGodUseDiceMethod);
        tvWealthGodCondition = (TextView) view.findViewById(R.id.tv_wealthGodCondition);
        tvWindCardWealthGodLoopMethod = (TextView) view.findViewById(R.id.tv_windCardWealthGodLoopMethod);
        tvFixedWealthGod = (TextView) view.findViewById(R.id.tv_fixedWealthGod);
        tvWealthGodLastBlockNum = (TextView) view.findViewById(R.id.tv_wealthGodLastBlockNum);
        tvWealthGodStartCardPosition = (TextView) view.findViewById(R.id.tv_wealthGodStartCardPosition);
        tvWealthGodPrecedenceNum = (TextView) view.findViewById(R.id.tv_wealthGodPrecedenceNum);
        tvWealthGodReserve = (TextView) view.findViewById(R.id.tv_wealthGodReserve);
        lvWealthGodParam = (MyListView) view.findViewById(R.id.lv_wealthGodParam);

        btnModifyPlayMethod = (Button) view.findViewById(R.id.btn_modifyPlayMethod);


        showChooseCardMethodListAdapter = new ShowChooseCardMethodListAdapter(getContext(), chooseCardMethodList);
        lvShowChooseCardMethod.setAdapter(showChooseCardMethodListAdapter);

        showBasicParamAdapter = new ShowParamListAdapter(getContext(), basicParamList);
        lvBasicParameter.setAdapter(showBasicParamAdapter);

        showDiceParamAdapter = new ShowParamListAdapter(getContext(), diceParamList);
        lvDiceParameter.setAdapter(showDiceParamAdapter);

        showWealthGodParamAdapter = new ShowParamListAdapter(getContext(), wealthGodParamList);
        lvWealthGodParam.setAdapter(showWealthGodParamAdapter);

        btnModifyPlayMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ShowPlayMethodActivity) getActivity()).setModify(true);
                EditPlayMethodActivity.myStartActivityForResult(ShowPlayMethodFragment.this, method, REQUEST_EDIT_PLAY_METHOD);
            }
        });

        updateParameter();
    }

    public void updateParameter() {
        parameter = MyApplication.getParameterList().get(method);

        // 选牌方式
        ChooseCardParameter ccp = parameter.getChooseCardParameter();
        List<ChooseCardMethod> tmpList = ccp.getMethods();
        chooseCardMethodList.clear();
        for (ChooseCardMethod method :
                tmpList) {
            if (method.isSelected()) {
                chooseCardMethodList.add(method);
            }
        }
        showChooseCardMethodListAdapter.notifyDataSetChanged();
        if (chooseCardMethodList.size() == 0) {
            tvChooseCardMethodTip.setText("暂无数据");
            tvChooseCardMethodTip.setVisibility(View.VISIBLE);
        } else {
            tvChooseCardMethodTip.setVisibility(View.GONE);
        }

        // 基本参数
        BasicParameter bp = parameter.getBasicParameter();
        tvPlayerNum.setText(getResources().getStringArray(R.array.player_num_arr)[bp.getPlayerNum()]);
        tvEveryHandCardNum.setText(getResources().getStringArray(R.array.every_hand_num_arr)[bp.getEveryHandCardNum()]);
        tvBankerCardNum.setText(getResources().getStringArray(R.array.banker_card_num_arr)[bp.getBankerCardNum()]);
        tvOtherPlayerCardNum.setText(getResources().getStringArray(R.array.other_player_card_num_arr)[bp.getOtherPlayerCardNum()]);
        tvBankerSkip.setText(getResources().getStringArray(R.array.banker_skip_arr)[bp.getBankerSkip()]);
        tvGetCardMethod.setText(getResources().getStringArray(R.array.get_card_method_arr)[bp.getGetCardMethod()]);
        tvProgramStartTimes.setText(getResources().getStringArray(R.array.program_start_times_arr)[bp.getProgramStartTimes()]);
        tvProgramStopTimes.setText(getResources().getStringArray(R.array.program_stop_times_arr)[bp.getProgramStopTimes()]);
        tvContinuousWorkTimes.setText(getResources().getStringArray(R.array.continuous_work_rounds_arr)[bp.getContinuousWorkRound()]);
        tvTotalRounds.setText(getResources().getStringArray(R.array.total_rounds_arr)[bp.getTotalUseRound()]);
        tvBroadcastCardNum.setText(getResources().getStringArray(R.array.broadcast_card_num_arr)[bp.getBroadcastCardNum()]);
        tvBasicMethodReserve.setText(getResources().getStringArray(R.array.basic_parameter_reserve_arr)[bp.getReserve()]);

        basicParamList.clear();
        if (bp.isVoiceBox()) {
            basicParamList.add("便携式语音盒");
        }
        if (bp.isMachineHeadPosition()) {
            basicParamList.add("机头定位");
        }
        if (bp.isPanelInduction()) {
            basicParamList.add("面板感应");
        }
        if (bp.isMoneyBoxPosition()) {
            basicParamList.add("钱盒定位");
        }
        if (bp.isContinuousBroadcastCard()) {
            basicParamList.add("连续报牌");
        }
        if (bp.isAssignedIDCardPosition()) {
            basicParamList.add("指定ID卡定位");
        }
        if (bp.isBroadcastWinCard()) {
            basicParamList.add("报胡牌");
        }
        if (bp.isUseDiceTest()) {
            basicParamList.add("二代码");
        }
        if (bp.isPengZhuanBroadcastCard()) {
            basicParamList.add("碰转报牌");
        }
        if (bp.isResetTest()) {
            basicParamList.add("复位测试");
        }
        if (bp.isBloodFight()) {
            basicParamList.add("血战");
        }
        if (bp.isThreePlayer()) {
            basicParamList.add("三人玩法三人点数");
        }
        showBasicParamAdapter.notifyDataSetChanged();
        if (basicParamList.size() == 0) {
            lvBasicParameter.setVisibility(View.GONE);
        } else {
            lvBasicParameter.setVisibility(View.VISIBLE);
        }

        tvLayerNum.setText(bp.isThreeLayer() ? "三层" : "两层");
        tvTotalCardNum.setText(String.valueOf(bp.getTotalCardNum()));
        tvEastTop.setText(String.valueOf(bp.getEastTop()));
        tvEastMiddle.setText(String.valueOf(bp.getEastMiddle()));
        tvEastBottom.setText(String.valueOf(bp.getEastBottom()));
        tvNorthTop.setText(String.valueOf(bp.getNorthTop()));
        tvNorthMiddle.setText(String.valueOf(bp.getNorthMiddle()));
        tvNorthBottom.setText(String.valueOf(bp.getNorthBottom()));
        tvWestTop.setText(String.valueOf(bp.getWestTop()));
        tvWestMiddle.setText(String.valueOf(bp.getWestMiddle()));
        tvWestBottom.setText(String.valueOf(bp.getWestBottom()));
        tvSouthTop.setText(String.valueOf(bp.getSouthTop()));
        tvSouthMiddle.setText(String.valueOf(bp.getSouthMiddle()));
        tvSouthBottom.setText(String.valueOf(bp.getSouthBottom()));

        // 色子参数
        DiceParameter dp = parameter.getDiceParameter();
        tvDiceNum.setText(getResources().getStringArray(R.array.dice_num_arr)[dp.getDiceNum()]);
        tvUseDiceTimes.setText(getResources().getStringArray(R.array.use_dice_times_arr)[dp.getUseDiceTimes()]);
        tvUseDiceMethod.setText(getResources().getStringArray(R.array.use_dice_method_arr)[dp.getUseDiceMethod()]);
        tvStartCardMethod.setText(getResources().getStringArray(R.array.first_dice_position_send_card_method_arr)[dp.getStartCardMethod()]);
        tvStartCardSupplementFlowerMethod.setText(getResources().getStringArray(R.array.start_card_supplement_flower_method_arr)[dp.getStartCardSupplementFlowerMethod()]);
        tvStartCardReserve1.setText(getResources().getStringArray(R.array.start_card_reserve1_arr)[dp.getStartCardReserve1()]);
        tvStartCardReserve2.setText(getResources().getStringArray(R.array.start_card_reserve2_arr)[dp.getStartCardReserve2()]);

        diceParamList.clear();
        if (dp.isOneFiveNineGetCard()) {
            diceParamList.add("打色一、五、九对家抓牌");
        }
        if (dp.isEastSouthWestNorthAsColorCard()) {
            diceParamList.add("东南西北当花牌");
        }
        if (dp.isZhongFaBaiAsColorCard()) {
            diceParamList.add("中发白当花牌");
        }
        if (dp.isAllWindCardAsColorCard()) {
            diceParamList.add("所有风牌当花牌");
        }
        if (dp.isBankerAndLastPlayerChangePosition()) {
            diceParamList.add("胡牌庄家与上家换位置");
        }
        if (dp.isBaidaAsFlowerCard()) {
            diceParamList.add("百搭为花牌");
        }
        if (dp.isDabaipiAsFlowerCard()) {
            diceParamList.add("大白皮为花牌");
        }
        showDiceParamAdapter.notifyDataSetChanged();
        if (diceParamList.size() == 0) {
            lvDiceParameter.setVisibility(View.GONE);
        } else {
            lvDiceParameter.setVisibility(View.VISIBLE);
        }

        tvWealthGodMode.setText(dp.isOpenWealthGodMode() ? "开启" : "关闭");
        linearWealthGodMode.setVisibility(dp.isOpenWealthGodMode() ? View.VISIBLE : View.GONE);
        tvWealthGodStartCardMethod.setText(getResources().getStringArray(R.array.wealth_god_start_card_method_arr)[dp.getWealthGodStartCardMethod()]);
        tvWealthGodUseDiceMethod.setText(getResources().getStringArray(R.array.wealth_god_use_dice_method_arr)[dp.getWealthGodUseDiceMethod()]);
        tvWealthGodCondition.setText(getResources().getStringArray(R.array.wealth_god_condition_arr)[dp.getWealthGodCondition()]);
        tvWindCardWealthGodLoopMethod.setText(getResources().getStringArray(R.array.wind_card_wealth_god_loop_method_arr)[dp.getWindCardWealthGodLoopMethod()]);
        tvFixedWealthGod.setText(getResources().getStringArray(R.array.fixed_wealth_god_arr)[dp.getFixedWealthGod()]);
        tvWealthGodLastBlockNum.setText(getResources().getStringArray(R.array.wealth_god_last_block_num_arr)[dp.getWealthGodLastBlockNum()]);
        tvWealthGodStartCardPosition.setText(getResources().getStringArray(R.array.wealth_god_start_card_position_arr)[dp.getWealthGodStartCardPosition()]);
        tvWealthGodPrecedenceNum.setText(getResources().getStringArray(R.array.wealth_god_precedence_num_arr)[dp.getWealthGodPrecedenceNum()]);
        tvWealthGodReserve.setText(getResources().getStringArray(R.array.wealth_god_reserve_arr)[dp.getWealthGodReserve()]);

        wealthGodParamList.clear();
        if (dp.isZhongAsFixedWealthGod()) {
            wealthGodParamList.add("红中为固定财神");
        }
        if (dp.isColorCardAsFixedWealthGod()) {
            wealthGodParamList.add("花牌为固定财神");
        }
        if (dp.isYiTiaoAsFixedWealthGod()) {
            wealthGodParamList.add("一条为固定财神");
        }
        if (dp.isBaiBanAsFixedWealthGod()) {
            wealthGodParamList.add("白板为固定财神");
        }
        if (dp.isYaojiufeng()) {
            wealthGodParamList.add("幺九风");
        }
        if (dp.isYaojiufengSuanGan()) {
            wealthGodParamList.add("幺九风算杆");
        }
        if (dp.isBaibanAsWealthGodSubstitute()) {
            wealthGodParamList.add("白板为财神替身");
        }
        if (dp.isFanpaifengpaiAsWealthGod()) {
            wealthGodParamList.add("翻牌风牌自身为财神");
        }
        if (dp.is13579()) {
            wealthGodParamList.add("13579任意三张");
        }
        if (dp.isEastSouthWestNorthOrZhongFaBaiBusuandacha()) {
            wealthGodParamList.add("东南西北/中发白不算大岔");
        }
        if (dp.isWealthGodIsEastWind()) {
            wealthGodParamList.add("翻花牌，财神为东风");
        }
        if (dp.isRedFlowerAsFixedWealthGod()) {
            wealthGodParamList.add("红花为固定财神");
        }
        if (dp.isBlackFlowerAsFixedWealthGod()) {
            wealthGodParamList.add("黑花为固定财神");
        }
        if (dp.isBaidaAsFixedWealthGod()) {
            wealthGodParamList.add("百搭为固定财神");
        }
        if (dp.isDabaipiAsFixedWealthGod()) {
            wealthGodParamList.add("大白皮为固定财神");
        }
        if (dp.isZhongFaBaiWealthGodAsEastWind()) {
            wealthGodParamList.add("翻中发白财神为东风");
        }
        showWealthGodParamAdapter.notifyDataSetChanged();
        if (wealthGodParamList.size() == 0) {
            lvWealthGodParam.setVisibility(View.GONE);
        } else {
            lvWealthGodParam.setVisibility(View.VISIBLE);
        }
    }

    public void savePlayMethod() {
        MyApplication.getSpPlayMethod().commitString("playMethod" + (method + 1),
                JSON.toJSONString(parameter));
    }

    public PlayMethodParameter getPlayMethodParameter() {
        return parameter;
    }
}
