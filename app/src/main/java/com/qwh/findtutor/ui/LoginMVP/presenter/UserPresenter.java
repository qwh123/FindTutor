package com.qwh.findtutor.ui.LoginMVP.presenter;

import android.os.Handler;
import android.text.TextUtils;

import com.qwh.findtutor.ui.LoginMVP.LoginContract;
import com.qwh.findtutor.ui.LoginMVP.bean.UserBean;
import com.qwh.findtutor.ui.LoginMVP.mode.UserMode;
import com.qwh.findtutor.ui.LoginMVP.view.LoginActivity;

/**
 * com.qwh.findtutor.ui.LoginMVP.presenter
 * 开发者 qwh
 * 时间: 15:48
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class UserPresenter extends LoginContract.IPresenter {

    private Handler mHandler = new Handler();
    private LoginContract.IUserMode mModel;
    private LoginContract.IUserView mView;

    public UserPresenter(LoginActivity mView) {
        this.mView = mView;
        this.mModel = new UserMode();
    }

    @Override
    public void login(String userName, String pwd,String type) {
        if (TextUtils.isEmpty(userName)) {
            mView.showErrorMsg("账号不能为空");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            mView.showErrorMsg("密码不能为空");
            return;
        } if (TextUtils.isEmpty(type)) {
            mView.showErrorMsg("请选择登陆身份");
            return;
        }
        mView.showProgressBar();
        mModel.login(userName, pwd, type,new LoginContract.OnLoginListener() {
            @Override
            public void loginSuccess(UserBean user) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        mView.toIntentMain();
                        mView.hideProgressBar();
                    }
                });
            }

            @Override
            public void LoginError() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mView.hideProgressBar();
                        mView.showErrorMsg("登录失败");
                    }
                });

            }
        });


    }

    @Override
    public void toRegister() {
        mView.toIntentRegister();
    }

    @Override
    public void AutoFillEdt() {
        mModel.getUserData(new LoginContract.OnGetUserListener() {
            @Override
            public void getSuccess(UserBean user) {
                mView.setAccountText(user.getUserName());
                mView.setPwdText(user.getPsw());
            }

            @Override
            public void getFail() {
//                mView.showErrorMsg();
            }
        });
    }

}
