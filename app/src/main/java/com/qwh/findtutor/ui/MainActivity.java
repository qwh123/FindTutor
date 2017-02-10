package com.qwh.findtutor.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.ui.fragment.HomeFragment;
import com.qwh.findtutor.ui.fragment.NeedCenterFragment;
import com.qwh.findtutor.ui.fragment.NeedCenterTeacherFragment;
import com.qwh.findtutor.ui.fragment.UserFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements HomeFragment.OnHeadlineSelectedListener {

    @Bind(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    private HomeFragment mHomeFragment;
    private UserFragment mUserFragment;
    private NeedCenterFragment mInfoFragment;
    private FragmentManager fm;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        initVariables();

        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE)
                .addItem(new BottomNavigationItem(R.drawable.main, "应用首页").setActiveColorResource(android.R.color.holo_blue_dark))
                .addItem(new BottomNavigationItem(R.drawable.help, "需求中心").setActiveColorResource(android.R.color.holo_blue_dark))
                .addItem(new BottomNavigationItem(R.drawable.mine, "个人中心").setActiveColorResource(android.R.color.holo_blue_dark))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position) {
                    case 0:
                        showFragment(0);
                        break;
                    case 1:
                        showFragment(1);
                        break;
                    case 2:
                        showFragment(2);
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {
            }

            @Override
            public void onTabReselected(int position) {
            }
        });
        showFragment(0);
    }

    @Override
    public void getData() {

    }

    protected void initVariables() {
        fm = getSupportFragmentManager();
    }


    private void showFragment(int position) {
        FragmentTransaction ft = fm.beginTransaction();
        hideAllFragment(ft);
        switch (position) {
            case 0:
                if (mHomeFragment != null) {
                    ft.show(mHomeFragment);
                } else {
                    mHomeFragment = new HomeFragment();
                    ft.add(R.id.fragment_layout, mHomeFragment);
                }
                break;
            case 1:
                if (mInfoFragment != null) {
                    ft.show(mInfoFragment);
                } else {
                    mInfoFragment = new NeedCenterFragment();
                    ft.add(R.id.fragment_layout, mInfoFragment);
                }
                break;
            case 2:
                if (mUserFragment != null) {
                    ft.show(mUserFragment);
                } else {
                    mUserFragment = new UserFragment();
                    ft.add(R.id.fragment_layout, mUserFragment);
                }
                break;
        }
        ft.commit();
    }

    private void hideAllFragment(FragmentTransaction ft) {
        if (mHomeFragment != null) {
            ft.hide(mHomeFragment);
        }
        if (mUserFragment != null) {
            ft.hide(mUserFragment);
        }
        if (mInfoFragment != null) {
            ft.hide(mInfoFragment);
        }

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setTitle("系统提示")//设置对话框标题
                .setMessage("确认退出?")//设置显示的内容
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {//添加确定按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {//响应事件
                // TODO Auto-generated method stub
            }
        }).show();//在按键响应事件中显示此对话框
    }

    @Override
    public void onFragmentSelected(int position) {
        showFragment(position);
        mBottomNavigationBar.selectTab(position);
    }
}