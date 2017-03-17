package com.qwh.findtutor.ui.LoginMVP.mode;

import com.qwh.findtutor.bean.Param;
import com.qwh.findtutor.http.OkHttpUtils;
import com.qwh.findtutor.http.apiServer;
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
    public void login(final String userName, final String pwd, String type, final LoginContract.OnLoginListener listener) {
        List<Param> mList = new ArrayList<>();
        mList.add(new Param("tel", userName));
        mList.add(new Param("password", pwd));
        mList.add(new Param("type", type));
        OkHttpUtils.post(apiServer.URL_Login, new OkHttpUtils.ResultCallback<UserBean>() {
            @Override
            public void onSuccess(UserBean response) {
                listener.loginSuccess(response);
            }

            @Override
            public void onFailure(Exception e) {
                listener.LoginError();
            }
        }, mList);

    }

}
