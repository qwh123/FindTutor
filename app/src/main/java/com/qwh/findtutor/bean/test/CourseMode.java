package com.qwh.findtutor.bean.test;

import com.qwh.findtutor.ui.activity.DropMenu.entity.FilterType;

import java.util.ArrayList;
import java.util.List;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 15:52
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class CourseMode {

    public List<FilterType> getFilterType() {
        List<FilterType> list = new ArrayList<>();
        list.add(new FilterType(getFilterDesc().get(0), getFilterChild1()));
        list.add(new FilterType(getFilterDesc().get(1), getFilterChild2()));
        list.add(new FilterType(getFilterDesc().get(2), getFilterChild3()));
        list.add(new FilterType(getFilterDesc().get(3), getFilterChild4()));
        list.add(new FilterType(getFilterDesc().get(4), null));
        return list;
    }


    /**
     *
     * @return 课程总类别
     */
    public List<String> getFilterDesc() {
        List<String> desc = new ArrayList<>();
        desc.add("学前");
        desc.add("小学");
        desc.add("初中");
        desc.add("高中");
        desc.add("艺术");
        return desc;
    }
    /**
     *
     * @return 热门科目
     */
    public List<String> getFilterHot() {
        List<String> desc = new ArrayList<>();
        desc.add("小学语文");
        desc.add("小学英语");
        desc.add("初中物理");
        desc.add("高中化学");
        desc.add("高中英语");
        return desc;
    }

    //学前
    public List<String> getFilterChild1() {
        List<String> childList = new ArrayList<>();
        childList.add("亲子");
        childList.add("益智");
        childList.add("潜能开发");
        childList.add("启蒙");
        childList.add("感统训练");
        childList.add("创意");
        childList.add("手工");
        childList.add("幼儿陪读");
        return childList;
    }  //小学

    public List<String> getFilterChild2() {
        List<String> childList = new ArrayList<>();
        childList = new ArrayList<>();
        childList.add("全科");
        childList.add("语文");
        childList.add("数学");
        childList.add("英语");
        childList.add("奥数");
        childList.add("作文");
        childList.add("陪读");
        return childList;
    }  //初中

    public List<String> getFilterChild3() {
        List<String> childList = new ArrayList<>();
        childList.add("全科");
        childList.add("数理化");
        childList.add("中考心理辅导");
        childList.add("语文");
        childList.add("数学");
        childList.add("英语");
        childList.add("历史");
        childList.add("地理");
        childList.add("物理");
        childList.add("化学");
        return childList;
    }//高中

    public List<String> getFilterChild4() {
        List<String> childList = new ArrayList<>();
        childList.add("全科");
        childList.add("文综");
        childList.add("理综");
        childList.add("数理化");
        childList.add("高考心理辅导");
        childList.add("语文");
        childList.add("数学");
        childList.add("英语");
        childList.add("历史");
        childList.add("地理");
        childList.add("物理");
        childList.add("化学");
        return childList;
    }
}
