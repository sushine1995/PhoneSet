package com.wzp.majiangset.activity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BluetoothBaseActivity;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.util.CRC16;
import com.wzp.majiangset.util.CalculateUtil;
import com.wzp.majiangset.widget.AddSubWidget;
import com.wzp.majiangset.widget.ListOptionButton;
import com.wzp.majiangset.widget.MyApplication;

/**
 * Created by wzp on 2017/8/28.
 */

public class StudyTestActivity extends BluetoothBaseActivity {

    private ImageButton ibtnBack;

    private TextView tvDirectionStudyProgress;
    private Button btnDirectionStudyEast;
    private Button btnDirectionStudySouth;
    private Button btnDirectionStudyWest;
    private Button btnDirectionStudyNorth;
    private TextView tvUseDiceProgress;
    private Button btnUseDiceEast;
    private Button btnUseDiceSouth;
    private Button btnUseDiceWest;
    private Button btnUseDiceNorth;
    private Button btnUpDown1;
    private Button btnUpDown2;
    private Button btnCheckMajiang;
    private TextView tvEasthMajiang;
    private TextView tvSouthMajiang;
    private TextView tvWestMajiang;
    private TextView tvNorthMajiang;
    private TextView tvCheckMajiangProgress;
    private ListOptionButton btnRedDice;
    private ListOptionButton btnWhiteDice;
    private Button btnUseDice;
    private Button btnRemoteControlStudy;
    private TextView tvRemoteControlStudyProgress;
    private Button btnChannelTest;
    private TextView tvChannelTestEast;
    private TextView tvChannelTestSouth;
    private TextView tvChannelTestWest;
    private TextView tvChannelTestNorth;
    private TextView tvChannelNo;
    private ImageView ivChannelTestMajiang;
    private AddSubWidget asBrakeTime;
    private Button btnSetBrakeTime;
    private AddSubWidget asUseDiceDelay;
    private Button btnSetUseDiceDelay;
    private AddSubWidget asChargePower;
    private Button btnSetChargePower;

    private Vibrator vibrator;

    private String[] directionArr = new String[] {"东", "南", "西", "北", "升降1", "升降2"};
    private Button[] btnDirectionStudyArr;
    private Button[] btnUseDiceArr;

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ibtn_back:
                    onBackPressed();
                    break;

                case R.id.btn_directionStudyEast:
                case R.id.btn_directionStudySouth:
                case R.id.btn_directionStudyWest:
                case R.id.btn_directionStudyNorth:
                case R.id.btn_useDiceEast:
                case R.id.btn_useDiceSouth:
                case R.id.btn_useDiceWest:
                case R.id.btn_useDiceNorth:
                case R.id.btn_upDown1:
                case R.id.btn_upDown2:
                    int id = v.getId();
                    if (id == R.id.btn_directionStudyEast || id == R.id.btn_directionStudySouth
                            || id == R.id.btn_directionStudyWest || id == R.id.btn_directionStudyNorth) {
                        tvDirectionStudyProgress.setText("");
                    } else {
                        tvUseDiceProgress.setText("");
                    }
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        int direction = 0;
                        switch (v.getId()) {
                            case R.id.btn_directionStudyEast:
                                direction = 0xd1;
                                break;
                            case R.id.btn_directionStudySouth:
                                direction = 0xd2;
                                break;
                            case R.id.btn_directionStudyWest:
                                direction = 0xd3;
                                break;
                            case R.id.btn_directionStudyNorth:
                                direction = 0xd4;
                                break;
                            case R.id.btn_useDiceEast:
                                direction = 0xd5;
                                break;
                            case R.id.btn_useDiceSouth:
                                direction = 0xd6;
                                break;
                            case R.id.btn_useDiceWest:
                                direction = 0xd7;
                                break;
                            case R.id.btn_useDiceNorth:
                                direction = 0xd8;
                                break;
                            case R.id.btn_upDown1:
                                direction = 0xd9;
                                break;
                            case R.id.btn_upDown2:
                                direction = 0xda;
                                break;
                            default:
                                break;
                        }

                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) direction;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_checkMajiang:
                    tvEasthMajiang.setVisibility(View.INVISIBLE);
                    tvSouthMajiang.setVisibility(View.INVISIBLE);
                    tvWestMajiang.setVisibility(View.INVISIBLE);
                    tvNorthMajiang.setVisibility(View.INVISIBLE);
                    tvCheckMajiangProgress.setText("");

                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa2;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_useDice:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa3;
                        sendMsg[2] = (byte) 0x01;
                        sendMsg[3] = (byte) (btnRedDice.getSelectedItemPosition() + 1); // 红色点数
                        sendMsg[4] = (byte) (btnWhiteDice.getSelectedItemPosition() + 1); // 白色点数
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_remoteControlStudy:
                    tvRemoteControlStudyProgress.setText("");

                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa4;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_channelTest:
                    final Drawable drawable = getResources().getDrawable(R.drawable.grey_dot);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    final TextView[] tvDirectionArr = new TextView[] {tvChannelTestEast, tvChannelTestSouth,
                            tvChannelTestWest, tvChannelTestNorth};
                    for (int i = 0; i < tvDirectionArr.length; i++) {
                        tvDirectionArr[i].setCompoundDrawables(null, null, null, drawable);
                    }
                    tvChannelNo.setText("");
                    ivChannelTestMajiang.setVisibility(View.INVISIBLE);

                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa5;
                        sendMsg[2] = (byte) 0x01;
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_setBrakeTime:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa6;
                        sendMsg[2] = (byte) 0x01;
                        sendMsg[3] = (byte) asBrakeTime.getCurVal();
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_setUseDiceDelay:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa7;
                        sendMsg[2] = (byte) 0x01;
                        sendMsg[3] = (byte) asUseDiceDelay.getCurVal();
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                case R.id.btn_setChargePower:
                    if (MyApplication.btClientHelper.isBluetoothConnected()) {
                        byte[] sendMsg = new byte[ProjectConstants.SEND_MSG_LENGTH];
                        sendMsg[0] = (byte) 0xfe;
                        sendMsg[1] = (byte) 0xa8;
                        sendMsg[2] = (byte) 0x01;
                        sendMsg[3] = (byte) asChargePower.getCurVal();
                        CalculateUtil.analyseMessage(sendMsg);
                        byte[] crc = CRC16.getCrc16(sendMsg, ProjectConstants.SEND_MSG_LENGTH - 2);
                        sendMsg[ProjectConstants.CRC_HIGH] = crc[0];
                        sendMsg[ProjectConstants.CRC_LOW] = crc[1];

                        MyApplication.btClientHelper.write(sendMsg);
                    }
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_test);

        initParam();
        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (vibrator != null) {
            vibrator.cancel();
        }
    }

    private void initParam() {
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
    }

    private void initWidget() {
        ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
        tvDirectionStudyProgress = (TextView) findViewById(R.id.tv_directionStudyProgress);
        btnDirectionStudyEast = (Button) findViewById(R.id.btn_directionStudyEast);
        btnDirectionStudySouth = (Button) findViewById(R.id.btn_directionStudySouth);
        btnDirectionStudyWest = (Button) findViewById(R.id.btn_directionStudyWest);
        btnDirectionStudyNorth = (Button) findViewById(R.id.btn_directionStudyNorth);
        tvUseDiceProgress = (TextView) findViewById(R.id.tv_useDiceProgress);
        btnUseDiceEast = (Button) findViewById(R.id.btn_useDiceEast);
        btnUseDiceSouth = (Button) findViewById(R.id.btn_useDiceSouth);
        btnUseDiceWest = (Button) findViewById(R.id.btn_useDiceWest);
        btnUseDiceNorth = (Button) findViewById(R.id.btn_useDiceNorth);
        btnUpDown1 = (Button) findViewById(R.id.btn_upDown1);
        btnUpDown2 = (Button) findViewById(R.id.btn_upDown2);
        btnCheckMajiang = (Button) findViewById(R.id.btn_checkMajiang);
        tvEasthMajiang = (TextView) findViewById(R.id.tv_eastMajiang);
        tvSouthMajiang = (TextView) findViewById(R.id.tv_southMajiang);
        tvWestMajiang = (TextView) findViewById(R.id.tv_westMajiang);
        tvNorthMajiang = (TextView) findViewById(R.id.tv_northMajiang);
        tvCheckMajiangProgress = (TextView) findViewById(R.id.tv_checkMajiangProgress);
        btnRedDice = (ListOptionButton) findViewById(R.id.btn_redDice);
        btnWhiteDice = (ListOptionButton) findViewById(R.id.btn_whiteDice);
        btnUseDice = (Button) findViewById(R.id.btn_useDice);
        btnRemoteControlStudy = (Button) findViewById(R.id.btn_remoteControlStudy);
        tvRemoteControlStudyProgress = (TextView) findViewById(R.id.tv_remoteControlProgress);
        btnChannelTest = (Button) findViewById(R.id.btn_channelTest);
        tvChannelTestEast = (TextView) findViewById(R.id.tv_channelTestEast);
        tvChannelTestSouth = (TextView) findViewById(R.id.tv_channelTestSouth);
        tvChannelTestWest = (TextView) findViewById(R.id.tv_channelTestWest);
        tvChannelTestNorth = (TextView) findViewById(R.id.tv_channelTestNorth);
        tvChannelNo = (TextView) findViewById(R.id.tv_channelNo);
        ivChannelTestMajiang = (ImageView) findViewById(R.id.iv_channelTestMajiang);
        asBrakeTime = (AddSubWidget) findViewById(R.id.as_brakeTime);
        btnSetBrakeTime = (Button) findViewById(R.id.btn_setBrakeTime);
        asUseDiceDelay = (AddSubWidget) findViewById(R.id.as_useDiceDelay);
        btnSetUseDiceDelay = (Button) findViewById(R.id.btn_setUseDiceDelay);
        asChargePower = (AddSubWidget) findViewById(R.id.as_chargePower);
        btnSetChargePower = (Button) findViewById(R.id.btn_setChargePower);


        ibtnBack.setOnClickListener(listener);
        btnDirectionStudyEast.setOnClickListener(listener);
        btnDirectionStudySouth.setOnClickListener(listener);
        btnDirectionStudyWest.setOnClickListener(listener);
        btnDirectionStudyNorth.setOnClickListener(listener);
        btnUseDiceEast.setOnClickListener(listener);
        btnUseDiceSouth.setOnClickListener(listener);
        btnUseDiceWest.setOnClickListener(listener);
        btnUseDiceNorth.setOnClickListener(listener);
        btnUpDown1.setOnClickListener(listener);
        btnUpDown2.setOnClickListener(listener);
        btnCheckMajiang.setOnClickListener(listener);
        btnUseDice.setOnClickListener(listener);
        btnRemoteControlStudy.setOnClickListener(listener);
        btnChannelTest.setOnClickListener(listener);
        btnSetBrakeTime.setOnClickListener(listener);
        btnSetUseDiceDelay.setOnClickListener(listener);
        btnSetChargePower.setOnClickListener(listener);

        btnDirectionStudyArr = new Button[] {
                btnDirectionStudyEast, btnDirectionStudySouth,
                btnDirectionStudyWest, btnDirectionStudyNorth
        };
        btnUseDiceArr = new Button[] {
                btnUseDiceEast, btnUseDiceSouth,
                btnUseDiceWest, btnUseDiceNorth,
                btnUpDown1, btnUpDown2,
        };
    }

    @Override
    protected void onBluetoothDataReceived(byte[] recvData) {
        switch (CalculateUtil.byteToInt(recvData[1])) {
            case 0xd1:
            case 0xd2:
            case 0xd3:
            case 0xd4:
                showProgress(tvDirectionStudyProgress, CalculateUtil.byteToInt(recvData[1]),
                        CalculateUtil.byteToInt(recvData[3]));
                break;
            case 0xd5:
            case 0xd6:
            case 0xd7:
            case 0xd8:
            case 0xd9:
            case 0xda:
                showProgress(tvUseDiceProgress, CalculateUtil.byteToInt(recvData[1]),
                        CalculateUtil.byteToInt(recvData[3]));
                break;

            case 0xa2:
                if (CalculateUtil.byteToInt(recvData[3]) == 0x00) {
                    tvCheckMajiangProgress.setTextColor(getResources().getColor(R.color.red));
                    tvCheckMajiangProgress.setText(getResources().getString(R.string.checking_majiang));
                    btnCheckMajiang.setTextColor(getResources().getColor(R.color.red));
                } else if (CalculateUtil.byteToInt(recvData[3]) == 0x01) {
                    tvCheckMajiangProgress.setTextColor(getResources().getColor(R.color.green));
                    tvCheckMajiangProgress.setText(getResources().getString(R.string.checked_majiang));
                    vibrator.vibrate(200);
                    TextView[] tvMajiangArr = new TextView[] {tvEasthMajiang, tvSouthMajiang,
                        tvWestMajiang, tvNorthMajiang};
                    for (int i = 0; i < 4; i++) {
                        final int imageRes = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[4 + i]));
                        final TextView tvMajiang = tvMajiangArr[i];
                        if (imageRes != -1) {
                            final Drawable drawable = getResources().getDrawable(imageRes);
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            tvMajiang.setCompoundDrawables(null, null, null, drawable);
                            tvMajiang.setVisibility(View.VISIBLE);
                        } else {
                            tvMajiang.setCompoundDrawables(null, null, null, null);
                            tvMajiang.setVisibility(View.VISIBLE);
                        }
                    }
                } else if (CalculateUtil.byteToInt(recvData[3]) == 0x02) {
                    tvCheckMajiangProgress.setTextColor(getResources().getColor(R.color.blue));
                    tvCheckMajiangProgress.setText(getResources().getString(R.string.check_majiang_fail));
                    btnCheckMajiang.setTextColor(getResources().getColor(R.color.black));
                    vibrator.vibrate(200);
                }
                break;

            case 0xa3:
                showToast("打色成功");
                vibrator.vibrate(200);
                break;

            case 0xa4:
                if (CalculateUtil.byteToInt(recvData[3]) == 0x00) {
                    tvRemoteControlStudyProgress.setTextColor(getResources().getColor(R.color.red));
                    tvRemoteControlStudyProgress.setText(R.string.remote_control_studying);
                    btnRemoteControlStudy.setTextColor(getResources().getColor(R.color.red));
                } else if (CalculateUtil.byteToInt(recvData[3]) == 0x01) {
                    tvRemoteControlStudyProgress.setTextColor(getResources().getColor(R.color.green));
                    tvRemoteControlStudyProgress.setText(R.string.remote_control_studied);
                    vibrator.vibrate(200);
                } else if (CalculateUtil.byteToInt(recvData[3]) == 0x02) {
                    tvRemoteControlStudyProgress.setTextColor(getResources().getColor(R.color.blue));
                    tvRemoteControlStudyProgress.setText(R.string.remote_control_study_fail);
                    btnRemoteControlStudy.setTextColor(getResources().getColor(R.color.black));
                    vibrator.vibrate(200);
                }
                break;

            case 0xa5:
                if (CalculateUtil.byteToInt(recvData[3]) == 0x00) {
                    btnChannelTest.setTextColor(getResources().getColor(R.color.red));
                    btnChannelTest.setText(R.string.channel_studying);
                } else if (CalculateUtil.byteToInt(recvData[3]) == 0x01) {
                    btnChannelTest.setTextColor(getResources().getColor(R.color.black));
                    btnChannelTest.setText("通道测试");
                    vibrator.vibrate(200);

                    final TextView[] tvDirectionArr = new TextView[] {tvChannelTestEast, tvChannelTestSouth,
                            tvChannelTestWest, tvChannelTestNorth};
                    for (int i = 0; i < 4; i++) {
                        int resource = CalculateUtil.byteToInt(recvData[4 + i]) == 0x01
                                ? R.drawable.green_dot : R.drawable.red_dot;
                        final Drawable drawable = getResources().getDrawable(resource);
                        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

                        final int j = i;
                        tvDirectionArr[j].setCompoundDrawables(null, null, null, drawable);
                    }
                    final int channelNo = CalculateUtil.byteToInt(recvData[8]);
                    tvChannelNo.setText(String.valueOf(channelNo));
                    int majiangRes = CalculateUtil.getMajiangImage(channelNo);
                    if (majiangRes != -1) {
                        ivChannelTestMajiang.setImageResource(majiangRes);
                        ivChannelTestMajiang.setVisibility(View.VISIBLE);
                    } else {
                        ivChannelTestMajiang.setVisibility(View.INVISIBLE);
                    }
                }
                break;

            case 0xa6:
                showToast("刹车时间设置成功");
                vibrator.vibrate(200);
                break;

            case 0xa7:
                showToast("打色延迟设置成功");
                vibrator.vibrate(200);
                break;

            case 0xa8:
                showToast("充电功率设置成功");
                vibrator.vibrate(200);
                break;
        }
    }

    private void showProgress(final TextView tvProgress, int direction, int progress) {
        final int index;
        final Button[] btnArr;
        if (tvProgress == tvDirectionStudyProgress) {
            index = direction - 0xd1;
            btnArr = btnDirectionStudyArr;
        } else if (tvProgress == tvUseDiceProgress) {
            index = direction - 0xd5;
            btnArr = btnUseDiceArr;
        } else {
            index = -1;
            btnArr = null;
        }
        final StringBuilder sbProgress = new StringBuilder(directionArr[index]);
        if (progress == 0x00) {
            sbProgress.append(getResources().getString(R.string.direction_studying));
            tvProgress.setTextColor(getResources().getColor(R.color.red));
            tvProgress.setText(sbProgress);
            btnArr[index].setTextColor(getResources().getColor(R.color.red));
        } else if (progress == 0x01) {
            sbProgress.append(getResources().getString(R.string.direction_study_finish));
            tvProgress.setTextColor(getResources().getColor(R.color.green));
            tvProgress.setText(sbProgress);

            vibrator.vibrate(200);
        } else if (progress == 0x02) {
            sbProgress.append(getResources().getString(R.string.direction_study_fail));
            tvProgress.setTextColor(getResources().getColor(R.color.blue));
            tvProgress.setText(sbProgress);
            btnArr[index].setTextColor(getResources().getColor(R.color.black));

            vibrator.vibrate(200);
        }
    }

    public static void myStartActivity(Context context) {
        Intent intent = new Intent(context, StudyTestActivity.class);
        context.startActivity(intent);
    }
}
