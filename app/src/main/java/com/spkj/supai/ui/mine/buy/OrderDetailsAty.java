package com.spkj.supai.ui.mine.buy;

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

public class OrderDetailsAty extends BasAty {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @Override
    public int getLayoutId() {
        return R.layout.aty_order_details;
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
        tv_title.setText("订单详情");
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
