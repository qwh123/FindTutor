package com.qwh.findtutor.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.bean.CommonBean;
import com.qwh.findtutor.bean.CommonBean1;
import com.qwh.findtutor.bean.NeedCenterDetailBean;
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

public class PublishActivity extends BaseActivity {


    @Bind(R.id.edt_publish_nickname)
    EditText edtPublishNickname;
    @Bind(R.id.edt_publish_way)
    EditText edtPublishWay;
    @Bind(R.id.edt_publish_class)
    EditText edtPublishClass;
    @Bind(R.id.edt_publish_price)
    Button btnPublishPrice;
    @Bind(R.id.edt_publish_date)
    EditText edtPublishDate;
    @Bind(R.id.edt_publish_adress)
    EditText edtPublishAdress;
    @Bind(R.id.edt_publish_other)
    EditText edtPublishOther;
    @Bind(R.id.btn_publish)
    Button btnPublish;
    String[] priceList = new String[]{"100元以下", "100-150元", "150-200元", "200-250元", "250-300元", "300元以上"};
    private String nickName;
    private String pubWay;
    private String pubClass;
    private String pubPrice;
    private String pubDate;
    private String pubAdress;
    private String pubOther;


    @Override
    public int setLayout() {
        return R.layout.activity_publish;
    }

    @Override
    public void initView() {
        edtPublishNickname.setText(PreferenceUtil.getString(SharedSaveConstant.User_NickName, ""));
        edtPublishWay.setText(PreferenceUtil.getString(SharedSaveConstant.User_Account, ""));
        edtPublishAdress.setText(PreferenceUtil.getString(SharedSaveConstant.User_ClassAdress, ""));
    }

    @Override
    public void getData() {
        initView();
    }


    @OnClick({R.id.iv_publish_back, R.id.btn_publish, R.id.edt_publish_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edt_publish_price:
                showPriceDialog();
                break;
            case R.id.iv_publish_back:
                setResult(4000, getIntent());
                finish();
                break;
            case R.id.btn_publish:
                isNull(edtPublishNickname);
                nickName = edtPublishNickname.getText().toString();
                isNull(edtPublishWay);
                pubWay = edtPublishWay.getText().toString();
                isNull(edtPublishClass);
                pubClass = edtPublishClass.getText().toString();
                pubPrice = btnPublishPrice.getText().toString();
                if (TextUtils.isEmpty(pubPrice)) {
                    toast(btnPublishPrice.getHint().toString());
                    return;
                }
                isNull(edtPublishDate);
                pubDate = edtPublishDate.getText().toString();
                isNull(edtPublishAdress);
                pubAdress = edtPublishAdress.getText().toString();
                pubOther = edtPublishOther.getText().toString();
                SubPublish();
                break;
        }
    }

    private void isNull(TextView tv) {
        String text = tv.getText().toString();
        if (TextUtils.isEmpty(text)) {
            toast(tv.getHint().toString());
            return;
        }
    }

    //弹出价格选择
    private void showPriceDialog() {
        new AlertDialog.Builder(this)
                .setTitle("选择价格区间")
                .setItems(priceList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnPublishPrice.setText(priceList[i]);
                    }
                })
                .show();
    }

    //上传数据
    private void SubPublish() {
        List<Param> params = new ArrayList<>();
        params.add(new Param("user_id", PreferenceUtil.getString(SharedSaveConstant.User_Id, "")));
        params.add(new Param("nickname", nickName));
        params.add(new Param("subject", pubClass));
        params.add(new Param("price", pubPrice));
        params.add(new Param("contact", pubWay));
        params.add(new Param("address", pubAdress));
        params.add(new Param("class_time", pubDate));
        params.add(new Param("detail", pubOther));
        OkHttpUtils.post(apiServer.URL_Publish, new OkHttpUtils.ResultCallback<CommonBean1>() {
            @Override
            public void onSuccess(CommonBean1 data) {
                toast(data.getSummary());
                if (data.getCode() == 200) {
                    toast("等待审核");
                    setResult(5000, getIntent());
                    finish();
                }
            }

            @Override
            public void onFailure(Exception e) {

            }
        }, params);
    }

}
