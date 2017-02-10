package com.qwh.findtutor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.base.utils.CommonAdapter;
import com.qwh.findtutor.base.utils.ViewHolder;
import com.qwh.findtutor.bean.CommentBean;
import com.qwh.findtutor.bean.TutorBean;
import com.qwh.findtutor.utils.SpacesItemDecoration;
import com.qwh.findtutor.utils.Utils;
import com.qwh.findtutor.view.FullyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends BaseActivity {

    @Bind(R.id.loadingprogressbar_detail_comment)
    ContentLoadingProgressBar loadingprogressbarDetailComment;
    @Bind(R.id.recyclerview_detail_comment)
    RecyclerView recyclerviewDetailComment;
    @Bind(R.id.edt_detail_comment_content)
    EditText edtDetailCommentContent;
    private CommonAdapter<CommentBean> mAdapter;

    List<CommentBean> mList;

    @Override
    public int setLayout() {
        return R.layout.activity_tutor_detail_comment;
    }

    @Override
    public void initView() {

        recyclerviewDetailComment.setLayoutManager(new FullyLinearLayoutManager(this));
        if (mList == null || mList.size() < 1) {
            Toast.makeText(this, "数据为空", Toast.LENGTH_SHORT).show();
            return;
        }
        mAdapter = new CommonAdapter<CommentBean>(this, R.layout.item_tutor_comment, mList) {
            @Override
            public void setData(ViewHolder holder, CommentBean commentBean) {
                holder.setImageResource(R.id.item_tutor_comment_icon, commentBean.getIcon());
                holder.setText(R.id.item_tutor_comment_name, commentBean.getName());
                holder.setText(R.id.item_tutor_comment_time, "时间: " + commentBean.getTime());
                holder.setText(R.id.item_tutor_comment_content, "评价:" + commentBean.getContent());
            }
        };
        mAdapter.notifyDataSetChanged();
        recyclerviewDetailComment.setAdapter(mAdapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(10);
        recyclerviewDetailComment.addItemDecoration(decoration);
    }

    @Override
    public void getData() {
        mList = new ArrayList<>();
        mList.add(new CommentBean("1", R.mipmap.ic_launcher, "张同学", "2016-11-26", "老师很棒老师很棒"));
        mList.add(new CommentBean("2", R.mipmap.ic_launcher, "张同学", "2016-11-26", "老师很棒老师很棒"));
        mList.add(new CommentBean("3", R.mipmap.ic_launcher, "李同学", "2016-1-26", "老师很棒老师很棒老师很棒老师很棒"));
        mList.add(new CommentBean("4", R.mipmap.ic_launcher, "张同学", "2016-10-26", "老师很棒"));
        mList.add(new CommentBean("5", R.mipmap.ic_launcher, "丘同学", "2016-09-26", "老师很棒老师很棒老师很棒老师老师很棒很棒老师很棒老师很棒老师很棒老师很棒"));
        mList.add(new CommentBean("6", R.mipmap.ic_launcher, "张同学", "2016-11-26", "老师很棒"));
    }

    @OnClick({R.id.iv_detail_comment_back, R.id.btn_detail_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_detail_comment_back:
                finish();
                break;
            case R.id.btn_detail_comment:
                if (TextUtils.isEmpty(edtDetailCommentContent.getText().toString())) {
                    Toast.makeText(this, "content can't null", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingprogressbarDetailComment.setVisibility(View.VISIBLE);
                Utils.hideSoftInput(edtDetailCommentContent, CommentActivity.this);
                mList.add(0, new CommentBean("",
                        R.mipmap.ic_launcher, "新同学",
                        "2016-11-20", edtDetailCommentContent.getText().toString()));

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        edtDetailCommentContent.setText("");

                        loadingprogressbarDetailComment.setVisibility(View.GONE);
                        mAdapter.notifyDataSetChanged();
                    }
                }, 2000);


                break;
        }
    }
}
