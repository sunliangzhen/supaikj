package com.spkj.supai.ui.lar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.interfaces.Lar;
import com.spkj.supai.utils.UiUtils;
import com.toocms.dink5.mylibrary.app.AppCountdown;
import com.toocms.dink5.mylibrary.app.AppManager;
import com.toocms.dink5.mylibrary.app.Config;
import com.toocms.dink5.mylibrary.base.BaseActivity;
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.toocms.dink5.mylibrary.commonwidget.StatusBarCompat;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by aa on 2017/6/23.
 */

public class LoginMsgAty extends BaseActivity {

    @ViewInject(R.id.edit_phone)
    public EditText edit_phone;
    @ViewInject(R.id.edit_code)
    public EditText edit_code;
    @ViewInject(R.id.tv_getcode)
    public TextView tv_getcode;

    private Lar lar;
    private AppCountdown appCountdown;


    @Override
    public int getLayoutId() {
        return R.layout.aty_login_msg;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        lar = new Lar();
        appCountdown = AppCountdown.getInstance();
    }

    @Override
    public void requestData() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, 0xff746F69);
        appCountdown.play(tv_getcode);
    }

    @Event(value = {R.id.relay_back, R.id.imgv_login_wei, R.id.tv_login, R.id.tv_getcode})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
            case R.id.imgv_login_wei:
                startActivity(LoginWeiAty.class);
                break;
            case R.id.tv_login:
                if (!UiUtils.isMobile(edit_phone.getText().toString())) {
                    showShortToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(edit_code.getText().toString())) {
                    showShortToast("验证码不能为空");
                    return;
                }
                startProgressDialog();
                lar.checkCode(this, edit_phone.getText().toString(), edit_code.getText().toString(), "5", this);
                break;
            case R.id.tv_getcode:
                if (!UiUtils.isMobile(edit_phone.getText().toString())) {
                    showShortToast("请输入正确的手机号");
                    return;
                }
                startProgressDialog();
                lar.setCode(this, edit_phone.getText().toString(), "5", this);
                break;
        }
    }

    @Override
    public void onComplete(RequestParams var1, String var2, String type) {
        super.onComplete(var1, var2, type);
        stopProgressDialog();
        if (type.equals("user.verify.code.send")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            showShortToast(map.get("errorMsg"));
            if (map.get("responseCode").equals("0")) {
                appCountdown.mStart();
            }
        }
        if (type.equals("user.verify.code.check")) {
            appCountdown.reSet();
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            showShortToast(map.get("errorMsg"));
            if (map.get("responseCode").equals("0")) {
                Config.setLoginState(true);
//                Map<String, String> user = JSONUtils.parseKeyAndValueToMap(map.get("user"));
//                PreferencesUtils.getString(this, "token", map.get("token"));
//                PreferencesUtils.getString(this, "userId", user.get("userId"));
//                PreferencesUtils.getString(this, "phone", user.get("phone"));
//                PreferencesUtils.getString(this, "headImage", user.get("headImage"));
//                PreferencesUtils.getString(this, "nickName", user.get("nickName"));
                AppManager.getInstance().killActivity(LoginAty.class);
                finish();
            }
        }
    }
}
