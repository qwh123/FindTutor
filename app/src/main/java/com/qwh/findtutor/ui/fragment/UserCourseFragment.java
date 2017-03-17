package com.qwh.findtutor.ui.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.test.TutorBean;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.ui.activity.CommentActivity;
import com.qwh.findtutor.ui.activity.NeedCenterDetailActivity;
import com.qwh.findtutor.ui.activity.UserCourseActivity;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCourseFragment extends BaseFragment {
    @Bind(R.id.recyclerView_user_course_ing)
    RecyclerView recyclerView;

    private List<TutorBean> mData;
    private CommonAdapter<TutorBean> mAdapter;
    private int mPage;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user_course_over;
    }

    @Override
    protected void initViews() {
        //  loadData();
        if (mData != null && mData.size() > 0)
            initCourseData(mData);

    }

    private void initCourseData(List<TutorBean> mData) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<TutorBean>(getActivity(), R.layout.item_user_course, mData) {
            @Override
            public void setData(ViewHolder holder, TutorBean tutorBean) {
                holder.setVisible(R.id.labelView_course, false);
                holder.setBtnText(R.id.item_main_btn_comment, "评价");
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
                holder.setImageResource(R.id.item_user_course_icon, tutorBean.getImg());
                holder.setText(R.id.item_user_course_name, tutorBean.getName());
                holder.setText(R.id.item_user_course_type, "教授课程: " + tutorBean.getType());
                holder.setText(R.id.item_user_course_summary, tutorBean.getSummary());
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
                        .putExtra("teacher_name", UserCourseFragment.this.mData.get(position).getName()));
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    /**
     * 展示结束课程
     */
    private void showOverDialog() {
        new AlertDialog.Builder(getActivity())
                .setMessage("应用提示")
                .setMessage("确认课程以结束?")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//TODO
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }


    @Override
    protected void loadData() {
        List<Param> params=new ArrayList<>();
        OkHttpUtils.post(apiServer.URL_User_Course, new OkHttpUtils.ResultCallback<CommonBean>() {
            @Override
            public void onSuccess(CommonBean data) {


            }

            @Override
            public void onFailure(Exception e) {

            }
        },params);


        mData = new ArrayList<>();
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "李老师", "数学", "每周san讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "刘老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "黄老师", "英语", "you are good"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "英语", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "英语", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        mData.add(new TutorBean(R.mipmap.ic_launcher, "张老师", "语文", "每周一讲"));
        initViews();
    }

}
