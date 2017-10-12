package com.toocms.dink5.mylibrary.app;


import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;

/**
 * Created by aa on 2017/5/4.
 */

public class Config {
    public static boolean isLogin() {
        return PreferencesUtils.getBoolean(AppManager.getInstance().getTopActivity(), "PREF_KEY_LOGIN_STATE");
    }

    public static void setLoginState(boolean isLogin) {
        PreferencesUtils.putBoolean(AppManager.getInstance().getTopActivity(), "PREF_KEY_LOGIN_STATE", isLogin);
    }
}
