package com.spkj.supai.appconfig;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.spkj.supai.R;
import com.spkj.supai.app.Constant;
import com.toocms.dink5.mylibrary.app.AppManager;

import org.xutils.x;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 */

public class DownloadUtils {


    private DownloadManager downloadManager;
    private Context mContext;
    private long downloadId;

    private ProgressDialog progressDialog;
    private DownloadChangeObserver downloadObserver;
    public static final int HANDLE_DOWNLOAD = 0x001;
    private ScheduledExecutorService scheduledExecutorService;

    public DownloadUtils(Context context) {
        this.mContext = context;
    }


    public void downloadAPK(String url) {

        downloadObserver = new DownloadChangeObserver();
        showProcess();
        registerContentObserver();
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        request.setMimeType("application/vnd.android.package-archive");
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
        request.setTitle("新版本" + x.app().getResources().getString(R.string.app_name));
        request.setDescription("正在下载" + x.app().getResources().getString(R.string.app_name));
        request.setVisibleInDownloadsUi(true);
        //设置下载的路径
//        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, " myApp.apk");
        request.setDestinationInExternalFilesDir(mContext.getApplicationContext(), "phoenix", "phoenix.apk");
        //获取DownloadManager
        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        downloadId = downloadManager.enqueue(request);
        registerBroadcast();
    }

    /**
     * 正在下载中的进度条
     */
    public void showProcess() {
        progressDialog = new ProgressDialog(AppManager.getInstance().getTopActivity());
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setMessage("正在下载...");
        progressDialog.show();
    }

    /**
     * 注册广播
     */
    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
        mContext.registerReceiver(receiver, intentFilter);
    }

    /**
     * 注册ContentObserver
     */
    private void registerContentObserver() {
        /** observer download change **/
        if (downloadObserver != null) {
            mContext.getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, downloadObserver);
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkStatus();
        }
    };

    //检查下载状态
    private void checkStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        //通过下载的id查找
        query.setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null && c.moveToFirst()) {
            int state = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (state) {
                //下载暂停
                case DownloadManager.STATUS_PAUSED:
                    break;
                case DownloadManager.ERROR_CANNOT_RESUME:
                    //下载延迟
                case DownloadManager.STATUS_PENDING:
                    break;
                //正在下载
                case DownloadManager.STATUS_RUNNING:
                    break;
                //下载完成
                case DownloadManager.STATUS_SUCCESSFUL:
                    //下载完成安装APK
                    close();
                    progressDialog.setProgress(100);
                    unregisterBroadcast();
                    unregisterContentObserver();
                    Uri downloadFileUri = downloadManager.getUriForDownloadedFile(downloadId);
                    SPUtil.put(Constant.SP_DOWNLOAD_PATH, downloadFileUri.getPath());
                    installApk(downloadFileUri);

                    break;
                //下载失败
                case DownloadManager.STATUS_FAILED:
                    Toast.makeText(mContext, "下载失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        c.close();
    }


    /**
     * 安装APK
     *
     * @param apkPath 安装包的路径
     */
    public void installApk(Uri apkPath) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(apkPath, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    public Handler downLoadHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (HANDLE_DOWNLOAD == msg.what) {
                float i = (msg.arg1 / (float) msg.arg2);
                int k = (int) (i * 100);
                if (k >= 0) {
                    progressDialog.setProgress(k);
                }
            }
        }
    };

    /**
     * 关闭定时器，线程等操作
     */
    private void close() {
        if (scheduledExecutorService != null && !scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.shutdown();
        }

        if (downLoadHandler != null) {
            downLoadHandler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 注销广播
     */
    private void unregisterBroadcast() {
        if (receiver != null) {
            mContext.unregisterReceiver(receiver);
            receiver = null;
        }
    }

    /**
     * 注销ContentObserver
     */
    private void unregisterContentObserver() {
        if (downloadObserver != null) {
            mContext.getContentResolver().unregisterContentObserver(downloadObserver);
        }
    }

    private Runnable progressRunnable = new Runnable() {
        @Override
        public void run() {
            int[] bytesAndStatus = getBytesAndStatus(downloadId);
            downLoadHandler.sendMessage(downLoadHandler.obtainMessage(HANDLE_DOWNLOAD, bytesAndStatus[0], bytesAndStatus[1], bytesAndStatus[2]));
        }
    };

    private int[] getBytesAndStatus(long downloadId) {
        int[] bytesAndStatus = new int[]{
                -1, -1, 0
        };
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                //已经下载文件大小
                bytesAndStatus[0] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                //下载文件的总大小
                bytesAndStatus[1] = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                //下载状态
                bytesAndStatus[2] = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return bytesAndStatus;
    }

    private class DownloadChangeObserver extends ContentObserver {

        public DownloadChangeObserver() {
            super(downLoadHandler);
            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        }

        /**
         * 当所监听的Uri发生改变时，就会回调此方法
         *
         * @param selfChange 此值意义不大, 一般情况下该回调值false
         */
        @Override
        public void onChange(boolean selfChange) {
            scheduledExecutorService.scheduleAtFixedRate(progressRunnable, 0, 2, TimeUnit.SECONDS);
        }
    }


}
