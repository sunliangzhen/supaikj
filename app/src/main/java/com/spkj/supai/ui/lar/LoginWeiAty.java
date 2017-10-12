package com.spkj.supai.ui.lar;

import android.os.Bundle;
import android.view.View;

import com.spkj.supai.R;
import com.toocms.dink5.mylibrary.base.BaseActivity;
import com.toocms.dink5.mylibrary.commonwidget.StatusBarCompat;

import org.xutils.view.annotation.Event;

/**
 * Created by aa on 2017/6/23.
 */

public class LoginWeiAty extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.aty_login_wei;
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
        StatusBarCompat.setStatusBarColor(this, 0xff746F69);
    }

    @Event(value = {R.id.relay_back})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
        }
    }


}
