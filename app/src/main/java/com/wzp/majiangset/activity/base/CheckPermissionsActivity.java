/**
 *
 */
package com.wzp.majiangset.activity.base;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.wzp.majiangset.R;
import com.wzp.majiangset.constant.ProjectConstants;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class CheckPermissionsActivity extends BaseActivity {

    /**
     * 需要进行检测的权限数组
     */
    private String[] needPermissions = {
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    };

    protected static final int PERMISSON_REQUEST_CODE = 0;

    private static final String TAG = "CheckPermissionsActi";

    private AlertDialog alertDialog;

    @Override
    protected void onStart() {
        super.onStart();

        List<String> deniedPermissionList = checkPermissions(needPermissions);
        Log.d(TAG, "onStart-deniedPermissions" + Objects.toString(deniedPermissionList));
        if (deniedPermissionList == null
                || !(deniedPermissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || deniedPermissionList.contains(Manifest.permission.READ_EXTERNAL_STORAGE))) {
            createDefaultFolder();
        }
    }

    @Override
    protected void onStop() {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        super.onStop();
    }

    /**
     * 检查是否具有设定的权限
     *
     * @param permissions
     * @return 权限是否均被接受
     */
    protected List<String> checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = null;
        if (Build.VERSION.SDK_INT >= 23
                && getApplicationInfo().targetSdkVersion >= 23) {
            needRequestPermissonList = findDeniedPermissions(permissions);
            getPermission(needRequestPermissonList);
        }
        return needRequestPermissonList;
    }

    /**
     * 获取权限集中需要申请权限的列表
     *
     * @param permissions
     * @return
     * @since 2.5.0
     */
    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<>();
        try {
            for (String perm : permissions) {
                Method checkSelfMethod = getClass().getMethod("checkSelfPermission", String.class);
                Method shouldShowRequestPermissionRationaleMethod = getClass().getMethod("shouldShowRequestPermissionRationale",
                        String.class);
                if ((Integer) checkSelfMethod.invoke(this, perm) != PackageManager.PERMISSION_GRANTED
                        || (Boolean) shouldShowRequestPermissionRationaleMethod.invoke(this, perm)) {
                    needRequestPermissonList.add(perm);
                }
            }
        } catch (Throwable e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return needRequestPermissonList;
    }

    private void getPermission(List<String> needRequestPermissonList) {
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            try {
                // 存在被拒绝的权限，需要提醒用户获取相应的权限，方可正常使用定位功能
                String[] array = needRequestPermissonList.toArray(new String[needRequestPermissonList.size()]);
                Method method = getClass().getMethod("requestPermissions",
                        new Class[]{String[].class, int.class});

                method.invoke(this, array, PERMISSON_REQUEST_CODE);
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
    }

    private boolean verifyPermissions(String[] permissions, int[] paramArrayOfInt) {
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < permissions.length; i++) {
            map.put(permissions[i], paramArrayOfInt[i]);
        }
//        for (String permission : needPermissions) {
//            if (!Objects.equals(map.get(permission), PackageManager.PERMISSION_GRANTED)) {
//                return false;
//            }
//        }
        for (String permission : needPermissions) {
            if (map.get(permission) != null && !Objects.equals(map.get(permission), PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    @TargetApi(23)
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] paramArrayOfInt) {
        Log.d(TAG, "onRequestPermissionsResult-permissions=" + Arrays.toString(permissions));
        Log.d(TAG, "onRequestPermissionsResult-paramArrayOfInt=" + Arrays.toString(paramArrayOfInt));

        if (requestCode == PERMISSON_REQUEST_CODE) {
//            List<String> permissionList = Arrays.asList(permissions);
//            for (String permission : needPermissions) {
//                if (!permissionList.contains(permission)) {
//                    return;
//                }
//            }

            if (!verifyPermissions(permissions, paramArrayOfInt)) {
                showMissingPermissionDialog();
            } else {
                createDefaultFolder();
            }
        }
    }

    /**
     * 显示提示信息
     *
     * @since 2.5.0
     */
    protected void showMissingPermissionDialog() {
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.notifyTitle);
            builder.setMessage(R.string.notifyMsg);

            // 拒绝, 退出应用
            builder.setNegativeButton(R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

            builder.setPositiveButton(R.string.setting,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startAppSettings();
                        }
                    });

            builder.setCancelable(false);

            alertDialog = builder.create();
        }
        alertDialog.show();
    }

    /**
     * 启动应用的设置
     *
     * @since 2.5.0
     */
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    private void createDefaultFolder() {
        File basePath = new File(ProjectConstants.BASE_FILE_PATH);
        if (!basePath.exists()) {
            basePath.mkdir();
        }

        File uncaughtExceptionLogPath = new File(ProjectConstants.UNCAUGHT_EXCEPTION_LOG_PATH);
        if (!uncaughtExceptionLogPath.exists()) {
            uncaughtExceptionLogPath.mkdirs();
        }
    }
}
