package com.spkj.supai.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.spkj.supai.R;
import com.spkj.supai.ui.lar.LoginljAty;
import com.toocms.dink5.mylibrary.base.BaseActivity;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;

import org.xutils.view.annotation.ViewInject;


/**
 * Created by pc on 2017/3/14.
 */

public class SplashAty extends BaseActivity {

    @ViewInject(R.id.imgv)
    ImageView imgv;

    //    public LocationClient mLocationClient = null;
//    public BDLocationListener myListener = new MyLocationListener();
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
        SetTranslanteBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!PreferencesUtils.getBoolean(SplashAty.this, "FirstG0")) {
                    startActivity(GuideAty.class);
                    finish();
                } else {
                    startActivity(LoginljAty.class);
                    finish();
                }
            }
        }, 2000);
    }

    @Override
    public int getLayoutId() {
        return R.layout.aty_splash;
    }


}
