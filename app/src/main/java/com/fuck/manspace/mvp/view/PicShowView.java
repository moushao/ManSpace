package com.fuck.manspace.mvp.view;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/3/28.
 */

public interface PicShowView extends BaseView {
    void getPictureListSuccess(ArrayList<String> picList);
    void getWebHtml(String picList);
}
