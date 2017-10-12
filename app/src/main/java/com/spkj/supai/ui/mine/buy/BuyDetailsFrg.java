package com.spkj.supai.ui.mine.buy;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spkj.supai.R;
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.toocms.dink5.mylibrary.commonwidget.LoadingTip;
import com.toocms.dink5.mylibrary.view.MyRefreshAndLoadListen;
import com.toocms.dink5.mylibrary.view.MyTwinklingRefreshLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * Created by aa on 2017/7/3.
 */

public class BuyDetailsFrg extends BaseFragment {


    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    @ViewInject(R.id.swipeRefreshLayout)
    MyTwinklingRefreshLayout refreshLayout;
    @ViewInject(R.id.loadedTip)
    LoadingTip loadedTip;
    private String city;

    public static BuyDetailsFrg newInstance(String city) {
        Bundle args = new Bundle();
        args.putString("city", city);
        BuyDetailsFrg pageFragment = new BuyDetailsFrg();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_mine_account_details;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        city = getArguments().getString("city");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setAdapter(new GoldRecyclerAdapter(getActivity()));
        refreshLayout.setMyRefreshAndLoadListen(new MyRefreshAndLoadListen() {
            @Override
            public void refreshStart() {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
            }

            @Override
            public void loadMoreStart() {
                refreshLayout.finishLoadmore();
                refreshLayout.finishRefreshing();
            }
        });
    }

    public class GoldRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        public GoldRecyclerAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_mine_buy_details, parent, false);
            return new GoldRecyclerAdapter.GoldViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            GoldViewHolder mholder = (GoldViewHolder) holder;
            switch (city) {
                case "1":
                    mholder.item_tv_type.setText("青花瓷1");
                    break;
                case "2":
                    mholder.item_tv_type.setText("青花瓷2");
                    break;
                case "3":
                    mholder.item_tv_type.setText("青花瓷3");
                    break;
                case "4":
                    mholder.item_tv_type.setText("青花瓷4");
                    break;
                case "5":
                    mholder.item_tv_type.setText("青花瓷5");
                    break;
            }
            mholder.relay_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(OrderDetailsAty.class);
                }
            });
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        class GoldViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.item_tv_type)
            private TextView item_tv_type;
            @ViewInject(R.id.relay_details)
            private RelativeLayout relay_details;

            public GoldViewHolder(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
                AutoUtils.autoSize(itemView);
            }
        }
    }
}
