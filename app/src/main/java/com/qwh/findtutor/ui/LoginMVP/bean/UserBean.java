package com.qwh.findtutor.ui.LoginMVP.bean;

/**
 * com.qwh.findtutor.ui.LoginMVP.mode
 * 开发者 qwh
 * 时间: 15:38
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class UserBean {

    /**
     * code : 200
     * summary : login success
     * data : {"id":"","tel":"","password":"","type":"0","token":"","creat_time":"2017-02-20 23:00:48"}
     */

    private int code;
    private String summary;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id :
         * tel :
         * password :
         * type : 0
         * token :
         * creat_time : 2017-02-20 23:00:48
         */

        private String id;
        private String tel;
        private String password;
        private String type;
        private String token;
        private String creat_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCreat_time() {
            return creat_time;
        }

        public void setCreat_time(String creat_time) {
            this.creat_time = creat_time;
        }
    }
}
