package com.spkj.supai.s;

import android.Manifest;
import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.spkj.supai.R;
import com.tbruyelle.rxpermissions.RxPermissions;

import org.xutils.x;

import java.io.File;

import rx.functions.Action1;

/**
 * Created by ROYGEM-0830-1 on 2017/10/9.
 */

public class AppUpdateService extends Service {

    private BroadcastReceiver receiver;
    private DownloadManager dm;
    /**
     * 系统下载器分配的唯一下载任务id，可以通过这个id查询或者处理下载任务
     */
    private long enqueue;
    private String downloadUrl = "http://dakaapp.troila.com/download/daka.apk?v=3.0";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        downloadUrl = intent.getStringExtra("download_url");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                install(context);
                stopSelf();
            }
        };
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            RxPermissions.getInstance(this)
                    // 申请权限
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            if (granted) {
                                //请求成功
                                startDownload(downloadUrl);
                            } else {
                                // 请求失败回收当前服务
                                stopSelf();

                            }
                        }
                    });
        } else {
            startDownload(downloadUrl);
        }
        return Service.START_STICKY;
    }

    public static void install(Context context) {
        File file = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                , "myApp.apk");
        //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
        Uri apkUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            apkUri = FileProvider.getUriForFile(context, "com.yll520wcf.test.fileprovider", file);
        } else {
            apkUri = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }


    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void startDownload(String downUrl) {
        //获得系统下载器
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        //设置下载地址
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downUrl));
        //设置下载文件的类型
        request.setMimeType("application/vnd.android.package-archive");
        //设置下载存放的文件夹和文件名字
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "myApp.apk");
        //设置下载时或者下载完成时，通知栏是否显示
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle("新版本" + x.app().getResources().getString(R.string.app_name));
        request.setDescription("正在下载" + x.app().getResources().getString(R.string.app_name));
        request.setVisibleInDownloadsUi(true);
        //执行下载，并返回任务唯一id
        enqueue = dm.enqueue(request);
    }
}