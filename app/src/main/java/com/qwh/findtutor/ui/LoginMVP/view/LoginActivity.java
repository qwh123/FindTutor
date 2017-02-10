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
import com.qwh.findtutor.ui.LoginMVP.LoginContract;
import com.qwh.findtutor.ui.LoginMVP.presenter.UserPresenter;
import com.qwh.findtutor.ui.MainActivity;
import com.qwh.findtutor.ui.activity.RegisterActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginContract.IUserView {


    @Bind(R.id.edt_login_account)
    EditText edtLoginAccount;
    @Bind(R.id.edt_login_pwd)
    EditText edtLoginPwd;
    @Bind(R.id.radiogroup_login)
    RadioGroup radioGroup;
    @Bind(R.id.login_progress)
    ProgressBar loginProgress;
    String type = null;

    private UserPresenter presenter;


    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {

        presenter = new UserPresenter(LoginActivity.this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int rbtnId = radioGroup.getCheckedRadioButtonId();
                RadioButton rbtn = (RadioButton) findViewById(rbtnId);
                type = rbtn.getText().toString();
            }
        });
    }

    @Override
    public void getData() {

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
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            try {
                String account = data.getExtras().getString("account");
                String pwd = data.getExtras().getString("pwd");
                setAccountText(account);
                setPwdText(pwd);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setAccountText(String text) {
        edtLoginAccount.setText(text);
    }

    @Override
    public void setPwdText(String text) {
        edtLoginPwd.setText(text);
    }

    @Override
    public String getLoginType() {
        return type;
    }

    @OnClick({R.id.btn_login, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                InputMethodManager inputMeMana = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMeMana.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                presenter.login(edtLoginAccount.getText().toString(), edtLoginPwd.getText().toString(), type);
                break;
            case R.id.btn_register:
                presenter.toRegister();
                break;
        }
    }
}
