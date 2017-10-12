package com.spkj.supai.ui.msg;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.ui.home.PageLjFrg;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by ROYGEM-0830-1 on 2017/9/25.
 */

public class MsgActivityAty extends BasAty {


    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;


    @Override
    public int getLayoutId() {
        return R.layout.aty_msg_activity;
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

    @Event(value = {R.id.imgv_back})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_back:
                finish();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setAdapter(new TotalRecyclerAdapter(this));
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
            View view = inflater.inflate(R.layout.item_msg_activity, parent, false);
            return new TotalRecyclerAdapter.DaiFuKuanViewHolder(view);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TotalRecyclerAdapter.DaiFuKuanViewHolder viewHolder = (TotalRecyclerAdapter.DaiFuKuanViewHolder) holder;
        }

        @Override
        public int getItemCount() {
            return 10;
        }


        class DaiFuKuanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //

            @ViewInject(R.id.imgv)
            public ImageView imgv;
            @ViewInject(R.id.tv_type)
            public TextView tv_type;
            @ViewInject(R.id.tv_content)
            public TextView tv_content;

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
