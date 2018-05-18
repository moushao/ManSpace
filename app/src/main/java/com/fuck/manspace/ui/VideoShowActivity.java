package com.fuck.manspace.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.fuck.manspace.base.BaseActivity;
import com.fuck.manspace.data.SpaceConfigs;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.pvirtech.androidlib.R;


public class VideoShowActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_show;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public static void startAction(Context mContext, String from) {
        Intent itt = new Intent(mContext, VideoShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SpaceConfigs.FROM, from);
        itt.putExtras(bundle);
        mContext.startActivity(itt);
    }
}
