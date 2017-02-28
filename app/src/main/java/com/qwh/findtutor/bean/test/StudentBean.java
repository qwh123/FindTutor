package com.qwh.findtutor.bean.test;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 11:28
 * 邮箱:2529509180@qq.com
 * 类作用：
 */

public class StudentBean {
    private String Id;
    private int stu_img;
    private String stu_name;
    private String stu_sex;
    private String stu_tel;
    private String stu_adress;
    private String stu_other;
    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getStu_img() {
        return stu_img;
    }

    public void setStu_img(int stu_img) {
        this.stu_img = stu_img;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_sex() {
        return stu_sex;
    }

    public void setStu_sex(String stu_sex) {
        this.stu_sex = stu_sex;
    }

    public String getStu_tel() {
        return stu_tel;
    }

    public void setStu_tel(String stu_tel) {
        this.stu_tel = stu_tel;
    }

    public String getStu_adress() {
        return stu_adress;
    }

    public void setStu_adress(String stu_adress) {
        this.stu_adress = stu_adress;
    }

    public String getStu_other() {
        return stu_other;
    }

    public void setStu_other(String stu_other) {
        this.stu_other = stu_other;
    }



    public StudentBean(String id, int stu_img, String stu_name, String stu_sex, String stu_tel, String stu_adress, String stu_other) {
        Id = id;
        this.stu_img = stu_img;
        this.stu_name = stu_name;
        this.stu_sex = stu_sex;
        this.stu_tel = stu_tel;
        this.stu_adress = stu_adress;
        this.stu_other = stu_other;
    }

}
