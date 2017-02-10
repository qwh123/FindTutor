package com.qwh.findtutor.bean;

/**
 * com.qwh.findtutor.bean
 * 开发者 qwh
 * 时间: 14:36
 * 邮箱:2529509180@qq.com
 * 类作用：需求中心列表数据
 */

public class InfoBean {
    private int Id;
    private int img;
    private String pub_name;
    private String pub_class;
    private String pub_date;
    private String pub_price;
    private String pub_address;
    private String pub_other;
    private String pub_level;
    private int pub_type;

    public InfoBean(int id, int img, String pub_name, String pub_class, String pub_date, String pub_price, String pub_address, String pub_other, String pub_level, int pub_type) {
        Id = id;
        this.img = img;
        this.pub_name = pub_name;
        this.pub_class = pub_class;
        this.pub_date = pub_date;
        this.pub_price = pub_price;
        this.pub_address = pub_address;
        this.pub_other = pub_other;
        this.pub_level = pub_level;
        this.pub_type = pub_type;
    }

    public String getPub_level() {
        return pub_level;
    }

    public void setPub_level(String pub_level) {
        this.pub_level = pub_level;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getPub_name() {
        return pub_name;
    }

    public void setPub_name(String pub_name) {
        this.pub_name = pub_name;
    }

    public String getPub_class() {
        return pub_class;
    }

    public void setPub_class(String pub_class) {
        this.pub_class = pub_class;
    }

    public String getPub_date() {
        return pub_date;
    }

    public void setPub_date(String pub_date) {
        this.pub_date = pub_date;
    }

    public String getPub_price() {
        return pub_price;
    }

    public void setPub_price(String pub_price) {
        this.pub_price = pub_price;
    }

    public String getPub_address() {
        return pub_address;
    }

    public void setPub_address(String pub_address) {
        this.pub_address = pub_address;
    }

    public String getPub_other() {
        return pub_other;
    }

    public void setPub_other(String pub_other) {
        this.pub_other = pub_other;
    }

    public int getPub_type() {
        return pub_type;
    }

    public void setPub_type(int pub_type) {
        this.pub_type = pub_type;
    }
}
