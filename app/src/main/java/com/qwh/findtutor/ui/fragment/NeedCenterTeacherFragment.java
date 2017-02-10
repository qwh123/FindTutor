package com.qwh.findtutor.ui.fragment;

import android.content.Intent;
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
import com.qwh.findtutor.bean.InfoBean;
import com.qwh.findtutor.ui.activity.InfoDetailActivity;
import com.qwh.findtutor.utils.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class NeedCenterTeacherFragment extends BaseFragment {

    @Bind(R.id.rv_need_center_teacher)
    RecyclerView rvInfo;
    @Bind(R.id.srl_need_center_teacher)
    SwipeRefreshLayout mRefreshLayout;

    private CommonAdapter<InfoBean> mAdapter;
    List<InfoBean> mList;


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
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.add(0, new InfoBean(1, R.mipmap.ic_launcher, "小丘", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                                "128.00", "福建省福州市仓山区上渡街王府井001", "这是添加数据", "甲级", 1));
                        mAdapter.notifyDataSetChanged();
                        if (mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                    }
                }, 3000);

            }
        });
    }

    private void initRv() {
        rvInfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (mList == null || mList.size() < 1) {
            return;
        }
        mAdapter = new CommonAdapter<InfoBean>(getActivity(), R.layout.item_info_rv, mList) {
            @Override
            public void setData(ViewHolder holder, InfoBean infoBean) {
                holder.setImageResource(R.id.iv_item_info_img, infoBean.getImg());
                holder.setText(R.id.tv_item_info_pub_name, "发布者:" + infoBean.getPub_name());
                holder.setText(R.id.tv_item_info_pub_class, "课程:" + infoBean.getPub_class());
                holder.setText(R.id.tv_item_info_pub_date, "上课时间:" + infoBean.getPub_date());
                holder.setText(R.id.tv_item_info_pub_price, "价格:" + infoBean.getPub_price());
                holder.setText(R.id.tv_item_info_pub_adress, "上课地址:" + infoBean.getPub_address());
                holder.setText(R.id.tv_item_info_pub_other, "备注:" + infoBean.getPub_other());
                if (infoBean.getPub_type() == 0)//老师
                {
                    holder.setText(R.id.tv_item_info_pub_level, "专业水平:" + infoBean.getPub_level());
                } else {
                    holder.setVisible(R.id.tv_item_info_pub_level, false);
                }
            }
        };
        mAdapter.notifyDataSetChanged();
        rvInfo.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        rvInfo.addItemDecoration(decoration);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                startActivity(new Intent(getActivity(), InfoDetailActivity.class)
                        .putExtra("info_title", mList.get(position).getPub_name()));

            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    @Override
    protected void loadData() {
        mList = new ArrayList<>();
        mList.add(new InfoBean(1, R.drawable.img_user, "小丘", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很备注有很多很多多很多", "甲级", 1));
        mList.add(new InfoBean(1, R.drawable.img_user, "小张", "英语", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "Lev 8", 1));
        mList.add(new InfoBean(1, R.drawable.img_user, "丘老师", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 0));
        mList.add(new InfoBean(1, R.drawable.img_user, "黄老师", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很备注有很多很多备注有很多很多备注有很多很多多", "Lev 8", 0));
        mList.add(new InfoBean(1, R.drawable.img_user, "郝先生", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 1));
        mList.add(new InfoBean(1, R.drawable.img_user, "张同学", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "Lev 8", 1));
        mList.add(new InfoBean(1, R.drawable.img_user, "天空你只", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 1));
        mList.add(new InfoBean(1, R.drawable.img_user, "很强势", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多备注有很多很多备注有很多很多", "Lev 8", 1));
        mList.add(new InfoBean(1, R.drawable.img_user, "李老师", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多很多", "甲级", 0));
        mList.add(new InfoBean(1, R.drawable.img_user, "小丘", "语文", "2010-10-01 10:00-2010-10-01 12:00",
                "128.00", "福建省福州市仓山区上渡街王府井001", "备注有很多备注有很多很多备注有很多很多很多", "甲级", 1));


    }

}
