package com.qwh.findtutor.bean;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 19:14
 * 邮箱:2529509180@qq.com
 * 类作用：用户选择头像
 */

public class HeadBean {
    private String Id;
    private int head;

    public HeadBean(String id, int head) {
        Id = id;
        this.head = head;
    }

    public int getHead() {
        return head;
    }

    public String getId() {
        return Id;
    }
}
