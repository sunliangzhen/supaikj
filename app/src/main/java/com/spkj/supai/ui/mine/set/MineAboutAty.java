package com.spkj.supai.ui.mine.set;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by aa on 2017/7/3.
 */

public class MineAboutAty extends BasAty {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @Override
    public int getLayoutId() {
        return R.layout.aty_mine_about;
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

    @Event(value = {R.id.relay_back})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_title.setText("关于我们");
    }
}
