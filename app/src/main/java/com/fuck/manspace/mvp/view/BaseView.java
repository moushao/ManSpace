package com.fuck.manspace.mvp.view;

/**
 * MVP基础view
 */
public interface BaseView {

    void showLoadProgressDialog(String str);

    void disDialog();

    void showToast(String message);

    void onFailed(String message);

}