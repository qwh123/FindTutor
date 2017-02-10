package com.qwh.findtutor.bean;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 14:19
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class TutorBean {
    private int img;
    private String name;
    private String type;
    private String summary;

    public TutorBean() {
    }

    public TutorBean(int img, String name, String type, String summary) {
        this.img = img;
        this.name = name;
        this.type = type;
        this.summary = summary;
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
}
