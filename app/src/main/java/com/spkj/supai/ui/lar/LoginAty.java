package com.spkj.supai.ui.lar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.spkj.supai.MainActivity;
import com.spkj.supai.R;
import com.spkj.supai.interfaces.Lar;
import com.spkj.supai.utils.UiUtils;
import com.toocms.dink5.mylibrary.app.AppManager;
import com.toocms.dink5.mylibrary.app.Config;
import com.toocms.dink5.mylibrary.base.BaseActivity;
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.toocms.dink5.mylibrary.commonwidget.StatusBarCompat;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by aa on 2017/6/23.
 */

public class LoginAty extends BaseActivity {


    @ViewInject(R.id.edit_phone)
    public EditText edit_phone;
    @ViewInject(R.id.edit_pass)
    public EditText edit_pass;

    private Lar lar;
    private ProgressDialog dialog;


    @Override
    public int getLayoutId() {
        return R.layout.aty_login;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        lar = new Lar();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, 0xff746F69);
    }

    @Event(value = {R.id.tv_login_forgetpass, R.id.tv_login_msg, R.id.imgv_back, R.id.tv_login_register, R.id.imgv_login_wei,
            R.id.tv_login})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.tv_login_forgetpass:
                Bundle bundle = new Bundle();
                bundle.putString("type", "forget");
                startActivity(ForgetPassAty.class, bundle);
                break;
            case R.id.tv_login_msg:
                startActivity(LoginMsgAty.class);
                break;
            case R.id.tv_login_register:
                Bundle bundle2 = new Bundle();
                bundle2.putString("type", "register");
                startActivity(ForgetPassAty.class, bundle2);
                break;
            case R.id.imgv_back:
                finish();
                break;
            case R.id.imgv_login_wei:
                if (UiUtils.isAvilible("com.tencent.mm")) {
//                    dialog.setMessage("正在进行微信登录");
//                    SocializeUtils.safeShowDialog(dialog);
                    UMShareAPI.get(this).getPlatformInfo(this, SHARE_MEDIA.WEIXIN, authListener);
                } else {
                    showShortToast("未安装微信");
                }
//                startActivity(LoginWeiAty.class);
                break;
            case R.id.tv_login:

                if (!UiUtils.isMobile(edit_phone.getText().toString())) {
                    showShortToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(edit_pass.getText().toString())) {
                    showShortToast("密码不能为空");
                    return;
                }
                startProgressDialog();
                lar.login(this, edit_phone.getText().toString(), edit_pass.getText().toString(), this);
                break;
        }
    }

    @Override
    public void onComplete(RequestParams var1, String var2, String type) {
        super.onComplete(var1, var2, type);
        stopProgressDialog();
        SocializeUtils.safeCloseDialog(dialog);
        if (type.equals("user.login")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            showShortToast(map.get("errorMsg"));
            if (map.get("responseCode").equals("0")) {
                Config.setLoginState(true);
                Map<String, String> user = JSONUtils.parseKeyAndValueToMap(map.get("user"));
                PreferencesUtils.putString(this, "token", map.get("token"));
                PreferencesUtils.putString(this, "userId", user.get("userId"));
                PreferencesUtils.putString(this, "phone", user.get("phone"));
                PreferencesUtils.putString(this, "headImage", user.get("headImage"));
                PreferencesUtils.putString(this, "nickName", user.get("nickName"));
//                PreferencesUtils.getString(this, "deposit", user.get("deposit"));
//                PreferencesUtils.getString(this, "isDeposit", user.get("isDeposit"));
//                PreferencesUtils.getString(this, "goldCoin", user.get("goldCoin"));
                AppManager.getInstance().killActivity(ForgetPassAty.class);
                finish();
            }
        }
    }

    UMAuthListener authListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            dialog.setMessage("正在进行微信登录");
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            dialog.setMessage("验证手机号");
            UMShareAPI.get(getApplicationContext()).deleteOauth(LoginAty.this, SHARE_MEDIA.WEIXIN, null);
//            String temp = "";
//            for (String key : data.keySet()) {
//                temp = temp + key + " : " + data.get(key) + "\n";
//            }
            lar.loginWei(LoginAty.this, data.get("openid"), LoginAty.this);
//            WeiXinLogin(data);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SocializeUtils.safeCloseDialog(dialog);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
