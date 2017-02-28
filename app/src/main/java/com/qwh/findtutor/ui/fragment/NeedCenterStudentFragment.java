package com.qwh.findtutor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseFragment;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.test.InfoBean;
import com.qwh.findtutor.bean.test.TutorBean;
import com.qwh.findtutor.ui.activity.NeedCenterDetailActivity;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class NeedCenterStudentFragment extends BaseFragment {
    @Bind(R.id.rv_need_center_teacher)
    RecyclerView recyclerView;
    @Bind(R.id.srl_need_center_teacher)
    SwipeRefreshLayout mRefreshLayout;

    private List<TutorBean> mData;
    private CommonAdapter<TutorBean> mAdapter;

    @Override
    protected void initViews() {
        initRefresh();
        initRv();


    }

    private void initRv() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<TutorBean>(getActivity(), R.layout.item_main_rv, mData) {
            @Override
            public void setData(ViewHolder holder, TutorBean TutorBean) {
                holder.setImageResource(R.id.item_main_rv_rv_icon, TutorBean.getImg());
                holder.setText(R.id.item_main_rv_rv_name, TutorBean.getName());
                holder.setText(R.id.item_main_rv_rv_type, "教授课程: " + TutorBean.getType());
                holder.setText(R.id.item_main_rv_rv_summary, TutorBean.getSummary());
            }
        };
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(getActivity(), NeedCenterDetailActivity.class)
                        .putExtra("teacher_name", mData.get(position).getName()));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });

    }

    private void initRefresh() {
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mData.add(0, new TutorBean(R.drawable.img_user, "张同学", "语文", "新增"));
                        mAdapter.notifyDataSetChanged();
                        if (mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 3000);

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_need_center_teacher;
    }

    @Override
    protected void loadData() {
        mData = new ArrayList<>();
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "李同学", "数学", "每周san讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "刘同学", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄同学", "英语", "you are good"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
    }
}
