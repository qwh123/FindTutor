package com.qwh.findtutor.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * com.qwh.findtutor.adapter
 * 开发者 qwh
 * 时间: 10:46
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class TabAdapter extends FragmentPagerAdapter {
    private List<String> mTitle;
    private List<Fragment> fragments;

    public TabAdapter(FragmentManager fm, List<Fragment> fragments, List<String> mTitles) {
        super(fm);
        this.mTitle = mTitles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitle.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return mTitle.get(position % mTitle.size());
    }


}