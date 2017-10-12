package com.spkj.supai.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.spkj.supai.app.SpkjApplication;
import com.toocms.dink5.mylibrary.app.WeApplication;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class UiUtils {

    public static Context getContext() {
        return WeApplication.getAppContext();
    }

    public static boolean isAvilible(String packageName) {
        PackageManager packageManager = getContext().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        return packageNames.contains(packageName);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        System.out.println(".......haha" + getContext() == null ? true : false);
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }


    public static String getMoney(String s) {

        if ("".equals(s)) return "";
        if ("".equals(s)) {
            return "";
        }
        double money = Double.valueOf(s).doubleValue();

        String newMoney = money / 10000 + "";

        int index = newMoney.indexOf(".");

        String decimal = newMoney.substring(index, newMoney.length());      //获取点后面的数

        if (Double.parseDouble(decimal) > 0) {                              //判断点后面的数是否大于0
            return newMoney;

        } else {
            return newMoney.substring(0, newMoney.indexOf("."));
        }
    }

    public static String getPhone(String phone) {
        String substring1 = phone.substring(0, 3);
        String substring2 = phone.substring(7);
        String newPhone = substring1.concat("****").concat(substring2);
        return newPhone;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
}
