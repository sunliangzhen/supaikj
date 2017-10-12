package com.spkj.supai.interfaces;

import android.content.Context;

import com.toocms.dink5.mylibrary.app.AppConfig;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.toocms.dink5.mylibrary.net.ApiListener;
import com.toocms.dink5.mylibrary.net.ApiTool;

import org.xutils.http.RequestParams;


/**
 */
public class Lar {

    private String module;

    public Lar() {
        module = this.getClass().getSimpleName().toLowerCase(); // 模块名
    }


    /**
     * 获取验证码
     *
     * @param apiListener
     */
    public void setCode(Context context, String phone, String type, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.verify.code.send");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("type", type);
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.verify.code.send");
    }

    /**
     * 检查验证码
     *
     * @param apiListener
     */
    public void checkCode(Context context, String phone, String verifyCode, String type, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.verify.code.check");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("verifyCode", verifyCode);
        params.addBodyParameter("type", type);
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.verify.code.check");
    }

    /**
     * 注册
     *
     * @param apiListener
     */
    public void register(Context context, String phone, String password, String successToken, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.register");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        params.addBodyParameter("successToken", successToken);
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.register");
    }

    /**
     * 重置登录密码
     *
     * @param apiListener
     */
    public void resetPass(Context context, String phone, String password, String successToken, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.setting.loginpassword");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        params.addBodyParameter("successToken", successToken);

        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.setting.loginpassword");
    }

    /**
     * 普通登录
     *
     * @param apiListener
     */
    public void login(Context context, String phone, String password, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.login");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", password);
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.login");
    }

    /**
     * 短信登录
     *
     * @param apiListener
     */
    public void loginMsg(Context context, String phone, String successToken, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.login");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("successToken", successToken);
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.login");
    }

    /**
     * 微信登录
     *
     * @param apiListener
     */
    public void loginWei(Context context, String openId, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.login");
        params.addBodyParameter("openId", openId);
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.login");
    }

    /**
     * 跟换头像和昵称
     *
     * @param apiListener
     */
    public void updateUser(Context context, String type, String content, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.info.update");
        params.addBodyParameter(type, content);
        params.addBodyParameter("userId", PreferencesUtils.getString(context, "userId", ""));
        params.addBodyParameter("token", PreferencesUtils.getString(context, "token", ""));
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.info.update");
    }

    /**
     * 更换手机号
     *
     * @param apiListener
     */
    public void updatePhone(Context context, String phone, String successToken, String verifyCode, ApiListener apiListener) {

        RequestParams params = new RequestParams(AppConfig.BASE_URL);
        params.addBodyParameter("command", "user.setting.phone");
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("successToken", successToken);
        params.addBodyParameter("verifyCode", verifyCode);
        params.addBodyParameter("userId", PreferencesUtils.getString(context, "userId", ""));
        params.addBodyParameter("token", PreferencesUtils.getString(context, "token", ""));
        ApiTool apiTool = new ApiTool();
        apiTool.postApi(params, apiListener, "user.setting.phone");
    }


}
