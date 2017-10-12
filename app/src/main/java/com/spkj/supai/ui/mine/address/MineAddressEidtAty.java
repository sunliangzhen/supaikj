package com.spkj.supai.ui.mine.address;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.suke.widget.SwitchButton;
import com.toocms.dink5.mylibrary.commonwidget.LoadingTip;
import com.toocms.dink5.mylibrary.view.MyRefreshAndLoadListen;
import com.toocms.dink5.mylibrary.view.MyTwinklingRefreshLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by aa on 2017/7/3.
 */

public class MineAddressEidtAty extends BasAty {


    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.switch_button)
    private SwitchButton switchButton;

    @Override
    public int getLayoutId() {
        return R.layout.aty_mine_address_edit;
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
        tv_title.setText("编辑地址");
//        switchButton.setChecked(true);
//        switchButton.isChecked();
//        switchButton.toggle();     //switch state
//        switchButton.toggle(false);//switch without animation
//        switchButton.setShadowEffect(true);//disable shadow effect
//        switchButton.setEnabled(false);//disable button
//        switchButton.setEnableEffect(false);//disable the switch animation
        switchButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                //TODO do your job
            }
        });
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
