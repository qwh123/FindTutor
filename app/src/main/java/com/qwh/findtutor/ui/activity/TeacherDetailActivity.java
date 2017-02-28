package com.qwh.findtutor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.adapter.TabAdapter;
import com.qwh.findtutor.ui.fragment.NeedCenterStudentFragment;
import com.qwh.findtutor.ui.fragment.NeedCenterTeacherFragment;
import com.qwh.findtutor.ui.fragment.TeacherDetailCommentFragment;
import com.qwh.findtutor.ui.fragment.TeacherDetailCourseFragment;
import com.qwh.findtutor.ui.fragment.TeacherDetailIntroFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TeacherDetailActivity extends AppCompatActivity {

    @Bind(R.id.btn_tutor_detail_join)
    Button btnTutorDetailJoin;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tab_detail)
    TabLayout mTabLayout;
    @Bind(R.id.viewpager_detail)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_detail);
        ButterKnife.bind(this);
        initToolBar();
        initView();
    }

    private void initToolBar() {
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle(getIntent().getStringExtra("teacher_name"));
        toolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.mipmap.em_back));
    }

    private void initView() {
        List<String> mTitles = new ArrayList<>();
        List<Fragment> fragments = new ArrayList<>();
        mTitles.add("简介");
        mTitles.add("课程");
        mTitles.add("评价");
        fragments.add(new TeacherDetailIntroFragment());
        fragments.add(new TeacherDetailCourseFragment());
        fragments.add(new TeacherDetailCommentFragment());
        mViewPager.setAdapter(new TabAdapter(getSupportFragmentManager(), fragments, mTitles));
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(android.R.color.holo_orange_dark));
        mTabLayout.setSelectedTabIndicatorHeight(5);
        mTabLayout.setTabTextColors(getResources().getColor(android.R.color.secondary_text_dark),
                getResources().getColor(android.R.color.tertiary_text_light));
        mTabLayout.setupWithViewPager(mViewPager);
    }


    @OnClick(R.id.btn_tutor_detail_join)
    public void onClick(View view) {
        Toast.makeText(this, "join", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
