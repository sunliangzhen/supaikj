package com.spkj.supai.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.ui.home.PageLjFrg;
import com.spkj.supai.ui.house.HouseFrg;
import com.spkj.supai.ui.mine.MineLjFrg;
import com.spkj.supai.ui.msg.MsgFrg;
import com.spkj.supai.ui.msg.TestTransformMatrixActivity;
import com.spkj.supai.ui.order.OrderFrg;
import com.toocms.dink5.mylibrary.base.BaseActivity;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by aa on 2017/6/14.
 */

public class MainAty extends BaseActivity {

    @ViewInject(R.id.tv_01)
    public TextView tv_01;
    @ViewInject(R.id.tv_02)
    public TextView tv_02;
    @ViewInject(R.id.tv_03)
    public TextView tv_03;
    @ViewInject(R.id.tv_030)
    public TextView tv_030;
    @ViewInject(R.id.tv_04)
    public TextView tv_04;
    @ViewInject(R.id.imgv_01)
    public ImageView imgv_01;
    @ViewInject(R.id.imgv_02)
    public ImageView imgv_02;
    @ViewInject(R.id.imgv_03)
    public ImageView imgv_03;
    @ViewInject(R.id.imgv_030)
    public ImageView imgv_030;
    @ViewInject(R.id.imgv_04)
    public ImageView imgv_04;
    private ArrayList<TextView> list_tv = new ArrayList<>();
    private ArrayList<ImageView> list_imgv = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.aty_main;
    }

    @Override
    protected int getFragmentContainerId() {
        return R.id.fralay_content;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        list_tv.add(tv_01);
        list_tv.add(tv_02);
        list_tv.add(tv_03);
        list_tv.add(tv_030);
        list_tv.add(tv_04);
        list_imgv.add(imgv_01);
        list_imgv.add(imgv_02);
        list_imgv.add(imgv_03);
        list_imgv.add(imgv_030);
        list_imgv.add(imgv_04);
    }

    @Override
    public void requestData() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetStatusBarColor(0xffB68C47);
        setBackTwo(true);
        addFragment(PageLjFrg.class, null);
    }


    @Event(value = {R.id.linlay_01, R.id.linlay_02, R.id.linlay_03, R.id.linlay_030, R.id.linlay_04})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.linlay_01:
                setSelector(tv_01);
                SetStatusBarColor(0xffB68C47);
                addFragment(PageLjFrg.class, null);
                break;
            case R.id.linlay_02:
                startActivity(TestTransformMatrixActivity.class);
                SetStatusBarColor(0xffffffff);
                setSelector(tv_02);
                addFragment(OrderFrg.class, null);
                break;
            case R.id.linlay_03:
                SetStatusBarColor(0xffffffff);
                setSelector(tv_03);
                addFragment(HouseFrg.class, null);
                break;
            case R.id.linlay_030:
                SetStatusBarColor(0xffffffff);
                setSelector(tv_030);
                addFragment(MsgFrg.class, null);
                break;
            case R.id.linlay_04:
                SetStatusBarColor(0xffffffff);
                setSelector(tv_04);
//                startActivity(LineChartActivity.class);
                addFragment(MineLjFrg.class, null);
                break;
        }
    }

    private void setSelector(TextView tv) {
        for (int i = 0; i < list_tv.size(); i++) {
            if (tv == list_tv.get(i)) {
                list_tv.get(i).setTextColor(0xffc8b08e);
                switch (i) {
                    case 0:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_page_check);
                        break;
                    case 1:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_order_check);
                        break;
                    case 2:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_cangku_check);
                        break;
                    case 3:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_msg_check);
                        break;
                    case 4:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_mine_check);
                        break;
                }
            } else {
                list_tv.get(i).setTextColor(0xff666666);
                switch (i) {
                    case 0:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_page);
                        break;
                    case 1:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_order);
                        break;
                    case 2:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_cangku);
                        break;
                    case 3:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_msg);
                        break;
                    case 4:
                        list_imgv.get(i).setImageResource(R.drawable.ic_home_mine);
                        break;
                }
            }
        }
    }

}
