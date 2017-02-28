package com.qwh.findtutor.bean.test;

import java.util.List;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 15:30
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class TestBean {


    private int code;
    private String summary;
    /**
     * id : 33
     * icon : http://oacugwkqu.bkt.clouddn.com/Frc9h_AVaEis0p5yLad99-YfamoT
     * summary : 中华家训族规选萃之蔡氏家训
     * state : 1
     * create_time : 2016-09-03 11:52:07
     */

    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String icon;
        private String summary;
        private int state;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
