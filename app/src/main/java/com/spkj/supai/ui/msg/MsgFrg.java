package com.spkj.supai.ui.msg;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.ui.house.AddHouseAty;
import com.spkj.supai.ui.house.HouseFrg;
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by ROYGEM-0830-1 on 2017/9/22.
 */

public class MsgFrg extends BaseFragment {


    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_msg;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recyclerview.setAdapter(new TotalRecyclerAdapter(getActivity()));
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
            View view = inflater.inflate(R.layout.item_msg, parent, false);
            return new TotalRecyclerAdapter.DaiFuKuanViewHolder(view);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            TotalRecyclerAdapter.DaiFuKuanViewHolder viewHolder = (TotalRecyclerAdapter.DaiFuKuanViewHolder) holder;
            switch (position) {
                case 0:
                    viewHolder.imgv.setImageResource(R.drawable.lj_ic_msg01);
                    viewHolder.tv_type.setText("客服");
                    viewHolder.tv_content.setText("售前售后客服消息");
                    break;
                case 1:
                    viewHolder.imgv.setImageResource(R.drawable.lj_ic_msg02);
                    viewHolder.tv_type.setText("活动");
                    viewHolder.tv_content.setText("平台优惠活动消息");
                    break;
                case 2:
                    viewHolder.imgv.setImageResource(R.drawable.lj_ic_msg03);
                    viewHolder.tv_type.setText("买家评价");
                    viewHolder.tv_content.setText("买家对商家的评价消息");
                    break;
            }
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (position) {
                        case 0:
                            break;
                        case 1:
                            startActivity(MsgActivityAty.class);
                            break;
                        case 2:
                            break;
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 3;
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
