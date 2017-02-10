package com.qwh.findtutor.ui.LoginMVP.mode;

import com.qwh.findtutor.ui.LoginMVP.LoginContract;
import com.qwh.findtutor.ui.LoginMVP.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * com.qwh.findtutor.ui.LoginMVP.mode
 * 开发者 qwh
 * 时间: 16:02
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class UserMode implements LoginContract.IUserMode {
    @Override
    public void login(final String userName, final String pwd,String type, final LoginContract.OnLoginListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //模拟登陆
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (userName.equals("123456") && pwd.equals("123")) {
                    UserBean bean = new UserBean();
                    bean.setUserName(userName);
                    bean.setPsw(pwd);
                    listener.loginSuccess(bean);
                } else {
                    listener.LoginError();
                }

            }
        }).start();

    }

    @Override
    public void getUserData(LoginContract.OnGetUserListener listener) {
        List<UserBean> userBeen = getData();
        if (userBeen.size() > 0)
            listener.getSuccess(userBeen.get(userBeen.size() - 1));
    }

    /**
     * 添加模拟数据
     */
    private List<UserBean> getData() {
        List<UserBean> user = new ArrayList<>();
        user.add(new UserBean("001", "18850105250", "123456"));
        user.add(new UserBean("002", "123456", "123456"));
        return user;
    }

}
