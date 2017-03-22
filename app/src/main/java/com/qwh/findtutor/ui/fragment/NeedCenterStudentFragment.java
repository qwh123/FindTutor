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
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.ReleaseUserBean;
import com.qwh.findtutor.bean.test.InfoBean;
import com.qwh.findtutor.bean.test.TutorBean;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
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

    private List<ReleaseUserBean.DataBean> mData;
    private CommonAdapter<ReleaseUserBean.DataBean> mAdapter;

    @Override
    protected void initViews() {
        initRefresh();
        initRv();
    }

    private void initRv() {
        if (mData == null || mData.size() == 0) {
            toast("暂无需求信息");
            return;
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<ReleaseUserBean.DataBean>(getActivity(), R.layout.item_main_rv, mData) {
            @Override
            public void setData(ViewHolder holder, ReleaseUserBean.DataBean data) {
                if (data.getIcon().equals(""))
                    holder.setImageResource(R.id.item_main_rv_icon, R.drawable.img_user);
                else
                    holder.setImageWithUrl(R.id.item_main_rv_icon, data.getIcon());
                holder.setText(R.id.item_main_rv_name, data.getDetail().getNickname());
                holder.setText(R.id.item_main_rv_level, "所需课程:" + data.getDetail().getSubject_id());
                holder.setText(R.id.item_main_rv_adress, "上课地址:" + data.getDetail().getAddress());
                holder.setText(R.id.item_main_rv_summary, "创建时间: " + data.getDetail().getCreate_time());
            }
        };
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                ReleaseUserBean.DataBean.DetailBean detail = mData.get(position).getDetail();
                Bundle bundle = new Bundle();
                bundle.putSerializable("userDetail", detail);
                startActivity(new Intent(getActivity(), NeedCenterDetailActivity.class)
                        .putExtras(bundle)
                );
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
                if (mData != null)
                    mData.clear();
                loadData();


            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_need_center_teacher;
    }

    @Override
    protected void loadData() {
        List<Param> params = new ArrayList<>();
        params.add(new Param("type", "2"));
        OkHttpUtils.post(apiServer.URL_NeedCenter_Student, new OkHttpUtils.ResultCallback<ReleaseUserBean>() {
            @Override
            public void onSuccess(ReleaseUserBean data) {
                if (data.getCode() == 200) {
                    mData = data.getData();
                    initViews();
                }
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Exception e) {
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
            }
        }, params);
//        mData = new ArrayList<>();
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "李同学", "数学", "每周san讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "刘同学", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄同学", "英语", "you are good"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
//        mData.add(new TutorBean(R.mipmap.ic_launcher, "张同学", "语文", "每周一讲"));
    }
}
