package com.qwh.findtutor.bean.test;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 14:07
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class CourseBean {
    private String Id;
    private int img;
    private String name;
    private String type;
    private String summary;
    private String isJoin;

    public CourseBean(String id, int img, String name, String type, String summary, String isJoin) {
        Id = id;
        this.img = img;
        this.name = name;
        this.type = type;
        this.summary = summary;
        this.isJoin = isJoin;
    }


    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getSummary() {
        return summary;
    }

    public String getIsJoin() {
        return isJoin;
    }
}
