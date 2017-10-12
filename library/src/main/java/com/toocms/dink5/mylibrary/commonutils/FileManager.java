package com.toocms.dink5.mylibrary.commonutils;

import android.os.Environment;

import org.xutils.x;

import java.io.File;

/**
 * @author Zero
 * @date 2016/4/24 18:10
 */
public class FileManager {
    public static final String SDCARD_FOLDER_NAME = "spkj";
    public static final String DISK_CACHE_DIR_NAME = "spkj_Bitmap_Cache";
    public static final String THUMB_CACHE = "spkj_Bitmap_Cache_Thumb";

    public FileManager() {
    }

    public static String getSaveFilePath() {
        return getRootFilePath() + "spkj" + File.separator + "picture_cache" + File.separator;
    }

    public static String getCompressFilePath() {
        return getRootFilePath() + "spkj" + File.separator + "compress_cache" + File.separator;
    }

    public static String getCrashLogFilePath() {
        return getRootFilePath() + "spkj" + File.separator + "crash_log" + File.separator;
    }

    public static String getVoiceFilePath() {
        return getRootFilePath() + "spkj" + File.separator + "voice" + File.separator;
    }

    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals("mounted");
    }

    public static String getRootFilePath() {
        return hasSDCard() ? Environment.getExternalStorageDirectory().getAbsolutePath() + "/" : Environment.getDataDirectory().getAbsolutePath() + "/data/";
    }

    public static void clearMemCache() {
        x.image().clearMemCache();
    }

    public static void clearCacheFiles() {
        x.image().clearCacheFiles();
    }
}