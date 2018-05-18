package com.fuck.manspace.bean;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/3/26.
 */

public class BaseHtml {
    //图区
    private ArrayList<HtmlBean> picZone ;
    //在线电影
    private ArrayList<HtmlBean> onlineMovie;
    //小说
    private ArrayList<HtmlBean> novel;
    //手机下载
    private ArrayList<HtmlBean> phoneMovie;

    public BaseHtml() {
        picZone = new ArrayList<>();
        onlineMovie = new ArrayList<>();
        novel = new ArrayList<>();
        phoneMovie = new ArrayList<>();
    }

    public void addVideoItem(HtmlBean bean) {
        this.onlineMovie.add(bean);
    }

    public void addPicZoneItem(HtmlBean bean) {
        this.picZone.add(bean);
    }

    public void addNovelItem(HtmlBean bean) {
        this.novel.add(bean);
    }

    public void addPhoneMovielItem(HtmlBean bean) {
        this.phoneMovie.add(bean);
    }

    //以下是get/set方法
    public ArrayList<HtmlBean> getPicZone() {
        return picZone;
    }

    public void setPicZone(ArrayList<HtmlBean> picZone) {
        this.picZone = picZone;
    }

    public ArrayList<HtmlBean> getOnlineMovie() {
        return onlineMovie;
    }

    public void setOnlineMovie(ArrayList<HtmlBean> onlineMovie) {
        this.onlineMovie = onlineMovie;
    }

    public ArrayList<HtmlBean> getNovel() {
        return novel;
    }

    public void setNovel(ArrayList<HtmlBean> novel) {
        this.novel = novel;
    }

    public ArrayList<HtmlBean> getPhoneMovie() {
        return phoneMovie;
    }

    public void setPhoneMovie(ArrayList<HtmlBean> phoneMovie) {
        this.phoneMovie = phoneMovie;
    }


}
