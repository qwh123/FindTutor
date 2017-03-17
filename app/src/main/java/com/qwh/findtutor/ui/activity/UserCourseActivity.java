package com.qwh.findtutor.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.adapter.TabAdapter;
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.test.TutorBean;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.ui.fragment.NeedCenterStudentFragment;
import com.qwh.findtutor.ui.fragment.UserCourseFragment;
import com.qwh.findtutor.ui.fragment.UserCourseIngFragment;
import com.qwh.findtutor.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserCourseActivity extends AppCompatActivity {


    @Bind(R.id.tab_user_course)
    TabLayout tabUserCourse;
    @Bind(R.id.viewpager_user_course)
    ViewPager viewpagerUserCourse;
    @Bind(R.id.toolbar_user_course)
    Toolbar toolbar;

    private List<String> mTitles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList();
    private List<TutorBean> mData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_course);
        ButterKnife.bind(this);
        initToolerBar();
        initView();
//        initTabLayoutAndViewPager();
    }

    private void initToolerBar() {
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle(getString(R.string.my_course));
        toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.mipmap.em_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initView() {
        if (!Utils.hasNetwork(UserCourseActivity.this)) {
            Toast.makeText(this, getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
            return;
        }

//模拟数据
        loadData();
    }

    private void loadData() {
        List<Param> params=new ArrayList<>();
        OkHttpUtils.post(apiServer.URL_User_Course, new OkHttpUtils.ResultCallback<CommonBean>() {
            @Override
            public void onSuccess(CommonBean data) {
                initTabLayoutAndViewPager();
            }

            @Override
            public void onFailure(Exception e) {

            }
        },params);
//
//        mData = new ArrayList<>();
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "李老师", "数学", "每周san讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "刘老师", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "英语", "you are good"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "英语", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "英语", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
    }

    private void initTabLayoutAndViewPager() {
        mTitles.add("进行中");
        mTitles.add("已结课");
        fragments.add(new UserCourseIngFragment());
        fragments.add(new UserCourseFragment());
//        fragments.add(UserCourseFragment.newInstance(1));
        viewpagerUserCourse.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, mTitles));
        tabUserCourse.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
        tabUserCourse.setSelectedTabIndicatorHeight(5);
        tabUserCourse.setTabTextColors(getResources().getColor(android.R.color.secondary_text_dark),
                getResources().getColor(android.R.color.white));
        tabUserCourse.setupWithViewPager(viewpagerUserCourse);

    }


    public List<TutorBean> getCourseList() {
        return mData;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ButterKnife.unbind(this);
    }

}
