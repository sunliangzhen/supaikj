package com.spkj.supai.appconfig;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.spkj.supai.R;
import com.toocms.dink5.mylibrary.app.AppManager;
import com.toocms.dink5.mylibrary.commonutils.FileManager;

import org.xutils.x;

import java.io.File;

/**
 */
public class DownLoadService extends Service {
    String download_url;
    private int requestCode = (int) SystemClock.uptimeMillis();
    File mFile;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String path = FileManager.getCompressFilePath() + x.app().getResources().getString(R.string.app_name) + ".apk";
        mFile = new File(path);
        download_url = intent.getStringExtra("download_url");
        Intent intent_noti = new Intent();
        intent_noti.setAction(Intent.ACTION_VIEW);
//        intent_noti.setDataAndType(Uri.fromFile(mFile), "application/vnd.android.package-archive");
        PendingIntent rightPendIntent = PendingIntent.getActivity(this,
                requestCode, intent_noti, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = R.drawable.ic_launcher;
        String ticker = "正在更新" + x.app().getResources().getString(R.string.app_name);
        NotifyUtil notify7 = new NotifyUtil(this, 7);
        notify7.notify_progress(rightPendIntent, smallIcon, ticker, x.app().getResources().getString(R.string.app_name) + "升级程序", "正在下载中",
                false, false, false, download_url, new NotifyUtil.DownLoadListener() {
                    @Override
                    public void OnSuccess(File file) {
                        DownLoadService.this.stopSelf();
                        install(file);
                    }

                    @Override
                    public void onFailure(Throwable t, int errorNo, String strMsg) {

                    }
                });
        return super.onStartCommand(intent, flags, startId);
    }

    public static void install(File file) {
        Uri apkUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(AppManager.getInstance().getTopActivity(), "com.yll520wcf.test.fileprovider", file);
        } else {
            apkUri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        AppManager.getInstance().getTopActivity().startActivity(intent);
    }
}
