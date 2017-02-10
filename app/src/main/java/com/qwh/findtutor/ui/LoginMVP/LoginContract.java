package com.qwh.findtutor.ui.LoginMVP;

import com.qwh.findtutor.ui.LoginMVP.bean.UserBean;

/**
 * com.qwh.findtutor.ui.LoginMVP
 * 开发者 qwh
 * 时间: 16:38
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public interface LoginContract {
    interface OnLoginListener {
        void loginSuccess(UserBean user);

        void LoginError();
    }

    interface OnGetUserListener {
        void getSuccess(UserBean user);

        void getFail();
    }

    interface IUserMode {

        void login(String userName, String pwd, String type,OnLoginListener listener);

        void getUserData(OnGetUserListener listener);
    }

    interface IUserView {
        void hideProgressBar();

        void showProgressBar();

        void showErrorMsg(String msg);

        void clearUserName();

        void clearPwd();

        void toIntentMain();

        void toIntentRegister();

        void setAccountText(String text);

        void setPwdText(String text);

        String getLoginType();
    }


    abstract class IPresenter {
        public IPresenter() {
        }

        public abstract void login(String userName, String pwd,String type);

        public abstract void toRegister();

        /**
         * 自动填充数据
         */
        public abstract void AutoFillEdt();


    }


}
