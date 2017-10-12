package com.spkj.supai.ui.house;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;


import com.toocms.dink5.mylibrary.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aa on 2017/5/12.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> fragmentList = new ArrayList<>();


    public HomeFragmentAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        setFragments(fm, fragmentList);
    }

    public void setFragments(FragmentManager fm, List<BaseFragment> fragments) {
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
