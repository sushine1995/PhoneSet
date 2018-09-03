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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.EditPlayMethodActivity;
import com.wzp.majiangset.entity.BasicParameter;
import com.wzp.majiangset.widget.ListOptionButton;

/**
 * Created by wzp on 2017/8/29.
 */

public class BasicMethodFragment extends Fragment {

    private EditPlayMethodActivity activity;

    private ListOptionButton btnPlayerNum;
    private ListOptionButton btnEveryHandCardNum;
    private ListOptionButton btnBankerCardNum;
    private ListOptionButton btnOtherPlayerCardNum;
    private ListOptionButton btnBankerSkip;
    private ListOptionButton btnGetCardMethod;
    private ListOptionButton btnProgramStartTimes;
    private ListOptionButton btnProgramStopTimes;
    private ListOptionButton btnContinuousWorkRound;
    private ListOptionButton btnTotalUseRound;
    private ListOptionButton btnBroadcastCardNum;
    private ListOptionButton btnReserve;
    private CheckBox cbVoiceBox;
    private CheckBox cbMachineHeadPosition;
    private CheckBox cbPanelInduction;
    private CheckBox cbMoneyBoxPosition;
    private CheckBox cbContinuousBroadcastCard;
    private CheckBox cbAssignedIDCardPosition;
    private CheckBox cbBroadcastWinCard;
    private CheckBox cbUseDiceTest;
    private CheckBox cbPengZhuanBroadcastCard;
    private CheckBox cbResetTest;
    private CheckBox cbBloodFight;
    private CheckBox cbThreePlayer;
    private RadioGroup rgLayer;
    private TextView tvTotalCardNum;
    private ListOptionButton btnEastTop;
    private ListOptionButton btnEastMiddle;
    private ListOptionButton btnEastBottom;
    private ListOptionButton btnSouthTop;
    private ListOptionButton btnSouthMiddle;
    private ListOptionButton btnSouthBottom;
    private ListOptionButton btnWestTop;
    private ListOptionButton btnWestMiddle;
    private ListOptionButton btnWestBottom;
    private ListOptionButton btnNorthTop;
    private ListOptionButton btnNorthMiddle;
    private ListOptionButton btnNorthBottom;


    private BasicParameter basicParameter;



    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_playerNum:

                    break;

            }
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (EditPlayMethodActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic_method, container, false);

        initData();
        initWidget(view);

        return view;
    }

    private void initData() {
        basicParameter = activity.getBasicParameter();
    }

    private void initWidget(View view) {
        btnPlayerNum = (ListOptionButton) view.findViewById(R.id.btn_playerNum);
        btnEveryHandCardNum = (ListOptionButton) view.findViewById(R.id.btn_everyHandNum);
        btnBankerCardNum = (ListOptionButton) view.findViewById(R.id.btn_bankerCardNum);
        btnOtherPlayerCardNum = (ListOptionButton) view.findViewById(R.id.btn_otherPlayerCardNum);
        btnBankerSkip = (ListOptionButton) view.findViewById(R.id.btn_bankerSkip);
        btnGetCardMethod = (ListOptionButton) view.findViewById(R.id.btn_getCardMethod);
        btnProgramStartTimes = (ListOptionButton) view.findViewById(R.id.btn_programStartTimes);
        btnProgramStopTimes = (ListOptionButton) view.findViewById(R.id.btn_programStopTimes);
        btnContinuousWorkRound = (ListOptionButton) view.findViewById(R.id.btn_continuousWorkingRound);
        btnTotalUseRound = (ListOptionButton) view.findViewById(R.id.btn_totalUseRound);
        btnBroadcastCardNum = (ListOptionButton) view.findViewById(R.id.btn_broadcastCardNum);
        btnReserve = (ListOptionButton) view.findViewById(R.id.btn_reserve);
        cbVoiceBox = (CheckBox) view.findViewById(R.id.cb_voiceBox);
        cbMachineHeadPosition = (CheckBox) view.findViewById(R.id.cb_machineHeadPosition);
        cbPanelInduction = (CheckBox) view.findViewById(R.id.cb_panelInduction);
        cbMoneyBoxPosition = (CheckBox) view.findViewById(R.id.cb_moneyBoxPosition);
        cbContinuousBroadcastCard = (CheckBox) view.findViewById(R.id.cb_continuousBroadcastCard);
        cbAssignedIDCardPosition = (CheckBox) view.findViewById(R.id.cb_assignedIDCardPosition);
        cbBroadcastWinCard = (CheckBox) view.findViewById(R.id.cb_broadcastWinCard);
        cbUseDiceTest = (CheckBox) view.findViewById(R.id.cb_useDiceTest);
        cbPengZhuanBroadcastCard = (CheckBox) view.findViewById(R.id.cb_pengZhuanBroadcastCard);
        cbResetTest = (CheckBox) view.findViewById(R.id.cb_resetTest);
        cbBloodFight = (CheckBox) view.findViewById(R.id.cb_bloodFight);
        cbThreePlayer = (CheckBox) view.findViewById(R.id.cb_threePlayer);
        rgLayer = (RadioGroup) view.findViewById(R.id.rg_layer);
        tvTotalCardNum = (TextView) view.findViewById(R.id.tv_totalCardNum);
        btnEastTop = (ListOptionButton) view.findViewById(R.id.btn_eastTop);
        btnEastMiddle = (ListOptionButton) view.findViewById(R.id.btn_eastMiddle);
        btnEastBottom = (ListOptionButton) view.findViewById(R.id.btn_eastBottom);
        btnSouthTop = (ListOptionButton) view.findViewById(R.id.btn_southTop);
        btnSouthMiddle = (ListOptionButton) view.findViewById(R.id.btn_southMiddle);
        btnSouthBottom = (ListOptionButton) view.findViewById(R.id.btn_southBottom);
        btnWestTop = (ListOptionButton) view.findViewById(R.id.btn_westTop);
        btnWestMiddle = (ListOptionButton) view.findViewById(R.id.btn_westMiddle);
        btnWestBottom = (ListOptionButton) view.findViewById(R.id.btn_westBottom);
        btnNorthTop = (ListOptionButton) view.findViewById(R.id.btn_northTop);
        btnNorthMiddle = (ListOptionButton) view.findViewById(R.id.btn_northMiddle);
        btnNorthBottom = (ListOptionButton) view.findViewById(R.id.btn_northBottom);



        btnPlayerNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setPlayerNum(position);
            }
        });
        btnEveryHandCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setEveryHandCardNum(position);
            }
        });
        btnBankerCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setBankerCardNum(position);
            }
        });
        btnOtherPlayerCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setOtherPlayerCardNum(position);
            }
        });
        btnBankerSkip.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setBankerSkip(position);
            }
        });
        btnGetCardMethod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setGetCardMethod(position);
            }
        });
        btnProgramStartTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setProgramStartTimes(position);
            }
        });
        btnProgramStopTimes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setProgramStopTimes(position);
            }
        });
        btnContinuousWorkRound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setContinuousWorkRound(position);
            }
        });
        btnTotalUseRound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setTotalUseRound(position);
            }
        });
        btnBroadcastCardNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setBroadcastCardNum(position);
            }
        });
        btnReserve.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setReserve(position);
            }
        });
        cbVoiceBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setVoiceBox(isChecked);
            }
        });
        cbMachineHeadPosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setMachineHeadPosition(isChecked);
            }
        });
        cbPanelInduction.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setPanelInduction(isChecked);
            }
        });
        cbMoneyBoxPosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setMoneyBoxPosition(isChecked);
            }
        });
        cbContinuousBroadcastCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setContinuousBroadcastCard(isChecked);
            }
        });
        cbAssignedIDCardPosition.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setAssignedIDCardPosition(isChecked);
            }
        });
        cbBroadcastWinCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setBroadcastWinCard(isChecked);
            }
        });
        cbUseDiceTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setUseDiceTest(isChecked);
            }
        });
        cbPengZhuanBroadcastCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setPengZhuanBroadcastCard(isChecked);
            }
        });
        cbResetTest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setResetTest(isChecked);
            }
        });
        cbBloodFight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setBloodFight(isChecked);
            }
        });
        cbThreePlayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                basicParameter.setThreePlayer(isChecked);
            }
        });
        rgLayer.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_double) {
                    // 两层
                    basicParameter.setThreeLayer(false);

                    btnEastMiddle.setEnabled(false);
                    btnNorthMiddle.setEnabled(false);
                    btnWestMiddle.setEnabled(false);
                    btnSouthMiddle.setEnabled(false);

                    setTotalCardNum();
                } else {
                    // 三层
                    basicParameter.setThreeLayer(true);

                    btnEastMiddle.setEnabled(true);
                    btnNorthMiddle.setEnabled(true);
                    btnWestMiddle.setEnabled(true);
                    btnSouthMiddle.setEnabled(true);

                    setTotalCardNum();
                }
            }
        });
        btnEastTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setEastTop(position);
                setTotalCardNum();
            }
        });
        btnEastMiddle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setEastMiddle(position);
                setTotalCardNum();
            }
        });
        btnEastBottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setEastBottom(position);
                setTotalCardNum();
            }
        });
        btnNorthTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setNorthTop(position);
                setTotalCardNum();
            }
        });
        btnNorthMiddle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setNorthMiddle(position);
                setTotalCardNum();
            }
        });
        btnNorthBottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setNorthBottom(position);
                setTotalCardNum();
            }
        });
        btnWestTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setWestTop(position);
                setTotalCardNum();
            }
        });
        btnWestMiddle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setWestMiddle(position);
                setTotalCardNum();
            }
        });
        btnWestBottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setWestBottom(position);
                setTotalCardNum();
            }
        });
        btnSouthTop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setSouthTop(position);
                setTotalCardNum();
            }
        });
        btnSouthMiddle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setSouthMiddle(position);
                setTotalCardNum();
            }
        });
        btnSouthBottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                basicParameter.setSouthBottom(position);
                setTotalCardNum();
            }
        });


        // 初始化控件参数
        btnPlayerNum.setSelectedItemPosition(basicParameter.getPlayerNum());
        btnEveryHandCardNum.setSelectedItemPosition(basicParameter.getEveryHandCardNum());
        btnBankerCardNum.setSelectedItemPosition(basicParameter.getBankerCardNum());
        btnOtherPlayerCardNum.setSelectedItemPosition(basicParameter.getOtherPlayerCardNum());
        btnBankerSkip.setSelectedItemPosition(basicParameter.getBankerSkip());
        btnGetCardMethod.setSelectedItemPosition(basicParameter.getGetCardMethod());
        btnProgramStartTimes.setSelectedItemPosition(basicParameter.getProgramStartTimes());
        btnProgramStopTimes.setSelectedItemPosition(basicParameter.getProgramStopTimes());
        btnContinuousWorkRound.setSelectedItemPosition(basicParameter.getContinuousWorkRound());
        btnTotalUseRound.setSelectedItemPosition(basicParameter.getTotalUseRound());
        btnBroadcastCardNum.setSelectedItemPosition(basicParameter.getBroadcastCardNum());
        btnReserve.setSelectedItemPosition(basicParameter.getReserve());
        cbVoiceBox.setChecked(basicParameter.isVoiceBox());
        cbMachineHeadPosition.setChecked(basicParameter.isMachineHeadPosition());
        cbPanelInduction.setChecked(basicParameter.isPanelInduction());
        cbMoneyBoxPosition.setChecked(basicParameter.isMoneyBoxPosition());
        cbContinuousBroadcastCard.setChecked(basicParameter.isContinuousBroadcastCard());
        cbAssignedIDCardPosition.setChecked(basicParameter.isAssignedIDCardPosition());
        cbBroadcastWinCard.setChecked(basicParameter.isBroadcastWinCard());
        cbUseDiceTest.setChecked(basicParameter.isUseDiceTest());
        cbPengZhuanBroadcastCard.setChecked(basicParameter.isPengZhuanBroadcastCard());
        cbResetTest.setChecked(basicParameter.isResetTest());
        cbBloodFight.setChecked(basicParameter.isBloodFight());
        cbThreePlayer.setChecked(basicParameter.isThreePlayer());
        rgLayer.check(basicParameter.isThreeLayer() ? R.id.rb_tripple : R.id.rb_double);
        tvTotalCardNum.setText(String.valueOf(basicParameter.getTotalCardNum()));
        btnEastTop.setSelectedItemPosition(basicParameter.getEastTop());
        btnEastMiddle.setSelectedItemPosition(basicParameter.getEastMiddle());
        btnEastBottom.setSelectedItemPosition(basicParameter.getEastBottom());
        btnNorthTop.setSelectedItemPosition(basicParameter.getNorthTop());
        btnNorthMiddle.setSelectedItemPosition(basicParameter.getNorthMiddle());
        btnNorthBottom.setSelectedItemPosition(basicParameter.getNorthBottom());
        btnWestTop.setSelectedItemPosition(basicParameter.getWestTop());
        btnWestMiddle.setSelectedItemPosition(basicParameter.getWestMiddle());
        btnWestBottom.setSelectedItemPosition(basicParameter.getWestBottom());
        btnSouthTop.setSelectedItemPosition(basicParameter.getSouthTop());
        btnSouthMiddle.setSelectedItemPosition(basicParameter.getSouthMiddle());
        btnSouthBottom.setSelectedItemPosition(basicParameter.getSouthBottom());
        if (basicParameter.isThreeLayer()) {
            btnEastMiddle.setEnabled(true);
            btnNorthMiddle.setEnabled(true);
            btnWestMiddle.setEnabled(true);
            btnSouthMiddle.setEnabled(true);
        } else {
            btnEastMiddle.setEnabled(false);
            btnNorthMiddle.setEnabled(false);
            btnWestMiddle.setEnabled(false);
            btnSouthMiddle.setEnabled(false);
        }
    }

    /**
     * 由机器档位解析出牌数
     * @param position
     */
    private void analyseCardNumFromMachineGear(int position) {
        if (rgLayer.getCheckedRadioButtonId() == R.id.rb_double) {
            // 双层
            String machineGear = getResources().getStringArray(R.array.machine_gear_arr)[position];
            basicParameter.setTotalCardNum(Integer.parseInt(machineGear.substring(machineGear.indexOf('：') + 1,
                    machineGear.indexOf('/'))));
            String[] numArr = machineGear.substring(machineGear.indexOf('/') + 1,
                    machineGear.length()).split("，");
            int eastTopNum = 0;
            int eastMiddleNum = 0;
            int eastBottomNum = 0;
            int northTopNum = 0;
            int northMiddleNum = 0;
            int northBottomNum = 0;
            int westTopNum = 0;
            int westMiddleNum = 0;
            int westBottomNum = 0;
            int southTopNum = 0;
            int southMiddleNum = 0;
            int southBottomNum = 0;
            if (position >= 42) {
                // 特殊的几种情况，单独处理
                if (position == 42) {
                    eastTopNum = 17;
                    eastMiddleNum = 0;
                    eastBottomNum = eastTopNum;
                    northTopNum = 17;
                    northMiddleNum = 0;
                    northBottomNum = northTopNum;
                    westTopNum = 17;
                    westMiddleNum = 0;
                    westBottomNum = westTopNum;
                    southTopNum = 17;
                    southMiddleNum = 0;
                    southBottomNum = southTopNum + 1;
                } else if (position == 43) {
                    eastTopNum = 8;
                    eastMiddleNum = 0;
                    eastBottomNum = eastTopNum + 1;
                    northTopNum = 8;
                    northMiddleNum = 0;
                    northBottomNum = northTopNum + 1;
                    westTopNum = 8;
                    westMiddleNum = 0;
                    westBottomNum = westTopNum + 1;
                    southTopNum = 8;
                    southMiddleNum = 0;
                    southBottomNum = southTopNum + 1;
                } else if (position <= 45) {
                    eastTopNum = Integer.parseInt(numArr[0].substring(0, numArr[0].length() - 1));
                    eastMiddleNum = 0;
                    eastBottomNum = 0;
                    northTopNum = Integer.parseInt(numArr[1].substring(0, numArr[1].length() - 1));
                    northMiddleNum = 0;
                    northBottomNum = 0;
                    westTopNum = Integer.parseInt(numArr[2].substring(0, numArr[2].length() - 1));
                    westMiddleNum = 0;
                    westBottomNum = 0;
                    southTopNum = Integer.parseInt(numArr[3].substring(0, numArr[3].length() - 1));
                    southMiddleNum = 0;
                    southBottomNum = 0;
                }
            } else {
                eastTopNum = Integer.parseInt(numArr[0]);
                eastMiddleNum = 0;
                eastBottomNum = eastTopNum;
                northTopNum = Integer.parseInt(numArr[1]);
                northMiddleNum = 0;
                northBottomNum = northTopNum;
                westTopNum = Integer.parseInt(numArr[2]);
                westMiddleNum = 0;
                westBottomNum = westTopNum;
                southTopNum = Integer.parseInt(numArr[3]);
                southMiddleNum = 0;
                southBottomNum = southTopNum;
            }

            basicParameter.setEastTop(eastTopNum);
            basicParameter.setEastMiddle(eastMiddleNum);
            basicParameter.setEastBottom(eastBottomNum);
            basicParameter.setNorthTop(northTopNum);
            basicParameter.setNorthMiddle(northMiddleNum);
            basicParameter.setNorthBottom(northBottomNum);
            basicParameter.setWestTop(westTopNum);
            basicParameter.setWestMiddle(westMiddleNum);
            basicParameter.setWestBottom(westBottomNum);
            basicParameter.setSouthTop(southTopNum);
            basicParameter.setSouthMiddle(southMiddleNum);
            basicParameter.setSouthBottom(southBottomNum);

            btnEastMiddle.setEnabled(false);
            btnNorthMiddle.setEnabled(false);
            btnWestMiddle.setEnabled(false);
            btnSouthMiddle.setEnabled(false);
        } else {
            // 三层


            btnEastMiddle.setEnabled(true);
            btnNorthMiddle.setEnabled(true);
            btnWestMiddle.setEnabled(true);
            btnSouthMiddle.setEnabled(true);
        }

        tvTotalCardNum.setText(String.valueOf(basicParameter.getTotalCardNum()));
        btnEastTop.setText(String.valueOf(basicParameter.getEastTop()));
        btnEastMiddle.setText(String.valueOf(basicParameter.getEastMiddle()));
        btnEastBottom.setText(String.valueOf(basicParameter.getEastBottom()));
        btnNorthTop.setText(String.valueOf(basicParameter.getNorthTop()));
        btnNorthMiddle.setText(String.valueOf(basicParameter.getNorthMiddle()));
        btnNorthBottom.setText(String.valueOf(basicParameter.getNorthBottom()));
        btnWestTop.setText(String.valueOf(basicParameter.getWestTop()));
        btnWestMiddle.setText(String.valueOf(basicParameter.getWestMiddle()));
        btnWestBottom.setText(String.valueOf(basicParameter.getWestBottom()));
        btnSouthTop.setText(String.valueOf(basicParameter.getSouthTop()));
        btnSouthMiddle.setText(String.valueOf(basicParameter.getSouthMiddle()));
        btnSouthBottom.setText(String.valueOf(basicParameter.getSouthBottom()));
    }

    private void setTotalCardNum() {
        int totalCardNum = 0;
        if (basicParameter.isThreeLayer()) {
            totalCardNum = basicParameter.getEastTop() + basicParameter.getEastMiddle() + basicParameter.getEastBottom()
                    + basicParameter.getNorthTop() + basicParameter.getNorthMiddle() + basicParameter.getNorthBottom()
                    + basicParameter.getWestTop() + basicParameter.getWestMiddle() + basicParameter.getWestBottom()
                    + basicParameter.getSouthTop() + basicParameter.getSouthMiddle() + basicParameter.getSouthBottom();
        } else {
            totalCardNum = basicParameter.getEastTop() + basicParameter.getEastBottom()
                    + basicParameter.getNorthTop() + basicParameter.getNorthBottom()
                    + basicParameter.getWestTop() + basicParameter.getWestBottom()
                    + basicParameter.getSouthTop() + basicParameter.getSouthBottom();
        }
        basicParameter.setTotalCardNum(totalCardNum);
        tvTotalCardNum.setText(String.valueOf(totalCardNum));
    }
}
