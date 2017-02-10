package com.qwh.findtutor.ui.activity.DropMenu.entity;

import java.util.List;

/**
 * author: baiiu
 * date: on 16/2/19 18:09
 * description:
 */
public class FilterType {
    private  String desc;
    private  List<String> child;

    public FilterType(String desc, List<String> child) {
        this.desc = desc;
        this.child = child;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getChild() {
        return child;
    }
}
