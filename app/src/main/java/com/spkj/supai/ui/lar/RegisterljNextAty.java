package com.spkj.supai.ui.lar;

import android.os.Bundle;
import android.view.View;

import com.spkj.supai.R;
import com.toocms.dink5.mylibrary.base.BaseActivity;

import org.xutils.view.annotation.Event;

/**
 * Created by aa on 2017/6/23.
 */

public class RegisterljNextAty extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.aty_ljregister_next;
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

    @Event(value = {R.id.imgv_back
    })
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
            case R.id.tv_login_register:
                break;
        }
    }


}
