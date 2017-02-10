package com.qwh.findtutor.bean;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 15:58
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class CommentBean {
    private String id;
    private int icon;
    private String name;
    private String time;
    private String content;

    public CommentBean(String id, int icon, String name, String time, String content) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public String getContent() {
        return content;
    }
}
