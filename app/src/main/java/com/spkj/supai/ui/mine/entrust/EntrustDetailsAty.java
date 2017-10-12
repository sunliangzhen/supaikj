package com.spkj.supai.ui.mine.entrust;

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

public class EntrustDetailsAty extends BasAty {

    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    private String type;

    @Override
    public int getLayoutId() {
        return R.layout.aty_mine_entrust_details;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (type.equals("wt")) {
            tv_title.setText("我委托的");
        } else {
            tv_title.setText("我应征的");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        recyclerview.setAdapter(new GoldRecyclerAdapter(this));
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
            View view = inflater.inflate(R.layout.item_mine_entrust_details, parent, false);
            return new GoldRecyclerAdapter.GoldViewHolder(view);
//            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

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


            public GoldViewHolder(View itemView) {
                super(itemView);
                x.view().inject(this, itemView);
                AutoUtils.autoSize(itemView);
            }
        }


    }

}
