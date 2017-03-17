package com.qwh.findtutor.ui.fragment;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.qwh.findtutor.R;
import com.qwh.findtutor.adapter.TabAdapter;
import com.qwh.findtutor.base.BaseFragment;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.ui.activity.PublishActivity;
import com.qwh.findtutor.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class NeedCenterFragment extends BaseFragment {
    @Bind(R.id.tab_need_center)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager_need_center)
    ViewPager mViewPager;

    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList();

    public NeedCenterFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initViews() {
            mTitles.add("需求中心");
        if (PreferenceUtil.getString(SharedSaveConstant.User_Type, "").equals("1")) {//老师身份
            fragments.add(new NeedCenterStudentFragment());
        } else {
            fragments.add(new NeedCenterTeacherFragment());
        }
        mViewPager.setAdapter(new TabAdapter(getActivity().getSupportFragmentManager(), fragments, mTitles));
        mTabLayout.setSelectedTabIndicatorHeight(0);
        mTabLayout.setTabTextColors(getResources().getColor(android.R.color.secondary_text_dark),
                getResources().getColor(android.R.color.white));
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_need_center;
    }

    @Override
    protected void loadData() {
        initViews();
    }


    @OnClick(R.id.fabtn_need_center)
    public void onClick() {
        startActivityForResult(new Intent(getActivity(), PublishActivity.class), 3000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000) {

        }
    }

}
