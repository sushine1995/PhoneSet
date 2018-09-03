package com.wzp.majiangset.activity.update;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.wzp.majiangset.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by su823 on 18/6/27.
 */

public class CheckVersionInfoTask extends AsyncTask<Void,Void,String> {
    private static final String TAG = "CheckVersionInfoTask";
    private ProgressDialog dialog;
    private Context mContext;
    private boolean mShowProgressDialog;
    private static final String VERSION_INFO_URL = "https://raw.githubusercontent.com/sushine1995/MajiangSet/master/update.json";

    public CheckVersionInfoTask(Context context, boolean showProgressDialog){
        this.mContext = context;
        this.mShowProgressDialog = showProgressDialog;
    }

    //初始化显示Dialog
    protected void onPreExecute(){
        if (mShowProgressDialog){
            dialog = new ProgressDialog(mContext);
            dialog.setMessage("正在检测版本");
            dialog.show();
        }
    }
    //后台-子线程检查服务器中的版本信息


    //在后台任务(子线程)中检查服务器的版本信息
    @Override
    protected String doInBackground(Void... params) {
        return getVersionInfo(VERSION_INFO_URL);
    }


    //后台任务执行完毕后，解除Dialog并且解析return返回的结果
    @Override
    protected void onPostExecute(String result) {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }

        if (!TextUtils.isEmpty(result)) {
            parseJson(result);
        }
    }

    /**
     * 从服务器取得版本信息
     * {
     "url":"https://raw.githubusercontent.com/sushine1995/MajiangSet/master/Majiang.apk",
     "versionCode":2,
     "updateMessage":"[1]新增在线更新功能<br/>"
     }
     * @return
     */
    public  String getVersionInfoUrl(String urlStr){
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        BufferedReader buffer = null;
        String result = null;
        try{
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            inputStream = urlConnection.getInputStream();
            buffer = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine())!=null){
                stringBuilder.append(line);
            }
            result = stringBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "http post error");
        }finally {
            if(buffer!=null)
                try{
                    buffer.close();
                }catch (IOException ignored){

                }
            if (inputStream !=null)
                try {
                    inputStream.close();
                }catch (IOException ignored){

                }
            if (urlConnection !=null)
                urlConnection.disconnect();
        }
        return result;
    }
    /**
     * 从服务器取得版本信息
     * {
     "url":"https://raw.githubusercontent.com/sushine1995/MajiangSet/master/Majiang.apk",
     "versionCode":2,
     "updateMessage":"[1]新增在线更新功能<br/>"
     }
     * @return
     */
    public String getVersionInfo(String urlStr){
        HttpURLConnection uRLConnection = null;
        InputStream is = null;
        BufferedReader buffer = null;
        String result = null;
        try {
            URL url = new URL(urlStr);
            uRLConnection = (HttpURLConnection) url.openConnection();
            uRLConnection.setRequestMethod("GET");
            is = uRLConnection.getInputStream();
            buffer = new BufferedReader(new InputStreamReader(is));
            StringBuilder strBuilder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                strBuilder.append(line);
            }
            result = strBuilder.toString();
        } catch (Exception e) {
            Log.e(TAG, "http post error");
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException ignored) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignored) {

                }
            }
            if (uRLConnection != null) {
                uRLConnection.disconnect();
            }
        }
        return result;
    }
    /**
     *
     * @param result
     */
    private void parseJson(String result) {
        try {
            JSONObject obj = new JSONObject(result);
            String apkUrl = obj.getString("url");                 //APK下载路径
            String updateMessage = obj.getString("updateMessage");//版本更新说明
            int apkCode = obj.getInt("versionCode");              //新版APK对于的版本号

            //取得已经安装在手机的APP的版本号 versionCode
            int versionCode = getCurrentVersionCode();
            //String versionName = getCurrentVersionName();
            //对比版本号判断是否需要更新
            if (apkCode > versionCode) {

                showDialog(updateMessage, apkUrl);

            } else if (mShowProgressDialog) {
                Toast.makeText(mContext, mContext.getString(R.string.there_no_new_version), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            Log.e(TAG, "parse json error");
        }
    }
    public int getCurrentVersionCode() {

        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return 0;
    }

    public  String getCurrentVersionName() {


        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return null;
        /*
        String version = null;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        */
        //return version;

    }

    /**
     * 显示对话框提示用户有新版本，并且让用户选择是否更新版本
     * @param content
     * @param downloadUrl
     */
    public void showDialog(String content, final String downloadUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        int versionCode = getCurrentVersionCode();
        String versionName = getCurrentVersionName();
        builder.setTitle("发现新版本,当前版本:"+ versionName );
        builder.setMessage(Html.fromHtml(content))
                .setPositiveButton(R.string.dialog_btn_confirm_download, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //下载apk文件
                        goToDownloadApk(downloadUrl);
                    }
                })
                .setNegativeButton(R.string.dialog_btn_cancel_download, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        AlertDialog dialog = builder.create();
        //点击对话框外面,对话框不消失
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //TODO
    //ProgressDialog下载的进度框
    /*
    public void progressDiaLog(){

        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setTitle("温馨提示");
        dialog.setMessage("正在下载...");
        dialog.setProgressStyle(dialog.STYLE_HORIZONTAL);
        new Thread(){
            @Override
            public void run() {
                super.run();
                while (true){
                    if (bar < 100){
                        bar++;
                        dialog.setProgress(bar);
                        try {
                            sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }else{
                        dialog.dismiss();
                        bar = 0;
                        break;
                    }
                }
            }
        }.start();
        dialog.show();
    }
*/

    /**
     * 用intent启用DownloadService服务去下载AKP文件
     * @param downloadUrl
     */
    private void goToDownloadApk(String downloadUrl) {
        Toast.makeText(mContext, "启动下载服务", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, DownloadApkService.class);
        intent.putExtra("apkUrl", downloadUrl);
        mContext.startService(intent);
    }
}
