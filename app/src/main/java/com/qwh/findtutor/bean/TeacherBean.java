package com.qwh.findtutor.bean;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 11:28
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class TeacherBean {
    private String Id;
    private String name;
    private int img;
    private String teach_class;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTeach_class() {
        return teach_class;
    }

    public void setTeach_class(String teach_class) {
        this.teach_class = teach_class;
    }

    public String getTeach_adress() {
        return teach_adress;
    }

    public void setTeach_adress(String teach_adress) {
        this.teach_adress = teach_adress;
    }

    public String getTeach_level() {
        return teach_level;
    }

    public void setTeach_level(String teach_level) {
        this.teach_level = teach_level;
    }

    private String teach_adress;
    private String teach_level;

    public TeacherBean(String id,int img, String name,  String teach_class, String teach_adress, String teach_level) {
        Id = id;
        this.name = name;
        this.img = img;
        this.teach_class = teach_class;
        this.teach_adress = teach_adress;
        this.teach_level = teach_level;
    }
}
