package com.spkj.supai.ui.lar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.spkj.supai.R;
import com.spkj.supai.interfaces.Lar;
import com.spkj.supai.ui.MainAty;
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

public class LoginljAty extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.aty_ljlogin;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarCompat.setStatusBarColor(this, 0xffffffff);
    }

    @Event(value = {R.id.tv_login, R.id.tv_login_register, R.id.tv_login_forgetpass
    })
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.tv_login:
                startActivity(MainAty.class);
                break;
            case R.id.tv_login_register:
                startActivity(RegisterljAty.class);
                break;
            case R.id.tv_login_forgetpass:
                startActivity(ForgetPassljAty.class);
                break;
        }
    }


}
