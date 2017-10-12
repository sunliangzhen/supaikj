package com.spkj.supai.ui.mine.company;

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
import com.toocms.dink5.mylibrary.view.MyRefreshAndLoadListen;
import com.toocms.dink5.mylibrary.view.MyTwinklingRefreshLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by aa on 2017/7/5.
 */

public class MineCompanyAty extends BasAty {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;
    @ViewInject(R.id.swipeRefreshLayout)
    MyTwinklingRefreshLayout refreshLayout;


    @Override
    public int getLayoutId() {
        return R.layout.aty_mine_company;
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
        tv_title.setText("入驻机构");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setAdapter(new GoldRecyclerAdapter(this));
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

    @Event(value = {R.id.relay_back})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.relay_back:
                finish();
                break;
        }
    }

    public class GoldRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private LayoutInflater inflater;

        public GoldRecyclerAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_mine_company, parent, false);
            return new GoldRecyclerAdapter.GoldViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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


            public GoldViewHolder(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
                AutoUtils.autoSize(itemView);
            }
        }

    }

}
