package com.qwh.findtutor.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.bean.CommentBean;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends BaseActivity {

    @Bind(R.id.ratingbar_comment_level)
    RatingBar ratingbarCommentLevel;
    @Bind(R.id.ratingbar_comment_attibute)
    RatingBar ratingbarCommentAttibute;
    @Bind(R.id.ratingbar_comment_skill)
    RatingBar ratingbarCommentSkill;
    @Bind(R.id.edt_comment)
    EditText edtComment;
    @Bind(R.id.btn_comment)
    Button btnComment;

    String ratingLevel = "5";
    String ratingAttitube = "5";
    String ratingSkill = "5";
    String strComment = null;
    String user_id = null;

    @Override
    public int setLayout() {
        return R.layout.activity_comment;
    }

    @Override
    public void initView() {
        ratingbarCommentLevel.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingLevel = String.valueOf(rating);
            }
        });
        ratingbarCommentAttibute.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingAttitube = String.valueOf(rating);
            }
        });
        ratingbarCommentSkill.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingSkill = String.valueOf(rating);
            }
        });

    }

    @Override
    public void getData() {
        user_id = getIntent().getStringExtra("user_id");
        initView();

    }


    @OnClick({R.id.iv_comment_back, R.id.btn_comment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_comment_back:
                showAlertDialog();
                break;
            case R.id.btn_comment:
                strComment = edtComment.getText().toString();
                if (TextUtils.isEmpty(strComment)) {
                    toast("评论内容不能为空...");
                    return;
                }
                SubComment();
                break;
        }
    }

    //提交评论
    private void SubComment() {
        List<Param> params = new ArrayList<>();
        params.add(new Param("id", PreferenceUtil.getString(SharedSaveConstant.User_Id, "")));
        params.add(new Param("user_id", user_id));
        params.add(new Param("education_bg", ratingLevel));
        params.add(new Param("attitude", ratingAttitube));
        params.add(new Param("skills", ratingSkill));
        params.add(new Param("comments", strComment));

        OkHttpUtils.post(apiServer.URL_Stu_Sub_Comment, new OkHttpUtils.ResultCallback<CommentBean>() {
            @Override
            public void onSuccess(CommentBean data) {
                toast(data.getSummary());
                if (data.getCode() == 200)
                    finish();
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, params);
    }

    //提示操作
    private void showAlertDialog() {
        new AlertDialog.Builder(this).setTitle("应用提示")
                .setMessage("确定不提交评论?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlertDialog();
    }
}
