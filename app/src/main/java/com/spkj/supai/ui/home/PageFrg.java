package com.spkj.supai.ui.home;

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
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.toocms.dink5.mylibrary.commonwidget.LoadingTip;
import com.toocms.dink5.mylibrary.loopviewpager.CircleIndicator;
import com.toocms.dink5.mylibrary.loopviewpager.LoopViewPager;
import com.toocms.dink5.mylibrary.view.MyRefreshAndLoadListen;
import com.toocms.dink5.mylibrary.view.MyTwinklingRefreshLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by aa on 2017/6/21.
 */

public class PageFrg extends BaseFragment {

    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    @ViewInject(R.id.swipeRefreshLayout)
    MyTwinklingRefreshLayout refreshLayout;
    @ViewInject(R.id.loadedTip)
    LoadingTip loadedTip;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_page;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
    }

    @Override
    public void onResume() {
        super.onResume();
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
            View view = null;
            switch (viewType) {
                case 0:
                    view = inflater.inflate(R.layout.item_page_banner, parent, false);
                    return new GoldRecyclerAdapter.GoldViewHolder(view);
                case 1:
                    view = inflater.inflate(R.layout.item_page, parent, false);
                    return new GoldRecyclerAdapter.mGoldViewHolder(view);
                case 4:
                    view = inflater.inflate(R.layout.item_page_foot, parent, false);
                    return new GoldRecyclerAdapter.fGoldViewHolder(view);
                default:
                    view = inflater.inflate(R.layout.item_page, parent, false);
                    return new GoldRecyclerAdapter.mGoldViewHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case 0:
                    GoldViewHolder goldViewHolder = (GoldViewHolder) holder;
                    ArrayList<String> list = new ArrayList<>();
                    list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498117550297&di=ac7364f1ed4d135e002c4086f4021fb9&imgtype=0&src=http%3A%2F%2Fphotoshow.108sq.com%2Fuser%2F2017%2F0504%2F1922348652347621121743017.jpg");
                    list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498117550297&di=cecedcf9dec9ddedba5e5ce1d248c03b&imgtype=0&src=http%3A%2F%2Fimg1.ph.126.net%2FuGXCiObZfCXqWxelYpQI-g%3D%3D%2F6597955973028472771.jpg");
                    list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498117550297&di=76db1fd9bfae8b7ee169bcfbcaf3bd0e&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201306%2F02%2F20130602140430_rA5iL.thumb.600_0.jpeg");
                    PageBannerAdapter bannerAdapter = new PageBannerAdapter(getContext());
                    bannerAdapter.setBannerData(list);
                    goldViewHolder.item_viewpager.setAdapter(bannerAdapter);
                    goldViewHolder.item_viewpager.setLooperPic(true);//是否设置自动轮播
                    goldViewHolder.item_indicator.setViewPager(goldViewHolder.item_viewpager);
                    break;
                case 4:
                    fGoldViewHolder fgoldViewHolder = (fGoldViewHolder) holder;
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    fgoldViewHolder.recyclerview.setLayoutManager(linearLayoutManager);
                    linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
                    fgoldViewHolder.recyclerview.setAdapter(new PageFootAdapter(getActivity()));

                    break;
            }
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

            @ViewInject(R.id.item_viewpager)
            private LoopViewPager item_viewpager;
            @ViewInject(R.id.item_indicator)
            private CircleIndicator item_indicator;

            public GoldViewHolder(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
                AutoUtils.autoSize(itemView);
            }
        }

        class mGoldViewHolder extends RecyclerView.ViewHolder {


            public mGoldViewHolder(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
                AutoUtils.autoSize(itemView);
            }
        }

        class fGoldViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.recyclerview)
            private RecyclerView recyclerview;

            public fGoldViewHolder(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
                AutoUtils.autoSize(itemView);
            }
        }

    }
}
