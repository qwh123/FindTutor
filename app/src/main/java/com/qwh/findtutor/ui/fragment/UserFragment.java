package com.qwh.findtutor.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qwh.findtutor.R;
import com.qwh.findtutor.ui.activity.UserCourseActivity;
import com.qwh.findtutor.ui.activity.UserMessageActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    @Bind(R.id.iv_user_icon)
    ImageView ivUserIcon;
    @Bind(R.id.iv_user_name)
    TextView ivUserName;

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
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rv_user_my_course, R.id.rv_user_my_follow, R.id.rv_user_my_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_user_my_course:
                startActivity(new Intent(getActivity(), UserCourseActivity.class));
                break;
            case R.id.rv_user_my_follow:
                break;
            case R.id.rv_user_my_message:
                startActivity(new Intent(getActivity(), UserMessageActivity.class));
                break;
        }
    }
}
