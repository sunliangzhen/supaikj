package com.spkj.supai.ui.mine.cp;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.spkj.supai.R;
import com.spkj.supai.app.BasAty;
import com.spkj.supai.ui.home.FragmentAdapter;
import com.toocms.dink5.mylibrary.base.BaseFragment;
import com.toocms.dink5.mylibrary.commonutils.Settings;
import com.zhy.autolayout.utils.AutoUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by aa on 2017/7/3.
 */

public class MineCpAty extends BasAty {

    @ViewInject(R.id.news_v_line)
    private View sortFlag; // 排序标识

    @ViewInject(R.id.news_tv_jin)
    private TextView tv_jin;
    @ViewInject(R.id.news_tv_all)
    private TextView tv_all;
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    private float sortFlagWidth; // 排序标识的长度
    private int sortItemWidth; // 一个排序标签的宽度
    private int sortItemPadding; // 每个item的左右边距
    private int sortFlagPosition = 0; // 排序标识位置
    private TextView[] ttvv;

    private FragmentAdapter adapter;
    private List<BaseFragment> mNewsFragmentList;

    @Override
    public int getLayoutId() {
        return R.layout.aty_mine_cp;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        sortFlagWidth = AutoUtils.getPercentWidthSize(60);
        sortItemWidth = (int) ((Settings.displayWidth - (AutoUtils.getPercentWidthSize(1) * 1)) / 2);
        sortItemPadding = (int) ((sortItemWidth - sortFlagWidth) / 2);
        mNewsFragmentList = new ArrayList<>();
        mNewsFragmentList.add(CpDetailsFrg.newInstance("1"));
        mNewsFragmentList.add(CpDetailsFrg.newInstance("2"));
        adapter = new FragmentAdapter(getSupportFragmentManager(), mNewsFragmentList);
    }

    @Override
    public void requestData() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ttvv = new TextView[]{tv_jin, tv_all};
        tv_title.setText("我参拍的");
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) sortFlag.getLayoutParams();
        params.gravity = Gravity.BOTTOM;
        sortFlag.setLayoutParams(params);
        sortFlag.setX(sortItemPadding);
        setTextviewColor(sortFlagPosition);

        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                sortFlagPosition = position;
                setTextviewColor(sortFlagPosition);
                startTranslate(sortFlag, sortItemPadding + (sortItemWidth * sortFlagPosition));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setTextviewColor(int index) {
        for (int i = 0; i < ttvv.length; i++) {
            if (index == i) {
                ttvv[i].setTextColor(0xffc8b08e);
            } else {
                ttvv[i].setTextColor(0xff999999);
            }
        }
    }

    @Event(value = {R.id.news_tv_jin, R.id.news_tv_all, R.id.relay_back})
    private void onTestBaidulClick(View view) {
        switch (view.getId()) {
            case R.id.news_tv_jin:
                sortFlagPosition = 0;
                break;
            case R.id.news_tv_all:
                sortFlagPosition = 1;
                break;
            case R.id.relay_back:
                finish();
                break;
        }
        viewpager.setCurrentItem(sortFlagPosition);
        setTextviewColor(sortFlagPosition);
        startTranslate(sortFlag, sortItemPadding + (sortItemWidth * sortFlagPosition));
    }

    private void startTranslate(final View view, float endX) {
        float startx = view.getX();
        ValueAnimator animator = ValueAnimator.ofFloat(startx, endX);
        animator.setTarget(view);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((Float) animation.getAnimatedValue());
            }
        });
        animator.start();
    }
}
