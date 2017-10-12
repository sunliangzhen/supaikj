package com.toocms.dink5.mylibrary.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.toocms.dink5.mylibrary.commonutils.FileManager;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.toocms.dink5.mylibrary.commonutils.Settings;
import com.toocms.dink5.mylibrary.commonutils.ListUtils;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Zero
 * @date 2016/4/11 10:13
 */
public class WeApplication extends Application {
    private AppManager appManager;
    private Map<String, String> userInfo;
    private static WeApplication baseApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        this.appManager = AppManager.getInstance();
        start();
        baseApplication = this;
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    private void start() {
        Settings.displayWidth = getScreenWidth(this);
        Settings.displayHeight = getScreenHeight(this);
        Settings.cacheCompressPath = FileManager.getCompressFilePath();
        Settings.crashLogPath = FileManager.getCrashLogFilePath();
        Settings.voicePath = FileManager.getVoiceFilePath();
        (new File(Settings.cacheCompressPath)).mkdirs();
        (new File(Settings.crashLogPath)).mkdirs();
        (new File(Settings.voicePath)).mkdirs();
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}