package com.spkj.supai.ui.lar;

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
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.commonwidget.StatusBarCompat;

import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by aa on 2017/6/29.
 */

public class ForgetPassAty extends BasAty {

    @ViewInject(R.id.tv_title)
    public TextView tv_title;
    @ViewInject(R.id.tv_getcode)
    public TextView tv_getcode;
    @ViewInject(R.id.edit_phone)
    public EditText edit_phone;
    @ViewInject(R.id.edit_yzm)
    public EditText edit_yzm;

    private AppCountdown appCountdown;
    private String type;

    private Lar lar;

    @Override
    public int getLayoutId() {
        return R.layout.aty_forgetpass;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        lar = new Lar();
        appCountdown = AppCountdown.getInstance();
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, 0xff746F69);
        appCountdown.play(tv_getcode);

        if (type.equals("forget")) {
            tv_title.setText("重置登录密码");
        } else {
            tv_title.setText("注册");
        }
    }


    @Event(value = {R.id.tv_next, R.id.tv_getcode, R.id.relay_back})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.tv_next:
                if (!UiUtils.isMobile(edit_phone.getText().toString())) {
                    showShortToast("请输入正确的手机号");
                    return;
                }
                if (TextUtils.isEmpty(edit_yzm.getText().toString())) {
                    showShortToast("验证码不能为空");
                    return;
                }
                startProgressDialog();
                if (type.equals("forget")) {
                    lar.checkCode(this, edit_phone.getText().toString(), edit_yzm.getText().toString(), "3", this);
                } else {
                    lar.checkCode(this, edit_phone.getText().toString(), edit_yzm.getText().toString(), "1", this);
                }
                break;
            case R.id.tv_getcode:
                if (!UiUtils.isMobile(edit_phone.getText().toString())) {
                    showShortToast("请输入正确的手机号");
                    return;
                }
                startProgressDialog();
                if (type.equals("forget")) {
                    lar.setCode(this, edit_phone.getText().toString(), "3", this);
                } else {
                    lar.setCode(this, edit_phone.getText().toString(), "1", this);
                }
                break;
            case R.id.relay_back:
                finish();
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
            if (map.get("responseCode").equals("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("type", this.type);
                bundle.putString("successToken", map.get("successToken"));
                bundle.putString("phone", edit_phone.getText().toString());
                startActivity(ForgetPassTwoAty.class, bundle);
            } else {
                showShortToast(map.get("errorMsg"));
            }
        }
    }
}
