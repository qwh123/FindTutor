package com.qwh.findtutor.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.qwh.findtutor.bean.test.TutorBean;
import com.qwh.findtutor.ui.activity.CommentActivity;
import com.qwh.findtutor.ui.activity.NeedCenterDetailActivity;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCourseFragment extends BaseFragment  {
    @Bind(R.id.recyclerView_user_course_ing)
    RecyclerView recyclerView;

    private List<TutorBean> mData;
    private CommonAdapter<TutorBean> mAdapter;

    public UserCourseFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user_course_ing;
    }

    @Override
    protected void initViews() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<TutorBean>(getActivity(), R.layout.item_user_course, mData) {
            @Override
            public void setData(ViewHolder holder, TutorBean TutorBean) {
                holder.setImageResource(R.id.item_user_course_icon, TutorBean.getImg());
                holder.setText(R.id.item_user_course_name, TutorBean.getName());
                holder.setText(R.id.item_user_course_type, "教授课程: " + TutorBean.getType());
                holder.setText(R.id.item_user_course_summary, TutorBean.getSummary());
                holder.setOnClickListener(R.id.item_main_btn_comment, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if(){
//                            toast("你已经评价过了");
//                            return;
//                        }
                        startActivity(new Intent(getActivity(), CommentActivity.class));
                    }
                });
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



    @Override
    protected void loadData() {
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
    }

}
