package com.fuck.manspace.mvp.view;

import com.fuck.manspace.bean.HtmlBean;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/4/2.
 */

public interface NovelView extends BaseView {

    void requestNovelSuccess(ArrayList<HtmlBean> mData);

    void getNovelWebHtml(String mData);
}
