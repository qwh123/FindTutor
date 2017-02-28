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

import com.qwh.findtutor.R;
import com.qwh.findtutor.adapter.TabAdapter;
import com.qwh.findtutor.ui.fragment.NeedCenterStudentFragment;
import com.qwh.findtutor.ui.fragment.UserCourseFragment;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_course);
        ButterKnife.bind(this);
        initToolerBar();
        initView();
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
        mTitles.add("进行中");
        mTitles.add("已结课");
        fragments.add(new UserCourseFragment());
        fragments.add(new NeedCenterStudentFragment());
        viewpagerUserCourse.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, mTitles));
        tabUserCourse.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.white));
        tabUserCourse.setSelectedTabIndicatorHeight(5);
        tabUserCourse.setTabTextColors(getResources().getColor(android.R.color.secondary_text_dark),
                getResources().getColor(android.R.color.white));
        tabUserCourse.setupWithViewPager(viewpagerUserCourse);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ButterKnife.unbind(this);
    }

}
