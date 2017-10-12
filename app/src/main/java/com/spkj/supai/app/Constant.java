package com.spkj.supai.app;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by wanghao on 2017/3/17.
 */
public class Constant {
    public static int WIDTH_SCREEN; //屏幕宽度
    public static int HEIGHT_SCREEN; //屏幕高度
    public static final String USER_HEAD = "user_head";
    public static final int REQUEST_TAKE_PHOTO = 0;
    public static final int REQUEST_PHOTO_ZOOM = 1;
    public static final int REQUEST_PHOTO_RESULT = 2;
    public static final int LOCATIONSUCCESS = 1;
    public static boolean MY_INFO_STATUS = false;//我的界面--个人信息
    public static boolean MY_INFO_STATUS_set = false;//我的界面--个人信息
    public static boolean LOGIN_STATUS = false;

    public static boolean ISDEPOSIT = false;
    public static String RECEIVEADDRESSID;
    //拍品状态
    public static final int DOING = 0;
    public static final int IMMEDIATELY = 1;
    public static final int ALREADY = 2;
    public static final int ABANDON = 3;

    public static final int daizhifu = 0;
    public static final int daifahuo = 1;
    public static final int daishouhuo = 2;
    public static final int daishiyong = 3;
    public static final int yiwancheng = 4;
    public static final int yishixiao = 5;
    public static final int jingpaizhong = 7;
    public static final int zhifuzhong = 8;

    public static final String SP_NAME = "Phoenix";
    public static final String SP_DOWNLOAD_PATH = "download.path";
    public static  boolean scorll_loadmore=false;

    public static void obtainScreenParams(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        Constant.WIDTH_SCREEN = metrics.widthPixels;
        Constant.HEIGHT_SCREEN = metrics.heightPixels;
    }
}
