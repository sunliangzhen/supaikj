package com.spkj.supai.ui.lar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.interfaces.Lar;
import com.toocms.dink5.mylibrary.app.AppCountdown;
import com.toocms.dink5.mylibrary.app.AppManager;
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;
import com.toocms.dink5.mylibrary.commonwidget.StatusBarCompat;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by aa on 2017/6/29.
 */

public class ForgetPassTwoAty extends BasAty {


    @ViewInject(R.id.tv_title)
    public TextView tv_title;
    @ViewInject(R.id.edit_pass)
    public EditText edit_pass;
    @ViewInject(R.id.edit_pass2)
    public EditText edit_pass2;
    private String type;
    private String successToken;
    private String phone;

    private Lar lar;

    @Override
    public int getLayoutId() {
        return R.layout.aty_forgetpasstwo;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("type");
        if (getIntent().hasExtra("successToken")) {
            successToken = getIntent().getStringExtra("successToken");
        }
        if (getIntent().hasExtra("phone")) {
            phone = getIntent().getStringExtra("phone");
        }
        lar = new Lar();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, 0xff746F69);
        if (type.equals("forget")) {
            tv_title.setText("重置登录密码");
        } else {
            tv_title.setText("注册");
        }
    }

    @Event(value = {R.id.relay_back, R.id.tv_next})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
            case R.id.tv_next:
                if (TextUtils.isEmpty(edit_pass.getText().toString())) {
                    showShortToast("密码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(edit_pass2.getText().toString())) {
                    showShortToast("密码不能为空");
                    return;
                }
                if (!edit_pass2.getText().toString().equals(edit_pass.getText().toString())) {
                    showShortToast("两次输入不一致");
                    return;
                }
                startProgressDialog();
                if (type.equals("forget")) {
                    lar.resetPass(this, phone, edit_pass.getText().toString(), successToken, this);
                } else {
                    lar.register(this, phone, edit_pass.getText().toString(), successToken, this);
                }
                break;
        }
    }

    @Override
    public void onComplete(RequestParams var1, String var2, String type) {
        super.onComplete(var1, var2, type);
        if (type.equals("user.register")) {
            stopProgressDialog();
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            showShortToast(map.get("errorMsg"));
            if (map.get("responseCode").equals("0")) {
                AppManager.getInstance().killActivity(ForgetPassAty.class);
                finish();
            }
        }
        if (type.equals("user.setting.loginpassword")) {
            stopProgressDialog();
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            showShortToast(map.get("errorMsg"));
            if (map.get("responseCode").equals("0")) {
                AppManager.getInstance().killActivity(ForgetPassAty.class);
                finish();
            }
        }
    }
}
