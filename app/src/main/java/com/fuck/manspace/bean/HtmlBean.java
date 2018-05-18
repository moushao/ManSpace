package com.fuck.manspace.bean;

import java.io.Serializable;

/**
 * Created by MouShao on 2018/3/26.
 */

public class HtmlBean implements Serializable {
    //标题
    private String tilte;
    //跳转链接
    private String herf;
    //照片输了
    private String picNumber;
    //发布日期
    private String data;


    public HtmlBean(String tilte, String url, String picNumber, String data) {

        this.tilte = tilte;
        this.herf = url;

        this.picNumber = picNumber;
        this.data = data;
    }

    public String getTilte() {
        return tilte;
    }

    public void setTilte(String tilte) {
        this.tilte = tilte;
    }

    public String getHerf() {
        return herf;
    }

    public void setHerf(String herf) {

        this.herf = herf;
    }

    public String getPicNumber() {
        return picNumber;
    }

    public void setPicNumber(String picNumber) {
        this.picNumber = picNumber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
