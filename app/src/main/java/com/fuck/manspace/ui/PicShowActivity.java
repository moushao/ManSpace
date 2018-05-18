package com.fuck.manspace.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.fuck.manspace.adapter.PicShowHolder;
import com.fuck.manspace.adapter.VBaseAdapter;
import com.fuck.manspace.base.BaseActivity;
import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.data.SpaceConfigs;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.fuck.manspace.mvp.presenter.PicShowPresenter;
import com.fuck.manspace.mvp.view.PicShowView;
import com.pvirtech.androidlib.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

public class PicShowActivity extends BaseActivity implements PicShowView {
    @BindView(R.id.pic_show_rcyc) RecyclerView mPicShowRcyc;
    @BindView(R.id.title_back) ImageView mTitleBack;
    @BindView(R.id.show_pic_webview) WebView mWebView;
    @BindView(R.id.title_tv) TextView mTitleTv;
    @BindView(R.id.toolbar_pic_show) LinearLayout mToolBarLayout;
    private Context mContext;
    private List<HtmlBean> picList;
    private VBaseAdapter picShowAdapter;
    private DelegateAdapter delegateAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pic_show;
    }

    @Override
    public BasePresenter getPresenter() {
        return new PicShowPresenter();
    }

    @Override
    protected void initInjector() {
        mContext = this;
    }

    @Override
    protected void initEventAndData() {
        initData();
        //        initRcyclerView();
    }

    private void initData() {
        HtmlBean bean = (HtmlBean) getIntent().getExtras().getSerializable(SpaceConfigs.HTML_BEAN);
        mTitleTv.setText(bean.getTilte());
        mTitleTv.setTextColor(getResources().getColor(R.color.colorAccent));
        mToolBarLayout.setBackgroundColor(getResources().getColor(R.color.transparent));
        //((PicShowPresenter) mPresenter).getPictureList(bean.getHerf());
        /*WebSettings settings = mWebView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);*/
        //mWebView.loadUrl(SpaceConfigs.BaseUrl + bean.getHerf());
       /* picList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            //picList.add(new HtmlBean("hehe", "https://img.581gg.com/picdata-watermark/a1/331/33157-" + i + ".jpg"));
            picList.add(new HtmlBean("hehe", "https://timgsa.baidu" +
                    ".com/timg?image&quality=80&size=b9999_10000&sec=1522216619370&di" +
                    "=3a9375991c2dffe1693c878a60c93376&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg" +
                    ".com%2Fit%2Fu%3D2959979352%2C2142335837%26fm%3D214%26gp%3D0.jpg"));
        }*/

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
        ((PicShowPresenter) mPresenter).getHtml(bean);

    }

    private void initRcyclerView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        mPicShowRcyc.setLayoutManager(virtualLayoutManager);
        final RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        mPicShowRcyc.setRecycledViewPool(pool);
        pool.setMaxRecycledViews(0, 20);

        delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);

        picShowAdapter = new VBaseAdapter<String>(mContext)
                .setData(new ArrayList<String>())
                .setLayout(R.layout.item_pic_show)
                .setHolder(PicShowHolder.class)
                .setLayoutHelper(new LinearLayoutHelper());
        delegateAdapter.addAdapter(picShowAdapter);
        mPicShowRcyc.setAdapter(delegateAdapter);
        mPicShowRcyc.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mContext != null)
                    switch (newState) {
                        case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.  
                            //当屏幕停止滚动，加载图片  
                            Glide.with(mContext).resumeRequests();
                            break;
                        case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input 
                            // such  as user touch input.  
                            //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片  

                            Glide.with(mContext).pauseRequests();
                            break;
                        case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position 
                            // while  not under outside control.  
                            //由于用户的操作，屏幕产生惯性滑动，停止加载图片  
                            Glide.with(mContext).pauseRequests();
                            break;
                    }

            }
        });
    }

    @OnClick(R.id.title_back)
    public void onViewClicked() {

    }

    @Override
    public void showLoadProgressDialog(String str) {

    }


    @Override
    public void disDialog() {

    }

    @Override
    public void onFailed(String message) {
        showToast(message);
    }

    @Override
    public void getPictureListSuccess(ArrayList<String> picList) {
        picShowAdapter.addAllData(picList);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                picShowAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getWebHtml(final String picList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadDataWithBaseURL("", picList, "text/html", "utf-8", null);
            }
        });
    
    }

    public static void startAction(Context mContext, String from, HtmlBean bean) {
        Intent itt = new Intent(mContext, PicShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SpaceConfigs.FROM, from);
        bundle.putSerializable(SpaceConfigs.HTML_BEAN, bean);
        itt.putExtras(bundle);
        mContext.startActivity(itt);
    }

 
}
