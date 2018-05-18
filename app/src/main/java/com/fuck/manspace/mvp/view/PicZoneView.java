package com.fuck.manspace.mvp.view;

import com.fuck.manspace.bean.HtmlBean;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/3/27.
 */

public interface PicZoneView extends BaseView{
    void requestPicSuccess(ArrayList<HtmlBean> mData);
}
