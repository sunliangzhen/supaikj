package com.spkj.supai.appconfig;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.spkj.supai.R;
import com.spkj.supai.app.Constant;
import com.spkj.supai.s.AppUpdateService;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.toocms.dink5.mylibrary.app.AppManager;
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.net.ApiListener;
import com.toocms.dink5.mylibrary.net.ApiTool;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.sql.Timestamp;
import java.util.Map;

import rx.functions.Action1;


public class UpdateManager {

    /**
     * FoceUpdate        强制更新
     * BrowSerUpdate     浏览器更新
     * BackGroundUpdate  后台更新
     */
    public static final String FoceUpdate = "foceupdate";
    public static final String BrowSerUpdate = "browserupdate";
    public static final String BackGroundUpdate = "backgroundupdate";

    private static UpdateManager manager = new UpdateManager();
    private checkVersionListen checkVersionListen;

    /**
     * 检查更新
     *
     * @param url          效验url
     * @param hasHint      如果是最新版本是否显示提示
     * @param isFoceUpdate 是否强制更新
     */
    public static final void checkUpdate(String url, boolean hasHint, String isFoceUpdate) {
        manager.check(url, hasHint, isFoceUpdate);
    }

    public void setCheckVersionListen(checkVersionListen checkVersionListen) {
        this.checkVersionListen = checkVersionListen;
    }

    public interface checkVersionListen {
        void checkVerssion(String str);
    }

    public void check(String url, final boolean hasHint, final String updateType) {
        removeOldApk();
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("command", "config.app.update.check");
        params.addBodyParameter("pubsJson", getPubJson());
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, new ApiListener() {
            @Override
            public void onCancelled(Callback.CancelledException cex) {
                if (checkVersionListen != null) {
                    checkVersionListen.checkVerssion("0");
                }
            }

            @Override
            public void onComplete(RequestParams params, String result, String type) {
                Map<String, String> map = JSONUtils.parseKeyAndValueToMap(result);
                if (map.get("hasNewVersion").equals("1")) {
                    if (checkVersionListen != null) {
                        checkVersionListen.checkVerssion("1");
                    }
                    switch (updateType) {
                        case UpdateManager.FoceUpdate:
                            startDownload(map.get("downloadUrl"));
                            break;
                        case UpdateManager.BackGroundUpdate:
//                            showBuilder(result, "");
                            break;
                        case UpdateManager.BrowSerUpdate:
                            browerUpdate(map.get("downloadUrl"));
                            break;
                    }
                } else {
                    if (checkVersionListen != null) {
                        checkVersionListen.checkVerssion("0");
                    }
                    if (hasHint) Toast.makeText(x.app(), "已是最新版本", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Map<String, String> var1, RequestParams var2) {
                LogUtil.e(var1.get("message"));
                if (checkVersionListen != null) {
                    checkVersionListen.checkVerssion("0");
                }
            }

            @Override
            public void onExceptionType(Throwable var1, RequestParams params, String type) {
                if (checkVersionListen != null) {
                    checkVersionListen.checkVerssion("0");
                }
            }
        }, "");
    }


    public void update(String url, String updateType) {
        switch (updateType) {
            case UpdateManager.FoceUpdate:
                startDownload(url);
                break;
            case UpdateManager.BackGroundUpdate:
                showBuilder("", url);
                break;
            case UpdateManager.BrowSerUpdate:
                browerUpdate("url");
                break;
        }
    }

    public void browerUpdate(String url) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        AppManager.getInstance().getTopActivity().startActivity(intent);
    }

    public void showBuilder(String result, final String url) {
        final Map<String, String> map = JSONUtils.parseKeyAndValueToMap(result);
        View view = View.inflate(AppManager.getInstance().getTopActivity(), R.layout.dlg_update, null);
        TextView tv_content = (TextView) view.findViewById(R.id.update_description);
        TextView tv_no = (TextView) view.findViewById(R.id.buildeexti_tv_no);
        TextView tv_ok = (TextView) view.findViewById(R.id.builderexit_tv_ok);
        final Dialog dialog = new Dialog(AppManager.getInstance().getTopActivity(), R.style.dialog);
//        tv_content.setText(map.get("new_features"));
        if (map != null && !TextUtils.isEmpty(map.get("newVersionDescribe"))) {
            tv_content.setText(map.get("newVersionDescribe"));
        }
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent intent = new Intent(AppManager.getInstance().getTopActivity(), AppUpdateService.class);
                if (map != null && !TextUtils.isEmpty(map.get("downloadUrl"))) {
                    intent.putExtra("download_url", map.get("downloadUrl"));
                } else {
                    intent.putExtra("download_url", url);
                }
                AppManager.getInstance().getTopActivity().startService(intent);

            }
        });
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void startDownload(final String url) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions.getInstance(AppManager.getInstance().getTopActivity())
                    // 申请权限
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                downloadFile(url);
                            } else {
                            }
                        }
                    });
        } else {
            downloadFile(url);
        }
    }

    private void removeOldApk() {
        File fileName = new File(SPUtil.getString(Constant.SP_DOWNLOAD_PATH, ""));
        if (fileName != null && fileName.exists() && fileName.isFile()) {
            fileName.delete();
        }
    }

    // 下载文件
    private synchronized void downloadFile(String url) {
        DownloadUtils downloadUtils = new DownloadUtils(AppManager.getInstance().getTopActivity());
        downloadUtils.downloadAPK(url);

    }

    // 获取版本号
    private String getVersionCode() {
        try {
            PackageInfo packageInfo = x.app().getPackageManager().getPackageInfo(x.app().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String getPubJson() {
        PubsJson pubsJson = new PubsJson();
        pubsJson.setImei(getIMSI());
        pubsJson.setOs("Android");
        pubsJson.setOsVersion(android.os.Build.DISPLAY);
        pubsJson.setAppVersion(getVersionCode());
        pubsJson.setTimestamp(getTimesTamp());
        Gson gson = new Gson();
        String s1 = gson.toJson(pubsJson);
        return s1;
    }

    /**
     * 获取手机IMSI号
     */
    public String getIMSI() {
        TelephonyManager mTelephonyMgr = (TelephonyManager) x.app().getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();
        return imsi;
    }

    public String getTimesTamp() {
        Timestamp now = new Timestamp(System.currentTimeMillis());//获取系统当前时间
        return now.toString();
    }
}
