package com.spkj.supai.ui.house;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spkj.supai.R;
import com.spkj.supai.app.Constant;
import com.spkj.supai.ui.order.TotalRecyclerAdapter;
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.toocms.dink5.mylibrary.commonwidget.LoadingTip;
import com.toocms.dink5.mylibrary.view.MyTwinklingRefreshLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by ROYGEM-0830-1 on 2017/9/21.
 */

public class HouseTabFrg extends BaseFragment {


    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    @ViewInject(R.id.swipeRefreshLayout)
    MyTwinklingRefreshLayout refreshLayout;
    @ViewInject(R.id.loadedTip)
    LoadingTip loadedTip;

    private TotalRecyclerAdapter totalRecyclerAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_hosue_tab;
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
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadmore(false);
    }

    private class TotalRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private LayoutInflater inflater;

        public TotalRecyclerAdapter(Context context) {
            this.mContext = context;
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_hosue_tab, parent, false);
            return new TotalRecyclerAdapter.DaiFuKuanViewHolder(view);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            switch (getItemViewType(position)) {
                case Constant.daizhifu:
                    break;
                default:
                    break;
            }

        }

        @Override
        public int getItemCount() {
            return 10;
        }


        class DaiFuKuanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //
            public DaiFuKuanViewHolder(View view) {
                super(view);
                x.view().inject(this, itemView);
                AutoUtils.autoSize(itemView);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
            }
        }

    }


}
