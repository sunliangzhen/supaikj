package com.spkj.supai.ui.home;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.spkj.supai.utils.ImagesUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa on 2017/5/12.
 */

public class PageBannerAdapter extends PagerAdapter {

    private List<String> bannerInfoList;
    private Context context;

    public PageBannerAdapter(Context context) {
        bannerInfoList = new ArrayList<>();
        this.context = context;
    }

    public void setBannerData(List<String> bannerInfoList) {
        this.bannerInfoList.clear();
        this.bannerInfoList.addAll(bannerInfoList);
    }

    @Override
    public int getCount() {
        return bannerInfoList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        ImagesUtils.disImg(context, bannerInfoList.get(position), imageView);
        container.addView(imageView);
        return imageView;
    }
}
