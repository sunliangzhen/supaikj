package com.spkj.supai.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.spkj.supai.R;
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.toocms.dink5.mylibrary.commonwidget.LoadingTip;
import com.toocms.dink5.mylibrary.view.MyTwinklingRefreshLayout;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by ROYGEM-0830-1 on 2017/9/21.
 */

public class TotalFrg extends BaseFragment {


    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    @ViewInject(R.id.swipeRefreshLayout)
    MyTwinklingRefreshLayout refreshLayout;
    @ViewInject(R.id.loadedTip)
    LoadingTip loadedTip;

    private TotalRecyclerAdapter totalRecyclerAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_order_total;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        totalRecyclerAdapter = new TotalRecyclerAdapter(getActivity());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setAdapter(totalRecyclerAdapter);
        refreshLayout.setEnableRefresh(true);
        refreshLayout.setEnableLoadmore(true);
    }
}
