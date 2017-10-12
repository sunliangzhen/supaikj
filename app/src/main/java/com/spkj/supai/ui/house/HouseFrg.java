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
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.ui.home.PageLjFrg;
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ROYGEM-0830-1 on 2017/9/21.
 */

public class HouseFrg extends BaseFragment {


    //    @ViewInject(R.id.sliding_tabs)
//    public TabLayout tabLayout;
//    @ViewInject(R.id.viewpager)
//    public ViewPager viewpager;
    private List<BaseFragment> mNewsFragmentList;
    private HomeFragmentAdapter fragmentAdapter;


    @ViewInject(R.id.recyclerview)
    public RecyclerView recyclerview;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_house;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mNewsFragmentList = new ArrayList<>();
        mNewsFragmentList.add(new HouseTabFrg());
        mNewsFragmentList.add(new HouseTabFrg());
        mNewsFragmentList.add(new HouseTabFrg());
        mNewsFragmentList.add(new HouseTabFrg());
    }


    @Event(value = {R.id.relay_now_order})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.relay_now_order:
                startActivity(NowOrderAty.class);
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (fragmentAdapter == null) {
//            fragmentAdapter = new HomeFragmentAdapter(getChildFragmentManager(), mNewsFragmentList);
//        } else {
//            fragmentAdapter.setFragments(getChildFragmentManager(), mNewsFragmentList);
//        }
//        tabLayout.removeAllTabs();
//        viewpager.setAdapter(fragmentAdapter);
//        tabLayout.setupWithViewPager(viewpager);
//        for (int i = 0; i < mNewsFragmentList.size(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);//获得每一个tab
//            tab.setCustomView(R.layout.item_house);//给每一个tab设置view
//            TextView textView = (TextView) tab.getCustomView().findViewById(R.id.tab_tv);
//            ImageView imgv = (ImageView) tab.getCustomView().findViewById(R.id.tab_imv);
//            switch (i + "") {
//                case "0":
//                    imgv.setVisibility(View.INVISIBLE);
//                    textView.setText("种类");
//                    break;
//                case "1":
//                    imgv.setVisibility(View.VISIBLE);
//                    textView.setText("销量额");
//                    break;
//                case "2":
//                    imgv.setVisibility(View.VISIBLE);
//                    textView.setText("剩余");
//                    break;
//                case "3":
//                    imgv.setVisibility(View.INVISIBLE);
//                    textView.setText("进货");
//                    break;
//            }
//        }
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);

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
            View view = inflater.inflate(R.layout.item_hosue_tab, parent, false);
            return new TotalRecyclerAdapter.DaiFuKuanViewHolder(view);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            DaiFuKuanViewHolder viewHolder = (DaiFuKuanViewHolder) holder;
            if (position == 9) {
                viewHolder.tv_bottom.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_bottom.setVisibility(View.GONE);
            }
            viewHolder.tv_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(AddHouseAty.class);
                }
            });

        }

        @Override
        public int getItemCount() {
            return 10;
        }


        class DaiFuKuanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            //

            @ViewInject(R.id.tv_bottom)
            public TextView tv_bottom;

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
