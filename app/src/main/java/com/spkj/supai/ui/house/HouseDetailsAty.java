package com.spkj.supai.ui.house;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.ui.home.PageBannerAdapter;
import com.spkj.supai.ui.home.PageLjFrg;
import com.toocms.dink5.mylibrary.loopviewpager.CircleIndicator;
import com.toocms.dink5.mylibrary.loopviewpager.LoopViewPager;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by ROYGEM-0830-1 on 2017/9/25.
 */

public class HouseDetailsAty extends BasAty {


    @ViewInject(R.id.item_viewpager)
    private LoopViewPager item_viewpager;
    @ViewInject(R.id.item_indicator)
    private CircleIndicator item_indicator;
    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    @Override
    public int getLayoutId() {
        return R.layout.aty_ljhousedetails;
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
        ArrayList<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498117550297&di=ac7364f1ed4d135e002c4086f4021fb9&imgtype=0&src=http%3A%2F%2Fphotoshow.108sq.com%2Fuser%2F2017%2F0504%2F1922348652347621121743017.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498117550297&di=cecedcf9dec9ddedba5e5ce1d248c03b&imgtype=0&src=http%3A%2F%2Fimg1.ph.126.net%2FuGXCiObZfCXqWxelYpQI-g%3D%3D%2F6597955973028472771.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498117550297&di=76db1fd9bfae8b7ee169bcfbcaf3bd0e&imgtype=0&src=http%3A%2F%2Fcdn.duitang.com%2Fuploads%2Fitem%2F201306%2F02%2F20130602140430_rA5iL.thumb.600_0.jpeg");
        PageBannerAdapter bannerAdapter = new PageBannerAdapter(this);
        bannerAdapter.setBannerData(list);
        item_viewpager.setAdapter(bannerAdapter);
        item_viewpager.setLooperPic(true);//是否设置自动轮播
        item_indicator.setViewPager(item_viewpager);


        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setAdapter(new TotalRecyclerAdapter(this));
    }


    @Event(value = {R.id.imgv_back})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
        }
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
            View view = inflater.inflate(R.layout.item_details, parent, false);
            return new TotalRecyclerAdapter.DaiFuKuanViewHolder(view);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TotalRecyclerAdapter.DaiFuKuanViewHolder viewHolder = (TotalRecyclerAdapter.DaiFuKuanViewHolder) holder;

        }

        @Override
        public int getItemCount() {
            return 4;
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
