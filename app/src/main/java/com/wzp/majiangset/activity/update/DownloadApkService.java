package com.wzp.majiangset.activity.update;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.wzp.majiangset.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by su823 on 18/6/27.
 */

public class DownloadApkService extends IntentService {
    private static final int BUFFER_SIZE = 10 * 1024; //缓存大小
    private static final String TAG = "DownloadService";

    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;
    private NotificationChannel mNotifyChannel;
    private NotificationCompat.Builder mBuilder;

    public DownloadApkService() {
        super("DownloadApkService");
    }

    /**
     * 在onHandleIntent中下载apk文件
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e(TAG,"onHandleIntent Start");
        //初始化通知，用于显示下载进度
        mNotifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);
        String appName = getString(getApplicationInfo().labelRes);
        int icon = getApplicationInfo().icon;
        mBuilder.setContentTitle(appName).setSmallIcon(icon);

        String urlStr = intent.getStringExtra("apkUrl"); //从intent中取得apk下载路径

        InputStream in = null;
        FileOutputStream out = null;
        try {
            //建立下载连接
            URL url = new URL(urlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(false);
            urlConnection.setConnectTimeout(10 * 1000);
            urlConnection.setReadTimeout(10 * 1000);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.connect();

            //以文件流读取数据
            long bytetotal = urlConnection.getContentLength(); //取得文件长度
            long bytesum = 0;
            int byteread = 0;
            in = urlConnection.getInputStream();
            //File dir = StorageUtils.getCacheDirectory(this); //取得应用缓存目录
            String mSavePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "MajiangSet" +File.separator;
            File dir = new File(mSavePath);

            //先判断目录是否存在，不存在则创建
            isFolderExists(mSavePath);
            String apkName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());//取得apK文件名
            File apkFile = new File(dir, apkName);
            Log.e(TAG,mSavePath);
            out = new FileOutputStream(apkFile);
            byte[] buffer = new byte[BUFFER_SIZE];

            int limit = 0;
            int oldProgress = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
                int progress = (int) (bytesum * 100L / bytetotal);
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，则会造成界面卡顿
                if (progress != oldProgress) {
                    updateProgress(progress);
                }
                oldProgress = progress;
            }

            // 下载完成,调用installAPK开始安装文件
            installAPk(apkFile);
            Log.d("调试","download apk finish");
            mNotifyManager.cancel(NOTIFICATION_ID);

        } catch (Exception e) {
            Log.e(TAG, "download apk file error");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {

                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    /**
     * 实时更新下载进度条显示
     * @param progress
     */
    private void updateProgress(int progress) {
        //"正在下载:" + progress + "%"
        if (Build.VERSION.SDK_INT >= 26)
        {
            if (mNotifyChannel == null) {
                //创建 通知通道  channelid和channelname是必须的（自己命名就好）
                mNotifyChannel = new NotificationChannel("1",
                        "Channel1",NotificationManager.IMPORTANCE_HIGH);
                mNotifyChannel.enableLights(true);//是否在桌面icon右上角展示小红点
                mNotifyChannel.setLightColor(Color.GREEN);//小红点颜色
                mNotifyChannel.setShowBadge(true); //是否在久按桌面图标时显示此渠道的通知
                mNotifyManager.createNotificationChannel(mNotifyChannel);
            }
            int notificationId = 0x1234;
            Notification.Builder builder = new Notification.Builder(getApplicationContext(), "1");
            builder.setOnlyAlertOnce(true);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("正在下载新版本，请稍后...")
                    .setAutoCancel(true);
            if (progress > 0 && progress <= 100) {
                builder.setProgress(100, progress, false);
            } else {
                builder.setProgress(0, 0, false);
            }
/*
            builder.setContentIntent(progress >= 100 ? installAPk();:
                    PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
*/
            builder.setContentText(this.getString(R.string.dialog_choose_update_content, progress)).setProgress(100, progress, false);
            PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
            builder.setContentIntent(pendingintent);
            Notification notification = builder.build();
            mNotifyManager.notify(notificationId, notification);

        }
        else{
            mBuilder.setContentText(this.getString(R.string.dialog_choose_update_content, progress)).setProgress(100, progress, false);
            PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent.FLAG_CANCEL_CURRENT);
            mBuilder.setContentIntent(pendingintent);
            mNotifyManager.notify(NOTIFICATION_ID, mBuilder.build());
        }

    }

    /**
     * 进入安装
     *
     * @return
     */
    /*
    private PendingIntent getContentIntent() {

        mNotifyManager.cancelAll();

        //移除通知栏
        if (Build.VERSION.SDK_INT >= 26) {
           mNotifyManager.deleteNotificationChannel("1");
        }

        File saveFile = new File(mSavePath);
        Intent install = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            Uri apkUri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", saveFile);//在AndroidManifest中的android:authorities值
            install = new Intent(Intent.ACTION_VIEW);
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            install.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            install.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, install, PendingIntent.FLAG_UPDATE_CURRENT);
        startActivity(install);
        return pendingIntent;
    }
    /**
     * 调用系统安装程序安装下载好的apk
     * @param apkFile
     */

    private void installAPk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if(Build.VERSION.SDK_INT>=24){
            Log.e(TAG,"Version>=24");
            Uri apkUri = FileProvider.getUriForFile(this, "com.wzp.majiangset.fileprovider", apkFile);//在AndroidManifest中的android:authorities值
            Log.e(TAG,apkUri.toString());
            Log.e(TAG,apkFile.getAbsolutePath());

            Intent install = new Intent(Intent.ACTION_VIEW);

            install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            install.setDataAndType(apkUri, "application/vnd.android.package-archive");

            startActivity(install);

        }
        else{
            //如果没有设置SDCard写权限，或者没有sdcard,apk文件保存在内存中，需要授予权限才能安装
            Log.e(TAG,"Version<=24");
            try {
                String[] command = {"chmod", "777", apkFile.toString()}; //777代表权限 rwxrwxrwx
                ProcessBuilder builder = new ProcessBuilder(command);
                builder.start();
            } catch (IOException ignored) {
            }
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    private boolean isFolderExists(String strFolder)
    {
        File file = new File(strFolder);

        if (!file.exists())
        {
            if (file.mkdir())
            {
                return true;
            }
            else
                return false;
        }
        return true;
    }
}