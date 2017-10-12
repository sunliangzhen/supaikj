package com.spkj.supai.ui.house;

import android.view.View;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.ui.MainAty;

import org.xutils.view.annotation.Event;

/**
 * Created by ROYGEM-0830-1 on 2017/9/25.
 */

public class ScreenAty extends BasAty {
    @Override
    public int getLayoutId() {
        return R.layout.aty_ljscreen;
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

    @Event(value = {R.id.imgv_back
    })
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_back:
                startActivity(MainAty.class);
                break;
        }
    }

}
