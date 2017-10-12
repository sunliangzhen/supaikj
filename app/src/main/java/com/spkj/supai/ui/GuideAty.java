package com.spkj.supai.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.spkj.supai.R;
import com.toocms.dink5.mylibrary.base.BaseActivity;
import com.toocms.dink5.mylibrary.commonutils.PreferencesUtils;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;


/**
 * @date 2016/4/9 13:04
 */
//@ContentView(R.layout.aty_guide)
public class GuideAty extends BaseActivity {

    @ViewInject(R.id.vp_guide)
    private ViewPager vp_guide;
    @ViewInject(R.id.btn_start)
    private Button btn_start;

    private static final int[] mImageIds = new int[]{R.drawable.guide_01,
            R.drawable.guide_02, R.drawable.guide_03, R.drawable.guide_04};
    private ArrayList<ImageView> mImageViewList;
    private int mPointWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SetTranslanteBar();
        initViews();
        vp_guide.setAdapter(new GuideAdapter());
        vp_guide.setOnPageChangeListener(new GuidePageListener());
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferencesUtils.putBoolean(GuideAty.this, "FirstG0", true);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.setClass(GuideAty.this, MainAty.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.aty_guide;
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

    /**
     * 初始化界面
     */
    private void initViews() {
        mImageViewList = new ArrayList<ImageView>();
        for (int i = 0; i < mImageIds.length; i++) {
            ImageView image = new ImageView(this);
            image.setBackgroundResource(mImageIds[i]);
            mImageViewList.add(image);
        }

        /**
         * ViewPager数据适配器
         */

        /**
         * viewpager的滑动监听
         */
    }
    class GuidePageListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (position == mImageIds.length - 1) {
                btn_start.setVisibility(View.VISIBLE);
            } else {
                btn_start.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }

    }
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
