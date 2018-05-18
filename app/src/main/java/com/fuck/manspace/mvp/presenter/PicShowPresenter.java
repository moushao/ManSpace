package com.fuck.manspace.mvp.presenter;

import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.event.MVPCallBack;
import com.fuck.manspace.mvp.model.PicShowModel;
import com.fuck.manspace.mvp.view.PicShowView;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/3/28.
 */

public class PicShowPresenter extends BasePresenter<PicShowView> {
    PicShowModel mModel = new PicShowModel();

    public void getPictureList(String herf) {
        mModel.getPictureList(herf, new MVPCallBack<ArrayList<String>>() {

            @Override
            public void succeed(ArrayList<String> mData) {
                if (mView != null) {
                    mView.getPictureListSuccess(mData);
                }
            }

            @Override
            public void failed(String message) {
                    requestFailed(message);
            }
        });
    }
    public void getHtml(HtmlBean bean) {
        mModel.getWebHtml(bean, new MVPCallBack<String>() {

            @Override
            public void succeed(String mData) {
                if (mView != null) {
                    mView.getWebHtml(mData);
                }
            }

            @Override
            public void failed(String message) {
                    requestFailed(message);
            }
        });
    }

}
