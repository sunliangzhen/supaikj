package com.spkj.supai.ui.home;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.spkj.supai.R;
import com.spkj.supai.view.a.AnimatorPath;
import com.spkj.supai.view.CycleView;
import com.spkj.supai.view.LineRelayout;
import com.spkj.supai.view.a.PathEvaluator;
import com.toocms.dink5.mylibrary.base.BaseFragment;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa on 2017/6/21.
 */

public class PageLjFrg extends BaseFragment {

    @ViewInject(R.id.sliding_tabs)
    public TabLayout mTabTl;
    @ViewInject(R.id.vp_content)
    public ViewPager vp_content;
    @ViewInject(R.id.line_chart)
    private LineRelayout lineChart;
    @ViewInject(R.id.arc1)
    private CycleView arc1;
    @ViewInject(R.id.arc2)
    private CycleView arc2;
    @ViewInject(R.id.arc3)
    private CycleView arc3;
    @ViewInject(R.id.imgv_02)
    private ImageView imgv_02;

    private List<String> tabIndicators;
    private List<Fragment> tabFragments;

    private ArrayList<String> date_list = new ArrayList<>();
    private ArrayList<Float> data = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.frg_ljpage;
    }

    @Override
    public void initPresenter() {

    }

    private void startAnimatorPath(View view, String propertyName, AnimatorPath path) {
        ObjectAnimator anim = ObjectAnimator.ofObject(this, propertyName, new PathEvaluator(), path.getPoints().toArray());
        anim.setInterpolator(new DecelerateInterpolator());//动画插值器
        anim.setDuration(3000);
        anim.start();
    }

    @Override
    protected void initView() {
        tabIndicators = new ArrayList<>();
        tabFragments = new ArrayList<>();
        date_list.add("0");
        date_list.add("1");
        date_list.add("2");
        date_list.add("3");
        date_list.add("4");
        date_list.add("5");
        date_list.add("6");
        date_list.add("7");
        date_list.add("8");
        date_list.add("12");
        date_list.add("16");
        date_list.add("20");
        date_list.add("24");
        date_list.add("28");
        data.add(0.0f);
        data.add(1.0f);
        data.add(0.0f);
        data.add(0.0f);
        data.add(4.0f);
        data.add(0.0f);
        data.add(1.0f);
        data.add(0.0f);
        data.add(0.0f);
        data.add(0.0f);
        data.add(0.0f);
        data.add(2.0f);
        data.add(3.0f);
        data.add(4.0f);
        data.add(1.0f);
        for (int i = 1; i < 13; i++) {
            tabIndicators.add(i + "月");
            tabFragments.add(new PageFrg());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private AnimatorPath path;//声明动画集合

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTabTl.setTabMode(TabLayout.MODE_SCROLLABLE);
//        mTabTl.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.white), ContextCompat.getColor(getActivity(), R.color.white));
//        mTabTl.setSelectedTabIndicatorColor(ContextCompat.getColor(getActivity(), R.color.white));
//        ViewCompat.setElevation(mTabTl, 10);
        mTabTl.setupWithViewPager(vp_content);
        vp_content.setAdapter(new ContentPagerAdapter(getChildFragmentManager()));
        lineChart.setData(data, date_list);
        lineChart.setmYdefaultPosition(4);
        arc1.setProgress(50);
        arc2.setProgress(100);
        arc3.setProgress(70);

    }



    @Event(value = {R.id.imgv_02})
    private void onTestBaidulClick(View view) {

        switch (view.getId()) {
            case R.id.imgv_02:
                data.clear();
                for(int i=0;i<15;i++){
                    data.add((float) i);
                }
                lineChart.setData(data, date_list);
                lineChart.setmYdefaultPosition(4);
                break;
        }
    }


    class ContentPagerAdapter extends FragmentPagerAdapter {

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabIndicators.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }


}
