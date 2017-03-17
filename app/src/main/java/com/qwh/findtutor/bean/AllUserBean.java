package com.qwh.findtutor.bean;

import java.util.List;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 11:36
 * 邮箱:2529509180@qq.com
 * 类作用：获取用户接口
 */

public class AllUserBean {

    /**
     * code : 200
     * summary : get resources success
     * data : {"user":[{"id":"7","tel":"456","password":"d9b1d7db4cd6e70935368a1efb10e377","type":"2","token":"","nickname":"赵老师","sex":"0","contact":"18850105023","subject_id":"小学语文","education_bg":"高级教师  ","address":"福州","class_address":"旗山","detail":"我是一个美男子","icon":"","creat_time":"2017-03-13 22:23:38","state":"1"},{"id":"8","tel":"789","password":"d9b1d7db4cd6e70935368a1efb10e377","type":"2","token":"","nickname":"张同学","sex":"0","contact":null,"subject_id":"","education_bg":"高级教师  ","address":"福州","class_address":"","detail":"","icon":"","creat_time":"2017-03-13 22:23:34","state":"1"},{"id":"9","tel":"46464","password":"d9b1d7db4cd6e70935368a1efb10e377","type":"2","token":"","nickname":"","sex":"","contact":null,"subject_id":"","education_bg":"","address":"","class_address":"","detail":"","icon":"","creat_time":"2017-03-11 16:49:33","state":"1"},{"id":"10","tel":"123456","password":"d9b1d7db4cd6e70935368a1efb10e377","type":"2","token":"","nickname":"黄同学","sex":"0","contact":null,"subject_id":"","education_bg":"高级教师  ","address":"福州","class_address":"","detail":"","icon":"","creat_time":"2017-03-13 22:55:44","state":"1"},{"id":"11","tel":"18850105023","password":"d9b1d7db4cd6e70935368a1efb10e377","type":"2","token":"","nickname":"大锅饭","sex":"0","contact":null,"subject_id":"","education_bg":"高级教师  ","address":"福州","class_address":"仓山","detail":"","icon":"","creat_time":"2017-03-13 22:23:47","state":"1"}]}
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
        private List<UserBean> user;

        public List<UserBean> getUser() {
            return user;
        }

        public void setUser(List<UserBean> user) {
            this.user = user;
        }

        public static class UserBean {
            /**
             * id : 7
             * tel : 456
             * password : d9b1d7db4cd6e70935368a1efb10e377
             * type : 2
             * token :
             * nickname : 赵老师
             * sex : 0
             * contact : 18850105023
             * subject_id : 小学语文
             * education_bg : 高级教师
             * address : 福州
             * class_address : 旗山
             * detail : 我是一个美男子
             * icon :
             * creat_time : 2017-03-13 22:23:38
             * state : 1
             */

            private String id;
            private String tel;
            private String password;
            private String type;
            private String token;
            private String nickname;
            private String sex;
            private String contact;
            private String subject_id;
            private String education_bg;
            private String address;
            private String class_address;
            private String detail;
            private String icon;
            private String creat_time;
            private String state;

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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getContact() {
                return contact;
            }

            public void setContact(String contact) {
                this.contact = contact;
            }

            public String getSubject_id() {
                return subject_id;
            }

            public void setSubject_id(String subject_id) {
                this.subject_id = subject_id;
            }

            public String getEducation_bg() {
                return education_bg;
            }

            public void setEducation_bg(String education_bg) {
                this.education_bg = education_bg;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getClass_address() {
                return class_address;
            }

            public void setClass_address(String class_address) {
                this.class_address = class_address;
            }

            public String getDetail() {
                return detail;
            }

            public void setDetail(String detail) {
                this.detail = detail;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getCreat_time() {
                return creat_time;
            }

            public void setCreat_time(String creat_time) {
                this.creat_time = creat_time;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
