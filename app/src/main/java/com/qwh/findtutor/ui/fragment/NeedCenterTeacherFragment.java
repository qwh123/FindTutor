package com.qwh.findtutor.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.ui.activity.NeedCenterDetailActivity;
import com.qwh.findtutor.ui.activity.StudentDetailActivity;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class NeedCenterTeacherFragment extends BaseFragment {

    @Bind(R.id.rv_need_center_teacher)
    RecyclerView rvInfo;
    @Bind(R.id.srl_need_center_teacher)
    SwipeRefreshLayout mRefreshLayout;

    private CommonAdapter<ReleaseUserBean.DataBean> mAdapter;
    List<ReleaseUserBean.DataBean> mList;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_need_center_teacher;
    }

    @Override
    protected void initViews() {
        initRefresh();
        initRv();

    }

    private void initRefresh() {
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_purple,
                android.R.color.holo_blue_bright,
                android.R.color.holo_red_light);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mList != null)
                    mList.clear();
                loadData();


            }
        });
    }

    private void initRv() {
        rvInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mList == null || mList.size() < 1) {
            return;
        }
        mAdapter = new CommonAdapter<ReleaseUserBean.DataBean>(getActivity(), R.layout.item_info_rv, mList) {
            @Override
            public void setData(ViewHolder holder, ReleaseUserBean.DataBean infoBean) {
                if (infoBean.getIcon().equals(""))
                    holder.setImageResource(R.id.iv_item_info_img, R.drawable.img_user);
                else
                    holder.setImageWithUrl(R.id.iv_item_info_img, infoBean.getIcon());
                holder.setText(R.id.tv_item_info_pub_name, infoBean.getDetail().getNickname());
                holder.setText(R.id.tv_item_info_pub_class, "课程:" + infoBean.getDetail().getSubject_id());
                holder.setText(R.id.tv_item_info_pub_date, "上课时间:" + infoBean.getDetail().getClass_time());
                holder.setText(R.id.tv_item_info_pub_price, "价格: ¥" + infoBean.getDetail().getPrice());
                holder.setText(R.id.tv_item_info_pub_adress, "上课地址:" + infoBean.getDetail().getAddress());
                holder.setText(R.id.tv_item_info_pub_other, "创建时间:" + infoBean.getDetail().getCreate_time());
            }
        };
        mAdapter.notifyDataSetChanged();
        rvInfo.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        rvInfo.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                ReleaseUserBean.DataBean.DetailBean detail = mList.get(position).getDetail();
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

    @Override
    protected void loadData() {
        List<Param> params = new ArrayList<>();
        params.add(new Param("type", "1"));
        OkHttpUtils.post(apiServer.URL_NeedCenter_Student, new OkHttpUtils.ResultCallback<ReleaseUserBean>() {
            @Override
            public void onSuccess(ReleaseUserBean data) {
                if (data.getCode() == 200) {
                    mList = data.getData();
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
//        mList = new ArrayList<>();
//        mList.add(new InfoBean(1, R.drawable.img_user, "小丘", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很备注有很多很多多很多", "甲级", 1));
//        mList.add(new InfoBean(1, R.drawable.img_user, "小张", "英语", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "Lev 8", 1));
//        mList.add(new InfoBean(1, R.drawable.img_user, "丘老师", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 0));
//        mList.add(new InfoBean(1, R.drawable.img_user, "黄老师", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很备注有很多很多备注有很多很多备注有很多很多多", "Lev 8", 0));
//        mList.add(new InfoBean(1, R.drawable.img_user, "郝先生", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 1));
//        mList.add(new InfoBean(1, R.drawable.img_user, "张同学", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "Lev 8", 1));
//        mList.add(new InfoBean(1, R.drawable.img_user, "天空你只", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 1));
//        mList.add(new InfoBean(1, R.drawable.img_user, "很强势", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多备注有很多很多备注有很多很多", "Lev 8", 1));
//        mList.add(new InfoBean(1, R.drawable.img_user, "李老师", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 0));
//        mList.add(new InfoBean(1, R.drawable.img_user, "小丘", "语文", "2010-10-01 10:00-2010-10-01 12:00",
//                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多备注有很多很多备注有很多很多很多", "甲级", 1));


    }

}
