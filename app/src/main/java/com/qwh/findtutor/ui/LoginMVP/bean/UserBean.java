package com.qwh.findtutor.ui.LoginMVP.bean;

/**
 * com.qwh.findtutor.ui.LoginMVP.mode
 * 开发者 qwh
 * 时间: 15:38
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class UserBean {
    private String Id;
    private String userName;
    private String psw;

    public UserBean() {
    }

    public UserBean(String id, String userName, String psw) {
        Id = id;
        this.userName = userName;
        this.psw = psw;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }
}
