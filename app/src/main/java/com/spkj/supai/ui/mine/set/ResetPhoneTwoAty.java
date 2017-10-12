package com.spkj.supai.ui.mine.set;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.interfaces.Lar;
import com.spkj.supai.utils.UiUtils;
import com.toocms.dink5.mylibrary.app.AppCountdown;
import com.toocms.dink5.mylibrary.app.AppManager;
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by aa on 2017/7/5.
 */

public class ResetPhoneTwoAty extends BasAty {


    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.et_password)
    private EditText et_password;
    @ViewInject(R.id.et_password2)
    private EditText et_password2;
    @ViewInject(R.id.tv_get)
    private TextView tv_get;

    private String successToken;
    private String phone;
    private AppCountdown appCountdown;
    private Lar lar;

    @Override
    public int getLayoutId() {
        return R.layout.aty_rephone_two;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        if (getIntent().hasExtra("successToken")) {
            successToken = getIntent().getStringExtra("successToken");
        }
        if (getIntent().hasExtra("phone")) {
            phone = getIntent().getStringExtra("phone");
        }
        lar = new Lar();
        appCountdown = AppCountdown.getInstance();
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appCountdown.play(tv_get);
        tv_title.setText("修改手机号");
        tv_right.setText("完成");
        tv_right.setVisibility(View.VISIBLE);
    }

    @Event(value = {R.id.relay_back, R.id.tv_right, R.id.tv_get})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
            case R.id.tv_right:
                if (!UiUtils.isMobile(et_password.getText().toString())) {
                    showShortToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(et_password2.getText().toString())) {
                    showShortToast("验证码不能为空");
                    return;
                }
                startProgressDialog();
                lar.updatePhone(this, et_password.getText().toString(), successToken, et_password2.getText().toString(), this);
                break;
            case R.id.tv_get:
                if (!UiUtils.isMobile(et_password.getText().toString())) {
                    showShortToast("请输入正确的手机号");
                    return;
                }
                startProgressDialog();
                lar.setCode(this, et_password.getText().toString(), "2", this);
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
        if (type.equals("user.setting.phone")) {
            appCountdown.reSet();
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            showShortToast(map.get("errorMsg"));
            if (map.get("responseCode").equals("0")) {
                PreferencesUtils.putString(this, "phone", et_password.getText().toString());
                AppManager.getInstance().killActivity(ResetPhoneAty.class);
                finish();
            }
        }
    }


}
