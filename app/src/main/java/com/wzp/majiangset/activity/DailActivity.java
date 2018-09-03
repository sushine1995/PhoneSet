package com.wzp.majiangset.activity;

import android.app.Activity;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BluetoothBaseActivity;
import com.wzp.majiangset.constant.BluetoothState;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.util.BluetoothClientHelper;
import com.wzp.majiangset.util.CRC16;
import com.wzp.majiangset.util.CalculateUtil;
import com.wzp.majiangset.widget.MyApplication;

import java.lang.reflect.Method;

import static com.wzp.majiangset.constant.ProjectConstants.DATA_LENGTH;

public class DailActivity extends BluetoothBaseActivity {

    private EditText edtNum;
    private Button btnDialDel;
    private Button btnNum0;
    private Button btnNum1;
    private Button btnNum2;
    private Button btnNum3;
    private Button btnNum4;
    private Button btnNum5;
    private Button btnNum6;
    private Button btnNum7;
    private Button btnNum8;
    private Button btnNum9;
    private Button btnNumStar;
    private Button btnNumSharp;
    private Button btnDial;
    private ImageView ivBtFlag;

    private BluetoothAdapter bluetoothAdapter;
    private Vibrator vibrator;

    private String macAddr; // 蓝牙设备的mac地址

    private IBluetoothConnect iBluetoothConnect;

    private static final int REQUEST_ENABLE_BT = 0x00; // 请求打开蓝牙
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 0x01; // 请求安全连接蓝牙设备

    private static final String LOG_TAG = "DailActivity";


    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int index = edtNum.getSelectionStart();
            switch (view.getId()) {
                case R.id.btn_dialDel:
                    if (index >= 1) {
                        edtNum.getText().delete(index - 1, index);
                    }
                    break;

                case R.id.btn_num0:
                    edtNum.getText().insert(index, "0");
                    break;

                case R.id.btn_num1:
                    edtNum.getText().insert(index, "1");
                    break;

                case R.id.btn_num2:
                    edtNum.getText().insert(index, "2");
                    break;

                case R.id.btn_num3:
                    edtNum.getText().insert(index, "3");
                    break;

                case R.id.btn_num4:
                    edtNum.getText().insert(index, "4");
                    break;

                case R.id.btn_num5:
                    edtNum.getText().insert(index, "5");
                    break;

                case R.id.btn_num6:
                    edtNum.getText().insert(index, "6");
                    break;

                case R.id.btn_num7:
                    edtNum.getText().insert(index, "7");
                    break;

                case R.id.btn_num8:
                    edtNum.getText().insert(index, "8");
                    break;

                case R.id.btn_num9:
                    edtNum.getText().insert(index, "9");
                    break;

                case R.id.btn_numStar:
                    edtNum.getText().insert(index, "*");
                    break;

                case R.id.btn_numSharp:
                    edtNum.getText().insert(index, "#");
                    break;

                case R.id.btn_dial:
                    String strNum = edtNum.getText().toString();
                    if (strNum.equals(ProjectConstants.CIPHER) || strNum.equals(ProjectConstants.CIPHER2)) {
                        if (bluetoothAdapter == null) {
                            Toast.makeText(DailActivity.this, "当前设备不具备蓝牙功能！",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }

                        if (!bluetoothAdapter.isEnabled()) {
                            // 蓝牙尚未打开
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        } else {
                            // 蓝牙已经打开
                            edtNum.setText("");
                            ChooseFunctionActivity.myStartActivity(DailActivity.this);
                        }
                    } else if (strNum.equals(ProjectConstants.CIPHER_OPEN_BLUETOOTH)) {
                        if (bluetoothAdapter == null) {
                            Toast.makeText(DailActivity.this, "当前设备不具备蓝牙功能！",
                                    Toast.LENGTH_LONG).show();
                            break;
                        }

                        if (!bluetoothAdapter.isEnabled()) {
                            // 蓝牙尚未打开
                            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                        } else {
                            // 蓝牙已经打开

                            Log.i("DailActivity", "bluetoothAdapter.getState():"+String.valueOf(bluetoothAdapter.getState()));
                            //当蓝牙为打开状态时，会优先连接历史匹配的设备，导致等待时间较久。停止

                            Intent searchIntent = new Intent(DailActivity.this, DeviceListActivity.class);
                            startActivityForResult(searchIntent, REQUEST_CONNECT_DEVICE_SECURE);
                        }
                    } else if (strNum.equals(ProjectConstants.CIPHER_OPEN_DAIL_MANUAL)) {
                        DailManualActivity.myStartActivity(DailActivity.this);
                    } else {
                        if (!TextUtils.isEmpty(strNum)) {
                            if (MyApplication.btClientHelper != null
                                    && MyApplication.btClientHelper.isBluetoothConnected()) {
                                byte[] sendMsg = new byte[DATA_LENGTH];
                                int i = 0;
                                sendMsg[i++] = (byte) 0xfe;
                                sendMsg[i++] = (byte) 0xa1;

                                char ch;
                                for (int j=0; j<strNum.length(); j++) {
                                    ch = strNum.charAt(j);
                                    if (ch >= '0' && ch <= '9') {
                                        sendMsg[i++] = (byte) (ch - '0');
                                    } else {
                                        sendMsg[i++] = (byte) ch;
                                    }
                                    if (i == (DATA_LENGTH - 3)) {
                                        break;
                                    }
                                }
                                sendMsg[i++] = (byte) 0xa1;
                                CalculateUtil.analyseMessage(sendMsg);
                                byte[] crc = CRC16.getCrc16(sendMsg, DATA_LENGTH - 2);
                                sendMsg[DATA_LENGTH - 2] = crc[0];
                                sendMsg[DATA_LENGTH - 1] = crc[1];

                                MyApplication.btClientHelper.write(sendMsg);
                            }
                        }
                    }

                    break;

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dail);

        initParam();

        if (bluetoothAdapter == null) {
            Toast.makeText(DailActivity.this, "当前设备不具备蓝牙功能！",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        if (!bluetoothAdapter.isEnabled()) {
            // 蓝牙尚未打开
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            // 蓝牙已经打开
            initWidget();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (bluetoothAdapter.isEnabled()) {
            if (MyApplication.btClientHelper != null
                    && MyApplication.btClientHelper.isBluetoothConnected()) {
                ivBtFlag.setImageResource(R.drawable.footer_left_bt_con);
            } else {
                ivBtFlag.setImageResource(R.drawable.footer_left);
            }

            MyApplication.btClientHelper.setBluetoothConnect(iBluetoothConnect);
        }
    }

    @Override
    public void onBackPressed() {
        if (MyApplication.btClientHelper != null) {
            MyApplication.btClientHelper.stop();
            MyApplication.btClientHelper = null;
        }

        // 退出应用时，清空消息队列
        MyApplication.getMessageQueue().clear();

        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    // 成功打开蓝牙，初始化控件
                    initWidget();
                } else {
                    Log.d(LOG_TAG, "蓝牙尚未开启");
                    Toast.makeText(this, "蓝牙尚未开启，无法使用该功能", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case REQUEST_CONNECT_DEVICE_SECURE:
                if (resultCode == Activity.RESULT_OK) {
                    connectDevice(data);
                }
                break;

            default:
                break;
        }
    }

    private void initParam() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);

        if (MyApplication.btClientHelper == null) {
            MyApplication.btClientHelper = new BluetoothClientHelper();
        }

        macAddr = MyApplication.getSpSetting().getString("macAddress", null);
    }

    private void initWidget() {
        edtNum = (EditText) findViewById(R.id.edt_num);
        btnDialDel = (Button) findViewById(R.id.btn_dialDel);
        btnNum0 = (Button) findViewById(R.id.btn_num0);
        btnNum1 = (Button) findViewById(R.id.btn_num1);
        btnNum2 = (Button) findViewById(R.id.btn_num2);
        btnNum3 = (Button) findViewById(R.id.btn_num3);
        btnNum4 = (Button) findViewById(R.id.btn_num4);
        btnNum5 = (Button) findViewById(R.id.btn_num5);
        btnNum6 = (Button) findViewById(R.id.btn_num6);
        btnNum7 = (Button) findViewById(R.id.btn_num7);
        btnNum8 = (Button) findViewById(R.id.btn_num8);
        btnNum9 = (Button) findViewById(R.id.btn_num9);
        btnNumStar = (Button) findViewById(R.id.btn_numStar);
        btnNumSharp = (Button) findViewById(R.id.btn_numSharp);
        btnDial = (Button) findViewById(R.id.btn_dial);
        ivBtFlag = (ImageView) findViewById(R.id.iv_btFlag);


        if (android.os.Build.VERSION.SDK_INT <= 10) {
            edtNum.setInputType(InputType.TYPE_NULL);
            edtNum.setFocusable(false);
        } else {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Method setSoftInputShownOnFocus = EditText.class.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(edtNum, false);
            } catch (Exception e) {
                e.printStackTrace();
                edtNum.setInputType(InputType.TYPE_NULL);
                edtNum.setFocusable(false);
            }
        }

        btnDialDel.setOnClickListener(listener);
        btnDialDel.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                edtNum.setText("");
                return false;
            }
        });
        btnNum0.setOnClickListener(listener);
        btnNum1.setOnClickListener(listener);
        btnNum2.setOnClickListener(listener);
        btnNum3.setOnClickListener(listener);
        btnNum4.setOnClickListener(listener);
        btnNum5.setOnClickListener(listener);
        btnNum6.setOnClickListener(listener);
        btnNum7.setOnClickListener(listener);
        btnNum8.setOnClickListener(listener);
        btnNum9.setOnClickListener(listener);
        btnNumStar.setOnClickListener(listener);
        btnNumSharp.setOnClickListener(listener);
        btnDial.setOnClickListener(listener);

        iBluetoothConnect = new IBluetoothConnect() {
            @Override
            public void showToast(String info, int duration) {
                DailActivity.this.showToast(info, duration);
            }

            @Override
            public void showToast(String info) {
                DailActivity.this.showToast(info);
            }

            @Override
            public void showBtState(final int state) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (state) {
                            case BluetoothState.STATE_NONE:
                                ivBtFlag.setImageResource(R.drawable.footer_left);
                                break;

                            case BluetoothState.STATE_CONNECTING:
                                ivBtFlag.setImageResource(R.drawable.footer_left);
                                break;

                            case BluetoothState.STATE_CONNECTED:
                                ivBtFlag.setImageResource(R.drawable.footer_left_bt_con);
                                edtNum.setText("");

                                if (readDataThread == null) {
                                    readDataThread = new ReadDataThread();
                                    readDataThread.start();
                                }

                                // 保存mac地址至SharedPreferences中
                                if (!TextUtils.isEmpty(macAddr)) {
                                    MyApplication.getSpSetting().commitString("macAddress", macAddr);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                });
            }
        };
        MyApplication.btClientHelper.setBluetoothConnect(iBluetoothConnect);

        if (macAddr != null) {
            showToast("正在连接蓝牙设备，请稍后...");
            connectDevice(macAddr);
        }
    }

    /**
     * 连接蓝牙设备
     *
     * @param data
     */
    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(ProjectConstants.EXTRA_DEVICE_ADDRESS);
        macAddr = address; // 记录mac地址，当蓝牙连接成功后，将mac地址保存至SharedPreferences
        connectDevice(address);
    }

    private void connectDevice(String address) {
        // Get the BluetoothDevice object
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        MyApplication.btClientHelper.connect(device);
    }

    @Override
    protected void onBluetoothDataReceived(byte[] recvData) {
        if (CalculateUtil.byteToInt(recvData[1]) == 0xa1
                && CalculateUtil.byteToInt(recvData[2]) == 0x01) {
            vibrator.vibrate(200);
            edtNum.setText("");
        }
    }
}
