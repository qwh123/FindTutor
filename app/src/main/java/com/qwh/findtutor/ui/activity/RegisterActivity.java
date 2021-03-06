package com.qwh.findtutor.ui.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.bean.RegisterBean;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
import com.qwh.findtutor.utils.PreferenceUtil;
import com.qwh.findtutor.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {

    @Bind(R.id.edt_register_account)
    EditText edtRegisterAccount;
    @Bind(R.id.edt_register_new_pwd)
    EditText edtRegisterNewPwd;
    @Bind(R.id.edt_register_confirm_pwd)
    EditText edtRegisterConfirmPwd;
    @Bind(R.id.register_progress)
    ProgressBar registerProgress;
    @Bind(R.id.edt_register_yzm)
    EditText edtRegisterYzm;
    @Bind(R.id.radiogroup_register)
    RadioGroup radiogroupRegister;
    @Bind(R.id.btn_register_yzm)
    Button btnYzm;

    String registerType;
    int i = 30;
    private String account = null;
    private String pwd = null;
    private boolean isSub = false;//是否发送验证码
    private ProgressBar mProBar;

    @Override
    public int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        radiogroupRegister.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int rbtnId = radioGroup.getCheckedRadioButtonId();
                if (rbtnId == R.id.rbtn_register_teacher) {
                    registerType = "1";
                } else {
                    registerType = "2";
                }
            }
        });

        // 启动短信验证sdk
        SMSSDK.initSDK(this, "10459c656d54e", "dd0d3289d8050c819010bdfeb33ab5f1");
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);


    }

    @Override
    public void getData() {
        initView();
    }

    @OnClick({R.id.btn_register, R.id.iv_register_back, R.id.btn_register_yzm})
    public void onClick(View view) {
        account = edtRegisterAccount.getText().toString();
        pwd = edtRegisterNewPwd.getText().toString();
        String configPwd = edtRegisterConfirmPwd.getText().toString();
        String yzm = edtRegisterYzm.getText().toString();
        switch (view.getId()) {
            case R.id.iv_register_back:
                finish();

                break;
            case R.id.btn_register:
                if (TextUtils.isEmpty(account)
                        || TextUtils.isEmpty(pwd)
                        || TextUtils.isEmpty(configPwd)
                        || TextUtils.isEmpty(yzm)
                        || TextUtils.isEmpty(registerType)) {
                    toast("所有输入内容均不能为空");
                    return;
                }
                if (!isSub) {
                    toast("验证码错误");
                    return;
                }
                SMSSDK.submitVerificationCode("86", account, yzm);
                createProgressBar();

                break;
            case R.id.btn_register_yzm:
                if (TextUtils.isEmpty(account)) {
                    toast("号码不能为空");
                    return;
                }
                // 1. 通过规则判断手机号
                if (!Utils.judgePhoneNums(account)) {
                    return;
                } // 2. 通过sdk发送短信验证
                SMSSDK.getVerificationCode("86", account);
                isSub = true;
                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                btnYzm.setClickable(false);
                btnYzm.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();


                break;
        }
    }

    /**
     *
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btnYzm.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                btnYzm.setText("获取验证码");
                btnYzm.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        toRegister();

                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        toast("验证码已经发送");
                    } else {
                        toast("验证码不一致");
                        if (mProBar != null)
                            mProBar.setVisibility(View.GONE);
                    }
                }
            }
        }
    };

    private void toRegister() {
        List<Param> mList = new ArrayList<>();
        mList.add(new Param("tel", account));
        mList.add(new Param("password", pwd));
        mList.add(new Param("type", registerType));
        OkHttpUtils.post(apiServer.URL_Register, new OkHttpUtils.ResultCallback<RegisterBean>() {
            @Override
            public void onSuccess(RegisterBean response) {
                toast(response.getSummary());
                if (response.getCode() == 200) {
                    PreferenceUtil.commitString(SharedSaveConstant.User_Account, account);
                    PreferenceUtil.commitString(SharedSaveConstant.User_Pwd, pwd);
                    PreferenceUtil.commitString(SharedSaveConstant.User_Type, registerType);
                    finish();
                }
                if (mProBar != null)
                    mProBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Exception e) {

            }
        }, mList);
    }


    /**
     * progressbar
     */
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }


}
