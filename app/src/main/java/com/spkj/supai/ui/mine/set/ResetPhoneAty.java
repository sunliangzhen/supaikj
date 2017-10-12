package com.spkj.supai.ui.mine.set;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.interfaces.Lar;
import com.spkj.supai.ui.lar.ForgetPassTwoAty;
import com.spkj.supai.utils.UiUtils;
import com.toocms.dink5.mylibrary.app.AppCountdown;
import com.toocms.dink5.mylibrary.commonutils.JSONUtils;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;

import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.Map;

/**
 * Created by aa on 2017/7/5.
 */

public class ResetPhoneAty extends BasAty {


    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;
    @ViewInject(R.id.tv_get)
    private TextView tv_get;
    @ViewInject(R.id.tv_info)
    private TextView tv_info;
    @ViewInject(R.id.et_yzm)
    private EditText et_yzm;
    private String type;

    private AppCountdown appCountdown;
    private Lar lar;
    private String phone;

    @Override
    public int getLayoutId() {
        return R.layout.aty_rephone;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("type");
        lar = new Lar();
        appCountdown = AppCountdown.getInstance();
        phone = PreferencesUtils.getString(this, "phone");
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appCountdown.play(tv_get);
        if (type.equals("rephone")) {
            tv_title.setText("修改手机号");
        } else {
            tv_title.setText("修改密码");
        }
        tv_right.setText("下一步");
        tv_right.setVisibility(View.VISIBLE);
    }

    @Event(value = {R.id.relay_back, R.id.tv_right, R.id.tv_get})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
            case R.id.tv_right:
//                if (type.equals("rephone")) {
//                    startActivity(ResetPhoneTwoAty.class);
//                } else {
//                    startActivity(ResetPassAty.class);
//                }
                if (TextUtils.isEmpty(et_yzm.getText().toString())) {
                    showShortToast("验证码不能为空");
                    return;
                }
                startProgressDialog();
                if (type.equals("rephone")) {
                    lar.checkCode(this, phone, et_yzm.getText().toString(), "2", this);
                } else {
                    lar.checkCode(this, phone, et_yzm.getText().toString(), "3", this);
                }
                break;
            case R.id.tv_get:
                startProgressDialog();
                if (type.equals("rephone")) {
                    lar.setCode(this, phone, "2", this);
                } else {
                    lar.setCode(this, phone, "3", this);
                }
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
                tv_info.setText("已发验证码到您的手机" + UiUtils.getPhone(phone));
                tv_info.setVisibility(View.VISIBLE);
            }
        }
        if (type.equals("user.verify.code.check")) {
            appCountdown.reSet();
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(var2);
            if (map.get("responseCode").equals("0")) {
                Bundle bundle = new Bundle();
                bundle.putString("successToken", map.get("successToken"));
                bundle.putString("phone", phone);
                if (this.type.equals("rephone")) {
                    startActivity(ResetPhoneTwoAty.class, bundle);
                } else {
                    startActivity(ResetPassAty.class, bundle);
                }
            } else {
                showShortToast(map.get("errorMsg"));
            }
        }
    }

}
