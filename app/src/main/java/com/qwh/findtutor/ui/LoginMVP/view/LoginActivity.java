package com.qwh.findtutor.ui.LoginMVP.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.qwh.findtutor.R;
import com.qwh.findtutor.base.BaseActivity;
import com.qwh.findtutor.bean.SharedSaveConstant;
import com.qwh.findtutor.ui.LoginMVP.LoginContract;
import com.qwh.findtutor.ui.LoginMVP.presenter.UserPresenter;
import com.qwh.findtutor.ui.MainActivity;
import com.qwh.findtutor.ui.activity.RegisterActivity;
import com.qwh.findtutor.utils.PreferenceUtil;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.IUserView {


    @Bind(R.id.edt_login_account)
    EditText edtLoginAccount;
    @Bind(R.id.edt_login_pwd)
    EditText edtLoginPwd;
    @Bind(R.id.radiogroup_login)
    RadioGroup radioGroup;
    @Bind(R.id.rbtn_login_teacher)
    RadioButton rbtnTeacher;
    @Bind(R.id.rbtn_login_student)
    RadioButton rbtnStudent;
    @Bind(R.id.login_progress)
    ProgressBar loginProgress;
    String type;

    private UserPresenter presenter;


    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

        presenter = new UserPresenter(LoginActivity.this);
        presenter.AutoFillEdt();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int rbtnId = radioGroup.getCheckedRadioButtonId();
                if (rbtnId == R.id.rbtn_login_teacher) {
                    type = "1";
                } else {
                    type = "2";
                }
            }
        });
    }

    @Override
    public void getData() {
        initView();
    }

    @Override
    public void hideProgressBar() {
        if (null != loginProgress)
            loginProgress.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBar() {
        if (null != loginProgress)
            loginProgress.setVisibility(View.VISIBLE);
    }


    @Override
    public void showErrorMsg(String msg) {
        toast(msg);
    }


    @Override
    public void clearUserName() {
        edtLoginAccount.setText("");
    }

    @Override
    public void clearPwd() {
        edtLoginPwd.setText("");
    }

    @Override
    public void toIntentMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void toIntentRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }


    @Override
    public void setAccountText(String text) {
        edtLoginAccount.setText(text);
    }

    @Override
    public void setLoginType(String type) {
        if (!type.isEmpty())
            if (type.equals("1")) {
                rbtnTeacher.setChecked(true);
            } else
                rbtnStudent.setChecked(true);

    }

    @Override
    public void setPwdText(String text) {
        edtLoginPwd.setText(text);
    }

    @Override
    public String getLoginType() {
        return type;
    }

    @OnClick({R.id.btn_login, R.id.btn_login_register, R.id.iv_login_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                InputMethodManager inputMeMana = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMeMana.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                presenter.login(edtLoginAccount.getText().toString(), edtLoginPwd.getText().toString(), type);
                break;
            case R.id.btn_login_register:
                presenter.toRegister();
                break;
            case R.id.iv_login_back:
                finish();
                break;
        }
    }
}
