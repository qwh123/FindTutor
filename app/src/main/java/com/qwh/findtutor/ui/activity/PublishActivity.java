package com.qwh.findtutor.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishActivity extends BaseActivity {

    @Bind(R.id.iv_publish_back)
    ImageView ivPublishBack;
    @Bind(R.id.edt_publish_nickname)
    EditText edtPublishNickname;
    @Bind(R.id.edt_publish_class)
    EditText edtPublishClass;
    @Bind(R.id.edt_publish_date)
    EditText edtPublishDate;
    @Bind(R.id.edt_publish_price)
    EditText edtPublishPrice;
    @Bind(R.id.edt_publish_adress)
    EditText edtPublishAdress;
    @Bind(R.id.edt_publish_way)
    EditText edtPublishWay;
    @Bind(R.id.edt_publish_other)
    EditText edtPublishOther;
    @Bind(R.id.btn_publish)
    Button btnPublish;


    @Override
    public int setLayout() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {

    }

    @Override
    public void getData() {

    }


    @OnClick({R.id.iv_publish_back, R.id.btn_publish})
    public void onClick(View view) {
        Intent data = new Intent();
        switch (view.getId()) {
            case R.id.iv_publish_back:
                setResult(4000, data);
                finish();
                break;
            case R.id.btn_publish:
                setResult(5000, data);
                finish();
                break;
        }
    }
}
