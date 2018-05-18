package com.fuck.manspace.mvp.presenter;

import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.event.MVPCallBack;
import com.fuck.manspace.mvp.model.PicZoneModel;
import com.fuck.manspace.mvp.view.PicZoneView;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/3/27.
 */

public class PicZonePresent extends BasePresenter <PicZoneView> {
    PicZoneModel mModel = new PicZoneModel();
    
    public void requestPictureList(String herf) {
        mModel.requestPictureList(herf, new MVPCallBack<ArrayList<HtmlBean>>() {
            @Override
            public void succeed(ArrayList<HtmlBean> mData) {
                if (mView != null) {
                    mView.requestPicSuccess(mData);
                }
            }   

            @Override
            public void failed(String message) {
                requestFailed(message);
            }
        });
    }

   
}
