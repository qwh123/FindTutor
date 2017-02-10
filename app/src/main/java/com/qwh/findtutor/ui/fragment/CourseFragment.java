package com.qwh.findtutor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.TutorBean;
import com.qwh.findtutor.ui.activity.TutorDetailActivity;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class CourseFragment extends Fragment {

    private List<TutorBean> mData;
    private CommonAdapter<TutorBean> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = new ArrayList<>();
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "李老师", "数学", "每周san讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "刘老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "英语", "you are good"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_user_course);
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
                startActivity(new Intent(getActivity(), TutorDetailActivity.class)
                        .putExtra("teacher_name", mData.get(position).getName()));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });

    }
}
