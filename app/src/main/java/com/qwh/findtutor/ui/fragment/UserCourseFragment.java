package com.qwh.findtutor.ui.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseFragment;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.OnItemClickListener;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.MyClassBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.bean.UserCourseBean;
import com.qwh.findtutor.bean.test.TutorBean;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.ui.activity.CommentActivity;
import com.qwh.findtutor.ui.activity.NeedCenterDetailActivity;
import com.qwh.findtutor.ui.activity.TeacherDetailActivity;
import com.qwh.findtutor.ui.activity.UserCourseActivity;
import com.qwh.findtutor.utils.PreferenceUtil;
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

    //    private List<MyClassBean.DataBean.OrderBean> mData = new ArrayList<>();
//    private CommonAdapter<MyClassBean.DataBean.OrderBean> mAdapter;
    private CommonAdapter<UserCourseBean> mAdapter;
    private List<UserCourseBean> mOverList = new ArrayList<>();//已结课名单
    private int mPage;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_user_course_over;
    }

    @Override
    protected void initViews() {

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new CommonAdapter<UserCourseBean>(getActivity(), R.layout.item_user_course, mOverList) {
            @Override
            public void setData(ViewHolder holder, final UserCourseBean user) {
//                MyClassBean.DataBean.OrderBean.UserInfoBean user = bean.getUser_info();
                holder.setVisible(R.id.labelView_course, false);
                if (user.getType().equals("1")) {//1.主动约的课程，可以评价
                    holder.setBtnEnable(R.id.item_main_btn_comment, true);
                } else {//我发布的课程，不可评价
                    holder.setBtnEnable(R.id.item_main_btn_comment, false);
                }
                holder.setBtnText(R.id.item_main_btn_comment, "评价");
                holder.setOnClickListener(R.id.item_main_btn_comment, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), CommentActivity.class)
                                .putExtra("order_id", user.getOrderId()));
                    }
                });
                if (user.getIcon().equals(""))
                    holder.setImageResource(R.id.item_user_course_icon, R.drawable.img_user);
                else
                    holder.setImageWithUrl(R.id.item_user_course_icon, user.getIcon());
                holder.setText(R.id.item_user_course_name, user.getName());
                holder.setVisible(R.id.item_user_course_level, false);
                holder.setText(R.id.item_user_course_type, "所在地址: " + user.getAdress());
                holder.setText(R.id.item_user_course_summary, "备注：" + user.getDetail());
            }
        };
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(getActivity(), TeacherDetailActivity.class)
                                .putExtra("teacher_name", mOverList.get(position).getName())
                                .putExtra("id", mOverList.get(position).getId())
//                        .putExtra("teacher_name", mData.get(position).getUser_info().getNickname())
//                        .putExtra("id", mData.get(position).getUser_info().getId())
                );

//                Log.i("order_id", "查看评论列表的id: " + mData.get(position).getUser_info().getId());
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }


    @Override
    protected void loadData() {

        List<Param> params = new ArrayList<>();
        params.add(new Param("id", PreferenceUtil.getString(SharedSaveConstant.User_Id, "")));
        OkHttpUtils.post(apiServer.URL_User_Course, new OkHttpUtils.ResultCallback<MyClassBean>() {
            @Override
            public void onSuccess(MyClassBean data) {
                Log.i("usercourseFragment", "onSuccess: ");
                if (data.getCode() == 200 && data.getData() != null) {
                    if (data.getData().size() > 0) {
                        for (MyClassBean.DataBean da : data.getData()) {
                            for (MyClassBean.DataBean.OrderBean bean : da.getOrder()) {
                                if (bean.getState().equals("5")) {
//                                mData.add(bean);
                                    UserCourseBean courseBean = new UserCourseBean();
                                    if (PreferenceUtil.getString(SharedSaveConstant.User_Id, "")
                                            .equals(bean.getUser_info().getId())) {
                                        courseBean.setOrderId(bean.getOrder_id());
                                        courseBean.setId(da.getUser_id());
                                        courseBean.setName(da.getNickname());
                                        courseBean.setIcon(da.getIcon());
                                        courseBean.setLevel(da.getContact());
                                        courseBean.setType("1");
                                        courseBean.setAdress(da.getAddress());
                                        courseBean.setDetail(da.getDetail());
                                    } else {
                                        courseBean.setId(bean.getUser_info().getId());
                                        courseBean.setOrderId(bean.getOrder_id());
                                        courseBean.setName(bean.getUser_info().getNickname());
                                        courseBean.setIcon(bean.getUser_info().getIcon());
                                        courseBean.setLevel(bean.getUser_info().getContact());
                                        courseBean.setType("2");
                                        courseBean.setAdress(bean.getUser_info().getAddress());
                                        courseBean.setDetail(bean.getUser_info().getDetail());
                                    }
                                    mOverList.add(courseBean);
                                }
                            }
                        }
                        if (mOverList.size() > 0) {
                            initViews();
                        }
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, params);


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

}
