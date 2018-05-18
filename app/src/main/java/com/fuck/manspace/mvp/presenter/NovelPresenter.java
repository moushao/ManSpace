package com.fuck.manspace.mvp.presenter;

import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.event.MVPCallBack;
import com.fuck.manspace.mvp.model.NovelModel;
import com.fuck.manspace.mvp.view.NovelView;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/4/2.
 */

public class NovelPresenter extends BasePresenter<NovelView> {
    NovelModel mModel = new NovelModel();

 
    public void requestNovelList(String herf) {
        mModel.requestNovelList(herf, new MVPCallBack<ArrayList<HtmlBean>>() {
            @Override
            public void succeed(ArrayList<HtmlBean> mData) {
                if (mView != null) {
                    mView.requestNovelSuccess(mData);
                }
            }

            @Override
            public void failed(String message) {
                requestFailed(message);
            }
        });
    }

    public void getNovelHtml(HtmlBean bean) {
        mModel.getNovelWebHtml(bean, new MVPCallBack<String>() {

            @Override
            public void succeed(String mData) {
                if (mView != null) {
                    mView.getNovelWebHtml(mData);
                }
            }

            @Override
            public void failed(String message) {
                requestFailed(message);
            }
        });
    }
}
