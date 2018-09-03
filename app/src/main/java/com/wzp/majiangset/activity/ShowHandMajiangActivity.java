package com.wzp.majiangset.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.base.BluetoothBaseActivity;
import com.wzp.majiangset.constant.ProjectConstants;
import com.wzp.majiangset.util.CalculateUtil;
import com.wzp.majiangset.widget.MyApplication;

public class ShowHandMajiangActivity extends BluetoothBaseActivity {

	private ImageButton ibtnBack;
	private ImageButton ibtnMoreFun;

	private LinearLayout linearNorth;
	private LinearLayout linearSouth;
	private LinearLayout linearWest;
	private LinearLayout linearEast;
	private LinearLayout linearNorthUp;
	private LinearLayout linearSouthUp;
	private LinearLayout linearWestUp;
	private LinearLayout linearEastUp;

	private TextView tvBankerPositionEast;
	private TextView tvMyPositionEast;
	private ImageView ivRedDiceEast;
	private ImageView ivBlueDiceEast;
	private ImageView ivRedDiceEast2;
	private ImageView ivBlueDiceEast2;
	private ImageView ivRedDiceEast3;
	private ImageView ivBlueDiceEast3;
	private TextView tvBankerPositionSouth;
	private TextView tvMyPositionSouth;
	private ImageView ivRedDiceSouth;
	private ImageView ivBlueDiceSouth;
	private ImageView ivRedDiceSouth2;
	private ImageView ivBlueDiceSouth2;
	private ImageView ivRedDiceSouth3;
	private ImageView ivBlueDiceSouth3;
	private TextView tvBankerPositionWest;
	private TextView tvMyPositionWest;
	private ImageView ivRedDiceWest;
	private ImageView ivBlueDiceWest;
	private ImageView ivRedDiceWest2;
	private ImageView ivBlueDiceWest2;
	private ImageView ivRedDiceWest3;
	private ImageView ivBlueDiceWest3;
	private TextView tvBankerPositionNorth;
	private TextView tvMyPositionNorth;
	private ImageView ivRedDiceNorth;
	private ImageView ivBlueDiceNorth;
	private ImageView ivRedDiceNorth2;
	private ImageView ivBlueDiceNorth2;
	private ImageView ivRedDiceNorth3;
	private ImageView ivBlueDiceNorth3;

	private TextView tvMajiangColor;
	private LinearLayout linearMoreMajiang;
	private ImageView ivMoreMajiang1;
	private ImageView ivMoreMajiang2;
	private ImageView ivMoreMajiang3;
	private ImageView ivMoreMajiang4;
	private LinearLayout linearLessMajiang;
	private ImageView ivLessMajiang1;
	private ImageView ivLessMajiang2;
	private ImageView ivLessMajiang3;
	private ImageView ivLessMajiang4;
	private LinearLayout linearWrongNum;
	private TextView tvWrongNum;
	private TextView tvErrorTip;

	private PopupMenu pmSelectDirection;

	private static final int RED_DICE = 1;
	private static final int BLUE_DICE = 0;

	private static final int DOWN_MAJIANG_NUM = 17;
	private static final int UP_MAJIANG_NUM = 8;
	private static final int PLACEHOLDER_INDEX = 5; // 两个ImageView之间的占位控件的位置


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_hand_majiang);

		initParam();
		initWidget();
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (MyApplication.btClientHelper == null
				|| !MyApplication.btClientHelper.isBluetoothConnected()) {
			showToast("蓝牙设备尚未连接");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initParam() {

	}

	private void initWidget() {
		ibtnBack = (ImageButton) findViewById(R.id.ibtn_back);
		ibtnMoreFun = (ImageButton) findViewById(R.id.ibtn_moreFun);

		linearNorth = (LinearLayout) findViewById(R.id.linear_north);
		linearSouth = (LinearLayout) findViewById(R.id.linear_south);
		linearWest = (LinearLayout) findViewById(R.id.linear_west);
		linearEast = (LinearLayout) findViewById(R.id.linear_east);
		linearNorthUp = (LinearLayout) findViewById(R.id.linear_northUp);
		linearSouthUp = (LinearLayout) findViewById(R.id.linear_southUp);
		linearWestUp = (LinearLayout) findViewById(R.id.linear_westUp);
		linearEastUp = (LinearLayout) findViewById(R.id.linear_eastUp);

		tvBankerPositionEast = (TextView) findViewById(R.id.tv_bankerPositionEast);
		tvMyPositionEast = (TextView) findViewById(R.id.tv_myPositionEast);
		ivRedDiceEast = (ImageView) findViewById(R.id.iv_redDiceEast);
		ivBlueDiceEast = (ImageView) findViewById(R.id.iv_blueDiceEast);
		ivRedDiceEast2 = (ImageView) findViewById(R.id.iv_redDiceEast2);
		ivBlueDiceEast2 = (ImageView) findViewById(R.id.iv_blueDiceEast2);
		ivRedDiceEast3 = (ImageView) findViewById(R.id.iv_redDiceEast3);
		ivBlueDiceEast3 = (ImageView) findViewById(R.id.iv_blueDiceEast3);
		tvBankerPositionSouth = (TextView) findViewById(R.id.tv_bankerPositionSouth);
		tvMyPositionSouth = (TextView) findViewById(R.id.tv_myPositionSouth);
		ivRedDiceSouth = (ImageView) findViewById(R.id.iv_redDiceSouth);
		ivBlueDiceSouth = (ImageView) findViewById(R.id.iv_blueDiceSouth);
		ivRedDiceSouth2 = (ImageView) findViewById(R.id.iv_redDiceSouth2);
		ivBlueDiceSouth2 = (ImageView) findViewById(R.id.iv_blueDiceSouth2);
		ivRedDiceSouth3 = (ImageView) findViewById(R.id.iv_redDiceSouth3);
		ivBlueDiceSouth3 = (ImageView) findViewById(R.id.iv_blueDiceSouth3);
		tvBankerPositionWest = (TextView) findViewById(R.id.tv_bankerPositionWest);
		tvMyPositionWest = (TextView) findViewById(R.id.tv_myPositionWest);
		ivRedDiceWest = (ImageView) findViewById(R.id.iv_redDiceWest);
		ivBlueDiceWest = (ImageView) findViewById(R.id.iv_blueDiceWest);
		ivRedDiceWest2 = (ImageView) findViewById(R.id.iv_redDiceWest2);
		ivBlueDiceWest2 = (ImageView) findViewById(R.id.iv_blueDiceWest2);
		ivRedDiceWest3 = (ImageView) findViewById(R.id.iv_redDiceWest3);
		ivBlueDiceWest3 = (ImageView) findViewById(R.id.iv_blueDiceWest3);
		tvBankerPositionNorth = (TextView) findViewById(R.id.tv_bankerPositionNorth);
		tvMyPositionNorth = (TextView) findViewById(R.id.tv_myPositionNorth);
		ivRedDiceNorth = (ImageView) findViewById(R.id.iv_redDiceNorth);
		ivBlueDiceNorth = (ImageView) findViewById(R.id.iv_blueDiceNorth);
		ivRedDiceNorth2 = (ImageView) findViewById(R.id.iv_redDiceNorth2);
		ivBlueDiceNorth2 = (ImageView) findViewById(R.id.iv_blueDiceNorth2);
		ivRedDiceNorth3 = (ImageView) findViewById(R.id.iv_redDiceNorth3);
		ivBlueDiceNorth3 = (ImageView) findViewById(R.id.iv_blueDiceNorth3);

		tvMajiangColor = (TextView) findViewById(R.id.tv_majiangColor);
		linearMoreMajiang = (LinearLayout) findViewById(R.id.linear_moreMajiang);
		ivMoreMajiang1 = (ImageView) findViewById(R.id.iv_moreMajiang1);
		ivMoreMajiang2 = (ImageView) findViewById(R.id.iv_moreMajiang2);
		ivMoreMajiang3 = (ImageView) findViewById(R.id.iv_moreMajiang3);
		ivMoreMajiang4 = (ImageView) findViewById(R.id.iv_moreMajiang4);
		ivLessMajiang1 = (ImageView) findViewById(R.id.iv_lessMajiang1);
		linearLessMajiang = (LinearLayout) findViewById(R.id.linear_lessMajiang);
		ivLessMajiang2 = (ImageView) findViewById(R.id.iv_lessMajiang2);
		ivLessMajiang3 = (ImageView) findViewById(R.id.iv_lessMajiang3);
		ivLessMajiang4 = (ImageView) findViewById(R.id.iv_lessMajiang4);
		linearWrongNum = (LinearLayout) findViewById(R.id.linear_wrongNum);
		tvWrongNum = (TextView) findViewById(R.id.tv_wrongMajiangNum);
		tvErrorTip = (TextView) findViewById(R.id.tv_errorTip);

		pmSelectDirection = new PopupMenu(this, ibtnMoreFun);
		getMenuInflater().inflate(R.menu.menu_select_direction, pmSelectDirection.getMenu());
		pmSelectDirection.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				boolean isChecked = !item.isChecked();
				item.setChecked(isChecked);

				switch (item.getItemId()) {
					case R.id.menu_east:
						if (isChecked) {
							linearEast.setVisibility(View.VISIBLE);
							linearEastUp.setVisibility(View.VISIBLE);
						} else {
							linearEast.setVisibility(View.INVISIBLE);
							linearEastUp.setVisibility(View.INVISIBLE);
						}
						break;

					case R.id.menu_south:
						if (isChecked) {
							linearSouth.setVisibility(View.VISIBLE);
							linearSouthUp.setVisibility(View.VISIBLE);
						} else {
							linearSouth.setVisibility(View.INVISIBLE);
							linearSouthUp.setVisibility(View.INVISIBLE);
						}
						break;

					case R.id.menu_west:
						if (isChecked) {
							linearWest.setVisibility(View.VISIBLE);
							linearWestUp.setVisibility(View.VISIBLE);
						} else {
							linearWest.setVisibility(View.INVISIBLE);
							linearWestUp.setVisibility(View.INVISIBLE);
						}
						break;

					case R.id.menu_north:
						if (isChecked) {
							linearNorth.setVisibility(View.VISIBLE);
							linearNorthUp.setVisibility(View.VISIBLE);
						} else {
							linearNorth.setVisibility(View.INVISIBLE);
							linearNorthUp.setVisibility(View.INVISIBLE);
						}
						break;
				}

				return true;
			}
		});

		ibtnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		ibtnMoreFun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pmSelectDirection.show();
			}
		});

		// 麻将控件内容初始化
		initMajiang();
	}

	private void initMajiang() {
		ImageView ivMajiang = null;
		View vPlaceholder =  null;


		// 北、南方向
		LinearLayout.LayoutParams paramsNorSou = new LinearLayout.LayoutParams(
				getResources().getDimensionPixelSize(R.dimen.a_majiang_width),
				getResources().getDimensionPixelSize(R.dimen.a_majiang_height));

		// 西、东方向
		LinearLayout.LayoutParams paramsWesEas = new LinearLayout.LayoutParams(
				getResources().getDimensionPixelSize(R.dimen.a_majiang_height),
				getResources().getDimensionPixelSize(R.dimen.a_majiang_width));

//		// 北、南方向分隔控件
//		LinearLayout.LayoutParams paramsNorSouSplit = new LinearLayout.LayoutParams(
//				0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
//
//		// 西、东方向分隔控件
//		LinearLayout.LayoutParams paramsWesEasSplit = new LinearLayout.LayoutParams(
//				LinearLayout.LayoutParams.MATCH_PARENT, 0, 1);

		// 北、南方向分隔控件
		LinearLayout.LayoutParams paramsNorSouSplit = new LinearLayout.LayoutParams(
				(DOWN_MAJIANG_NUM - UP_MAJIANG_NUM) * getResources().getDimensionPixelSize(R.dimen.a_majiang_width),
				getResources().getDimensionPixelSize(R.dimen.a_majiang_height));

		// 西、东方向分隔控件
		LinearLayout.LayoutParams paramsWesEasSplit = new LinearLayout.LayoutParams(
				getResources().getDimensionPixelSize(R.dimen.a_majiang_height),
				(DOWN_MAJIANG_NUM - UP_MAJIANG_NUM) * getResources().getDimensionPixelSize(R.dimen.a_majiang_width));

		// 北
		for (int i = 0; i < DOWN_MAJIANG_NUM; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			linearNorth.addView(ivMajiang);
		}
		for (int i = 0; i <= UP_MAJIANG_NUM; i++) {
			if (i == UP_MAJIANG_NUM - PLACEHOLDER_INDEX) {
				vPlaceholder = new View(this);
				vPlaceholder.setLayoutParams(paramsNorSouSplit);
				linearNorthUp.addView(vPlaceholder);
				continue;
			}
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			linearNorthUp.addView(ivMajiang);
		}

		// 南
		for (int i = 0; i < DOWN_MAJIANG_NUM; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			linearSouth.addView(ivMajiang);
		}
		for (int i = 0; i <= UP_MAJIANG_NUM; i++) {
			if (i == PLACEHOLDER_INDEX) {
				vPlaceholder = new View(this);
				vPlaceholder.setLayoutParams(paramsNorSouSplit);
				linearSouthUp.addView(vPlaceholder);
				continue;
			}
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsNorSou);
			linearSouthUp.addView(ivMajiang);
		}

		// 西
		for (int i = 0; i < DOWN_MAJIANG_NUM; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			linearWest.addView(ivMajiang);
		}
		for (int i = 0; i <= UP_MAJIANG_NUM; i++) {
			if (i == PLACEHOLDER_INDEX) {
				vPlaceholder = new View(this);
				vPlaceholder.setLayoutParams(paramsWesEasSplit);
				linearWestUp.addView(vPlaceholder);
				continue;
			}
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			linearWestUp.addView(ivMajiang);
		}

		// 东
		for (int i = 0; i < DOWN_MAJIANG_NUM; i++) {
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			linearEast.addView(ivMajiang);
		}
		for (int i = 0; i <= UP_MAJIANG_NUM; i++) {
			if (i == UP_MAJIANG_NUM - PLACEHOLDER_INDEX) {
				vPlaceholder = new View(this);
				vPlaceholder.setLayoutParams(paramsWesEasSplit);
				linearEastUp.addView(vPlaceholder);
				continue;
			}
			ivMajiang = new ImageView(this);
			ivMajiang.setLayoutParams(paramsWesEas);
			linearEastUp.addView(ivMajiang);
		}
	}

	/**
	 * 启动Activity
	 *
	 * @param context
	 */
	public static void myStartActivity(Context context) {
		Intent intent = new Intent(context, ShowHandMajiangActivity.class);
		context.startActivity(intent);
	}

	private void updateMajiang(byte[] recvData) {
		LinearLayout linearDirection = null;
		LinearLayout linearDirectionUp = null;
		Bitmap majiangBitmap = null;
		Matrix matrix = new Matrix();
		int majiangDirection = CalculateUtil.byteToInt(recvData[1]);
		switch (majiangDirection) {
			// 东
			case 0xe9:
				matrix.setRotate(-90);
				linearDirection = linearEast;
				linearDirectionUp = linearEastUp;
				break;

			// 南
			case 0xec:
				linearDirection = linearSouth;
				linearDirectionUp = linearSouthUp;
				break;

			// 西
			case 0xeb:
				matrix.setRotate(90);
				linearDirection = linearWest;
				linearDirectionUp = linearWestUp;
				break;

			// 北
			case 0xea:
				linearDirection = linearNorth;
				linearDirectionUp = linearNorthUp;
				break;

			default:
				break;
		}

		int index; // ImageView在父容器中的索引
		int imageId; // 麻将图片的资源ID
		int msgMajiangIndex = 3; // 报文中麻将的index
		/*
		下方麻将
		 */
		for (int i = 0; i < DOWN_MAJIANG_NUM; i++) {
			if (majiangDirection == 0xe9 || majiangDirection == 0xec) {
				// 东方位先后顺序为，从下至上
				// 北方位先后顺序为，从右至左
				index = DOWN_MAJIANG_NUM - 1 - i;
			} else {
				index = i;
			}

			imageId = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[msgMajiangIndex++]));
			if (imageId != -1) {
				// 正常显示麻将图片
				majiangBitmap = BitmapFactory.decodeResource(getResources(), imageId);
				majiangBitmap = Bitmap.createBitmap(majiangBitmap, 0, 0,
						majiangBitmap.getWidth(), majiangBitmap.getHeight(),
						matrix, true);
				((ImageView) linearDirection.getChildAt(index)).setImageBitmap(majiangBitmap);
				linearDirection.getChildAt(index).setVisibility(View.VISIBLE);
			} else {
				// 当前位置不显示麻将
				linearDirection.getChildAt(index).setVisibility(View.INVISIBLE);
			}
		}
		/*
		上方麻将
		 */
		msgMajiangIndex = 20;
		for (int i = 0; i <= UP_MAJIANG_NUM; i++) {
			if (i == PLACEHOLDER_INDEX) {
				continue;
			}

			if (majiangDirection == 0xe9 || majiangDirection == 0xec) {
				// 东方位先后顺序为，从下至上
				// 北方位先后顺序为，从右至左
				index = UP_MAJIANG_NUM - i;
			} else {
				index = i;
			}

			imageId = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[msgMajiangIndex++]));
			if (imageId != -1) {
				// 正常显示麻将图片
				majiangBitmap = BitmapFactory.decodeResource(getResources(), imageId);
				majiangBitmap = Bitmap.createBitmap(majiangBitmap, 0, 0,
						majiangBitmap.getWidth(), majiangBitmap.getHeight(),
						matrix, true);
				((ImageView) linearDirectionUp.getChildAt(index)).setImageBitmap(majiangBitmap);
				linearDirectionUp.getChildAt(index).setVisibility(View.VISIBLE);
			} else {
				// 当前位置不显示麻将
				linearDirectionUp.getChildAt(index).setVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * 更新色子和位置标识（庄家位置和我的位置）
	 *
	 * @param recvData
	 */
	private void updateDiceAndPositionFlag(byte[] recvData) {
		int bankerPosition = recvData[3];
		switch (bankerPosition) {
			case 0x00:
				tvBankerPositionEast.setVisibility(View.GONE);
				tvBankerPositionSouth.setVisibility(View.GONE);
				tvBankerPositionWest.setVisibility(View.GONE);
				tvBankerPositionNorth.setVisibility(View.GONE);
				break;
			case 0x01:
				tvBankerPositionEast.setVisibility(View.VISIBLE);
				tvBankerPositionSouth.setVisibility(View.GONE);
				tvBankerPositionWest.setVisibility(View.GONE);
				tvBankerPositionNorth.setVisibility(View.GONE);
				break;
			case 0x02:
				tvBankerPositionEast.setVisibility(View.GONE);
				tvBankerPositionSouth.setVisibility(View.VISIBLE);
				tvBankerPositionWest.setVisibility(View.GONE);
				tvBankerPositionNorth.setVisibility(View.GONE);
				break;
			case 0x03:
				tvBankerPositionEast.setVisibility(View.GONE);
				tvBankerPositionSouth.setVisibility(View.GONE);
				tvBankerPositionWest.setVisibility(View.VISIBLE);
				tvBankerPositionNorth.setVisibility(View.GONE);
				break;
			case 0x04:
				tvBankerPositionEast.setVisibility(View.GONE);
				tvBankerPositionSouth.setVisibility(View.GONE);
				tvBankerPositionWest.setVisibility(View.GONE);
				tvBankerPositionNorth.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}

		int myPosition = recvData[4];
		switch (myPosition) {
			case 0x00:
				tvMyPositionEast.setVisibility(View.GONE);
				tvMyPositionSouth.setVisibility(View.GONE);
				tvMyPositionWest.setVisibility(View.GONE);
				tvMyPositionNorth.setVisibility(View.GONE);
				break;
			case 0x01:
				tvMyPositionEast.setVisibility(View.VISIBLE);
				tvMyPositionSouth.setVisibility(View.GONE);
				tvMyPositionWest.setVisibility(View.GONE);
				tvMyPositionNorth.setVisibility(View.GONE);
				break;
			case 0x02:
				tvMyPositionEast.setVisibility(View.GONE);
				tvMyPositionSouth.setVisibility(View.VISIBLE);
				tvMyPositionWest.setVisibility(View.GONE);
				tvMyPositionNorth.setVisibility(View.GONE);
				break;
			case 0x03:
				tvMyPositionEast.setVisibility(View.GONE);
				tvMyPositionSouth.setVisibility(View.GONE);
				tvMyPositionWest.setVisibility(View.VISIBLE);
				tvMyPositionNorth.setVisibility(View.GONE);
				break;
			case 0x04:
				tvMyPositionEast.setVisibility(View.GONE);
				tvMyPositionSouth.setVisibility(View.GONE);
				tvMyPositionWest.setVisibility(View.GONE);
				tvMyPositionNorth.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}

		ImageView[] ivDiceArr = new ImageView[] {
				ivRedDiceEast, ivBlueDiceEast,
				ivRedDiceSouth, ivBlueDiceSouth,
				ivRedDiceWest, ivBlueDiceWest,
				ivRedDiceNorth, ivBlueDiceNorth,
				ivRedDiceEast2, ivBlueDiceEast2,
				ivRedDiceSouth2, ivBlueDiceSouth2,
				ivRedDiceWest2, ivBlueDiceWest2,
				ivRedDiceNorth2, ivBlueDiceNorth2,
				ivRedDiceEast3, ivBlueDiceEast3,
				ivRedDiceSouth3, ivBlueDiceSouth3,
				ivRedDiceWest3, ivBlueDiceWest3,
				ivRedDiceNorth3, ivBlueDiceNorth3
		};
		int diceResId;
		int redBlueFlag;
		for (int i=0; i<ivDiceArr.length; i++) {
			if ((i & 0x01) == 0) {
				// 偶数
				redBlueFlag = RED_DICE;
			} else {
				// 奇数
				redBlueFlag = BLUE_DICE;
			}
			diceResId = getDiceImageResource(recvData[5 + i], redBlueFlag);
			if (diceResId != -1) {
				ivDiceArr[i].setImageResource(diceResId);
				ivDiceArr[i].setVisibility(View.VISIBLE);
			} else {
				ivDiceArr[i].setVisibility(View.GONE);
			}
		}
	}

	private int getDiceImageResource(byte flag, int color) {
		int imageResource = -1;

		if (color == 1) {
			switch (flag) {
				case 0x01:
					imageResource = R.drawable.red_dice1;
					break;
				case 0x02:
					imageResource = R.drawable.red_dice2;
					break;
				case 0x03:
					imageResource = R.drawable.red_dice3;
					break;
				case 0x04:
					imageResource = R.drawable.red_dice4;
					break;
				case 0x05:
					imageResource = R.drawable.red_dice5;
					break;
				case 0x06:
					imageResource = R.drawable.red_dice6;
					break;
				default:
					break;
			}
		} else {
			switch (flag) {
				case 0x01:
					imageResource = R.drawable.blue_dice1;
					break;
				case 0x02:
					imageResource = R.drawable.blue_dice2;
					break;
				case 0x03:
					imageResource = R.drawable.blue_dice3;
					break;
				case 0x04:
					imageResource = R.drawable.blue_dice4;
					break;
				case 0x05:
					imageResource = R.drawable.blue_dice5;
					break;
				case 0x06:
					imageResource = R.drawable.blue_dice6;
					break;
				default:
					break;
			}
		}

		return imageResource;
	}


	@Override
	protected void onBluetoothDataReceived(final byte[] recvData) {
		// 桌面牌
		switch (CalculateUtil.byteToInt(recvData[1])) {
			// 东
			case 0xe9:
			// 南
			case 0xea:
			// 西
			case 0xeb:
			// 北
			case 0xec:
				updateMajiang(recvData);
				break;

			// 色子
			case 0xed:
				updateDiceAndPositionFlag(recvData);
				break;

			// 故障显示
			case 0xe0:
				final int msgIndex = 2;
				int majiangRes;

				/*
				多牌
				 */
				int moreNum = CalculateUtil.byteToInt(recvData[msgIndex]);
				if (moreNum > 4) { // 最多4张牌，超过则认为出错，忽略此条报文
					return;
				} else if (moreNum > 0) {
					linearMoreMajiang.setVisibility(View.VISIBLE);
				} else {
					linearMoreMajiang.setVisibility(View.GONE);
				}
				ImageView[] ivMoreMajiangArr = new ImageView[]{
						ivMoreMajiang1, ivMoreMajiang2,
						ivMoreMajiang3, ivMoreMajiang4
				};
				setMajiangInvisible(ivMoreMajiangArr);
				for (int i = 0; i < moreNum; i++) {
					majiangRes = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[msgIndex + 1 + i]));
					if (majiangRes != ProjectConstants.INVISIBLE_MAJIANG) {
						ivMoreMajiangArr[i].setImageResource(majiangRes);
						ivMoreMajiangArr[i].setVisibility(View.VISIBLE);
					} else {
						ivMoreMajiangArr[i].setVisibility(View.INVISIBLE);
					}
				}

				/*
				少牌
				 */
				int lessNum = CalculateUtil.byteToInt(recvData[msgIndex + 5]);
				if (lessNum > 4) { // 最多4张牌，超过则认为出错，忽略此条报文
					return;
				} else if (lessNum > 0) {
					linearLessMajiang.setVisibility(View.VISIBLE);
				} else {
					linearLessMajiang.setVisibility(View.GONE);
				}
				ImageView[] ivLessMajiangArr = new ImageView[]{
						ivLessMajiang1, ivLessMajiang2,
						ivLessMajiang3, ivLessMajiang4
				};
				setMajiangInvisible(ivLessMajiangArr);
				for (int i = 0; i < lessNum; i++) {
					majiangRes = CalculateUtil.getMajiangImage(CalculateUtil.byteToInt(recvData[msgIndex + 6 + i]));
					if (majiangRes != ProjectConstants.INVISIBLE_MAJIANG) {
						ivLessMajiangArr[i].setImageResource(majiangRes);
						ivLessMajiangArr[i].setVisibility(View.VISIBLE);
					} else {
						ivLessMajiangArr[i].setVisibility(View.INVISIBLE);
					}
				}

				// 错牌数量
				int wrongNum = CalculateUtil.byteToInt(recvData[msgIndex + 10]);
				if (wrongNum > 0) {
					tvWrongNum.setText(String.format("%02d", wrongNum) + "张");
					linearWrongNum.setVisibility(View.VISIBLE);
				} else {
					tvWrongNum.setText("");
					linearWrongNum.setVisibility(View.GONE);
				}

				// 故障提示
				if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 1) {
					tvErrorTip.setText("请检查牌" + String.format("%02d",
							CalculateUtil.byteToInt(recvData[msgIndex + 12])));
					tvErrorTip.setVisibility(View.VISIBLE);
				}
				else if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 2) {
				tvErrorTip.setText("程序关闭");
				tvErrorTip.setVisibility(View.VISIBLE);
			}
				else if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 3) {
					tvErrorTip.setText("总次数到" );
					tvErrorTip.setVisibility(View.VISIBLE);
				}
				else if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 4) {
					tvErrorTip.setText("连续工作次数到" );
					tvErrorTip.setVisibility(View.VISIBLE);
				}
				else if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 5) {
					tvErrorTip.setText("开机次数到" );
					tvErrorTip.setVisibility(View.VISIBLE);
				}
				else if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 6) {
					tvErrorTip.setText("玩法一" );
					tvErrorTip.setVisibility(View.VISIBLE);
				}
				else if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 7) {
					tvErrorTip.setText("玩法二" );
					tvErrorTip.setVisibility(View.VISIBLE);
				}
				else if (CalculateUtil.byteToInt(recvData[msgIndex + 11]) == 8) {
					tvErrorTip.setText("玩法三" );
					tvErrorTip.setVisibility(View.VISIBLE);
				}
			else {
					tvErrorTip.setText("");
					tvErrorTip.setVisibility(View.GONE);
				}

				// 麻将牌颜色
				int color = CalculateUtil.byteToInt(recvData[msgIndex + 13]);
				switch (color) {
					case 0x00:
						tvMajiangColor.setText("");
						tvMajiangColor.setVisibility(View.GONE);
						break;

					case 0x01:
						tvMajiangColor.setText("蓝牌");
						tvMajiangColor.setVisibility(View.VISIBLE);
						break;

					case 0x02:
						tvMajiangColor.setText("绿牌");
						tvMajiangColor.setVisibility(View.VISIBLE);
						break;

					default:
						break;
				}
				break;

			default:
				break;
		}
	}
}
