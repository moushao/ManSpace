package com.fuck.manspace.fragment;

import com.bumptech.glide.Glide;
import com.fuck.manspace.base.BaseFragment;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.pvirtech.androidlib.R;

import butterknife.BindView;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayerStandard;

/**
 * Created by MouShao on 2018/3/26.
 */

public class VideoFragment extends BaseFragment {
    @BindView(R.id.videoplayer) JZVideoPlayerStandard mVideoplayer;
    Unbinder unbinder;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initInjector() {

    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void lazyLoadData() {
        mVideoplayer.setUp("http://666.maomixia666.com:888/new/zw/2018-04/01/3wnge7dn.mp4"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "饺子闭眼睛");
        Glide.with(getActivity()).load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
                .into(mVideoplayer.thumbImageView);
    }

  
}
