package com.fuck.manspace.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fuck.manspace.base.BaseActivity;
import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.data.SpaceConfigs;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.fuck.manspace.mvp.presenter.NovelPresenter;
import com.fuck.manspace.mvp.presenter.PicShowPresenter;
import com.fuck.manspace.mvp.view.NovelView;
import com.pvirtech.androidlib.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NovelReadActivity extends BaseActivity implements NovelView {
    final static String TAG = "NovelReadActivity";
    @BindView(R.id.title_back) ImageView mTitleBack;
    @BindView(R.id.title_tv) TextView mTitleTv;
    @BindView(R.id.more) TextView mMore;
    @BindView(R.id.toolbar_novel) LinearLayout mToolBarLayout;
    @BindView(R.id.novel_webview) WebView mWebView;
    private Context mContext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_novel_read;
    }

    @Override
    protected void initInjector() {
        mContext = this;
    }

    @Override
    public BasePresenter getPresenter() {
        return new NovelPresenter();
    }

    @Override
    protected void initEventAndData() {
        initData();
    }

    private void initData() {
        HtmlBean bean = (HtmlBean) getIntent().getExtras().getSerializable(SpaceConfigs.HTML_BEAN);
        mTitleTv.setText(bean.getTilte());
        mTitleTv.setTextColor(getResources().getColor(R.color.colorAccent));
        mToolBarLayout.setBackgroundColor(getResources().getColor(R.color.transparent));

        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放 
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具 
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //mWebView.loadDataWithBaseURL("", getHtml(), "text/html", "utf-8", null);
        ((NovelPresenter) mPresenter).getNovelHtml(bean);

    }

    @Override
    public void showLoadProgressDialog(String str) {

    }

    @Override
    public void disDialog() {

    }

    @Override
    public void onFailed(String message) {

    }

    @Override
    public void requestNovelSuccess(ArrayList<HtmlBean> mData) {

    }

    @Override
    public void getNovelWebHtml(final String mData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadDataWithBaseURL("", mData, "text/html", "utf-8", null);
            }
        });
    }

    public static void startAction(Context mContext, String from, HtmlBean bean) {
        Intent itt = new Intent(mContext, NovelReadActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SpaceConfigs.FROM, from);
        bundle.putSerializable(SpaceConfigs.HTML_BEAN, bean);
        itt.putExtras(bundle);
        mContext.startActivity(itt);
    }
}
