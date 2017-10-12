package com.spkj.supai.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import com.spkj.supai.R;
import com.spkj.supai.ui.home.PageLjFrg;
import com.toocms.dink5.mylibrary.base.BaseFragment;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ROYGEM-0830-1 on 2017/9/21.
 */

public class OrderFrg extends BaseFragment {


    @ViewInject(R.id.sliding_tabs)
    public TabLayout mTabTl;
    @ViewInject(R.id.vp_content)
    public ViewPager vp_content;

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;

    private OrderTabAdapter orderTabAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_order;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        list_fragment = new ArrayList<>();
        list_title = new ArrayList<>();
        list_title.add("全部");
        list_title.add("待付款");
        list_title.add("待发货");
        list_title.add("待收货");
        list_title.add("待确认");
        list_title.add("待评价");
        list_title.add("退款中");
        list_fragment.add(new TotalFrg());
        list_fragment.add(new TotalFrg());
        list_fragment.add(new TotalFrg());
        list_fragment.add(new TotalFrg());
        list_fragment.add(new TotalFrg());
        list_fragment.add(new TotalFrg());
        list_fragment.add(new TotalFrg());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderTabAdapter = new OrderTabAdapter(getChildFragmentManager(), list_fragment, list_title);

        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabTl.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.tab), ContextCompat.getColor(getActivity(), R.color.main_color2));
//        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
//        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(vp_content);
        vp_content.setAdapter(orderTabAdapter);

    }

    private class OrderTabAdapter extends FragmentPagerAdapter {
        private List<Fragment> list_fragment;
        private List<String> list_Title;

        public OrderTabAdapter(FragmentManager fm, List<Fragment> list_fragment, List<String> list_Title) {
            super(fm);
            this.list_fragment = list_fragment;
            this.list_Title = list_Title;
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }

        @Override
        public int getCount() {
            return list_Title.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return list_Title.get(position % list_Title.size());
        }
    }
}
