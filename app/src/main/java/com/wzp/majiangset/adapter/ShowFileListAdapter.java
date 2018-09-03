package com.wzp.majiangset.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.wzp.majiangset.R;
import com.wzp.majiangset.activity.ReceiveSendFileActivity;
import com.wzp.majiangset.activity.base.BaseActivity;
import com.wzp.majiangset.constant.RemoteFileSource;
import com.wzp.majiangset.entity.PlayMethodParameter;
import com.wzp.majiangset.widget.MyApplication;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static android.app.Activity.RESULT_OK;


public class ShowFileListAdapter extends BaseAdapter {

    private Context context;
    private List<File> dataList;


    public ShowFileListAdapter(Context context, List<File> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final File file = (File) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem_show_file, parent, false);

            holder = new ViewHolder();
            holder.tvFilename = (TextView) convertView.findViewById(R.id.tv_filename);
            holder.tvRead = (TextView) convertView.findViewById(R.id.tv_read);
            holder.tvSend = (TextView) convertView.findViewById(R.id.tv_send);
            holder.tvDelete = (TextView) convertView.findViewById(R.id.tv_delete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String filename = file.getName().substring(0, file.getName().lastIndexOf('.'));
        holder.tvFilename.setText(filename);
        holder.tvRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (readFile(file)) {
                    ((BaseActivity) context).showToast("读取成功");
                    ((BaseActivity) context).setResult(RESULT_OK);
                    ((BaseActivity) context).finish();
                } else {
                    ((BaseActivity) context).showToast("文件异常，读取失败");
                }
            }
        });
        holder.tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ReceiveSendFileActivity) context).getRemoteSource()
                        == RemoteFileSource.QQ) {
                    // QQ
                    if (!isWeixinAvilible(context)) {
                        ((BaseActivity) context).showToast("请先安装QQ！");
                        return;
                    }
                } else {
                    // 微信
                    if (!isWeixinAvilible(context)) {
                        ((BaseActivity) context).showToast("请先安装微信！");
                        return;
                    }
                }

                Intent localIntent = new Intent("android.intent.action.SEND");

                if (((ReceiveSendFileActivity) context).getRemoteSource()
                        == RemoteFileSource.QQ) {
                    // QQ
                    localIntent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
                } else {
                    // 微信
                    localIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
                }
                localIntent.putExtra("android.intent.extra.TEXT", file.getName());
                Uri uri;
                // 判断版本大于等于7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    uri = FileProvider.getUriForFile(context, "com.wzp.majiang.fileprovider", file);
                } else {
                    uri = Uri.fromFile(file);
                }
                localIntent.putExtra("android.intent.extra.STREAM", uri);
                localIntent.setType("*/*");

                context.startActivity(localIntent);
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("注意")
                        .setMessage("是否确定删除" + filename)
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                File fileDel = dataList.get(position);
                                if (fileDel.delete()) {
                                    ((BaseActivity) context).showToast("删除成功");
                                }
                                dataList.remove(position);
                                notifyDataSetChanged();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        return convertView;
    }

    private boolean readFile(File file) {
        try {
            DocumentBuilderFactory factory  = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);

            Element root = document.getDocumentElement();
            NodeList list = root.getElementsByTagName("string");

            List<String> strList = new ArrayList<>();
            for (int i = 0; i < list.getLength(); i++) {
                Element playMethod =  (Element) list.item(i);
                Log.d("ShowFileListAdapter", playMethod.getAttribute("name"));
                strList.add(playMethod.getTextContent());
            }

            for (int i = 0; i < 3; i++) {
                MyApplication.getParameterList().set(i, JSON.parseObject(strList.get(i), PlayMethodParameter.class));
            }

            return true;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        // 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        // 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    class ViewHolder {
        TextView tvFilename;
        TextView tvRead;
        TextView tvSend;
        TextView tvDelete;
    }
}
