package com.qwh.findtutor.bean.test;

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
    private String level;
    private String attitude;
    private String skill;

    public CommentBean(String id, int icon, String name, String time, String content, String level, String attitude, String skill) {
        this.id = id;
        this.icon = icon;
        this.name = name;
        this.time = time;
        this.content = content;
        this.level = level;
        this.attitude = attitude;
        this.skill = skill;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getAttitude() {
        return attitude;
    }

    public void setAttitude(String attitude) {
        this.attitude = attitude;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }
}
