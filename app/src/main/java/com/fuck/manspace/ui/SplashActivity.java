package com.fuck.manspace.ui;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.fuck.manspace.base.BaseActivity;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.pvirtech.androidlib.R;

import butterknife.BindView;


public class SplashActivity extends BaseActivity {
    @BindView(R.id.splash_tv_timer) TextView mTvTimer;

    public final static String TAG = "SplashActivity";

    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_spalish;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initInjector() {
        mContext = this;
    }

    @Override
    protected void initEventAndData() {
        SetTranslanteBar();
        starCountDown();

    }

    private void starCountDown() {
        CountDownTimer timer = new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
                mTvTimer.setText(millisUntilFinished / 1000 + "秒");
            }

            public void onFinish() {
                MainActivity.startAction(mContext, TAG);
            }

        };
        timer.start();
    }

}
