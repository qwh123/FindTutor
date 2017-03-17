package com.qwh.findtutor.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.ui.LoginMVP.view.LoginActivity;
import com.qwh.findtutor.ui.activity.UserCourseActivity;
import com.qwh.findtutor.ui.activity.UserMessageActivity;
import com.qwh.findtutor.ui.activity.UserRecordActivity;
import com.qwh.findtutor.utils.PreferenceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    //
//    @Bind(R.id.iv_user_icon)
//    ImageView ivUserIcon;
//    @Bind(R.id.iv_user_name)
//    TextView ivUserName;
    @Bind(R.id.btn_user_login)
    Button btnLogin;
    @Bind(R.id.tv_user_course)
    TextView tvCourse;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (TextUtils.isEmpty(PreferenceUtil.getString(SharedSaveConstant.User_Account, ""))) {
            btnLogin.setText("登陆");
        } else {
            btnLogin.setText("退出帐号");
        }
        if (PreferenceUtil.getString(SharedSaveConstant.User_Type, "").equals("1")) {
            tvCourse.setText("我的学员");
        } else
            tvCourse.setText("我的教员");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rv_user_my_course, R.id.rv_user_my_follow, R.id.rv_user_my_message, R.id.btn_user_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_user_login:
                if (TextUtils.isEmpty(PreferenceUtil.getString(SharedSaveConstant.User_Account, ""))) {
//                    getActivity().finish();
                } else {
                    showDialog();

                }
                break;
            case R.id.rv_user_my_course:
                startActivity(new Intent(getActivity(), UserCourseActivity.class));
                break;
            case R.id.rv_user_my_follow:
                startActivity(new Intent(getActivity(), UserRecordActivity.class));
                break;
            case R.id.rv_user_my_message:
                startActivity(new Intent(getActivity(), UserMessageActivity.class));
                break;
        }
    }

    private void showDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle("应用提示")
                .setMessage("退出后本地缓存将清空")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PreferenceUtil.removeAll();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();

                    }
                }).show();
    }
}
