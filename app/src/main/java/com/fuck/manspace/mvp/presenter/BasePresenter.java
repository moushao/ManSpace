package com.fuck.manspace.mvp.presenter;


import com.fuck.manspace.mvp.view.BaseView;

public abstract class BasePresenter<T extends BaseView> {
    public T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }

    public void requestFailed(String message) {
        if (mView != null) {
            mView.disDialog();
            mView.onFailed(message);
        }
    }

}