package com.spkj.supai.ui.mine.set;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by aa on 2017/7/5.
 */

public class ResetPassAty extends BasAty {


    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_right)
    private TextView tv_right;


    @Override
    public int getLayoutId() {
        return R.layout.aty_rephone_two;
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
        tv_title.setText("修改密码");
        tv_right.setText("完成");
        tv_right.setVisibility(View.VISIBLE);
    }

    @Event(value = {R.id.relay_back, R.id.tv_right})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
            case R.id.tv_right:
                finish();
                break;
        }
    }

}
