package com.fuck.manspace.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.fuck.manspace.adapter.NovelHolder;
import com.fuck.manspace.adapter.VBaseAdapter;
import com.fuck.manspace.base.BaseFragment;
import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.data.SpaceData;
import com.fuck.manspace.event.ItemListener;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.fuck.manspace.mvp.presenter.NovelPresenter;
import com.fuck.manspace.mvp.view.NovelView;
import com.fuck.manspace.ui.NovelReadActivity;
import com.pvirtech.androidlib.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by MouShao on 2018/3/26.
 */

public class NovelFragment extends BaseFragment implements NovelView {
    public static final String TAG = "NovelFragment";
    @BindView(R.id.novel_tab) TabLayout mNovelTab;
    @BindView(R.id.novel_rcyc) RecyclerView mNovelRcyc;
    @BindView(R.id.novel_refresh) SmartRefreshLayout mNovelRefresh;
    private int novelTypePosition;
    private VBaseAdapter novelAdapter;
    private int page =1;
    private Context mContext;

    @Override
    public BasePresenter getPresenter() {
        return new NovelPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_novel;
    }

    @Override
    protected void initInjector() {
        mContext = getActivity();
    }

    @Override
    protected void initEventAndData() {
        initTabLayout();
        initRefresh();
        initRcyclerView();

    }

    @Override
    protected void lazyLoadData() {
        requestNovel(1);
    }


    private void requestNovel(int i) {
        String herf = SpaceData.BaseData.getNovel().get(novelTypePosition)
                .getHerf();
        if (page != 1) {
            herf = herf + page + ".htm";
        }
        ((NovelPresenter) mPresenter).requestNovelList(herf);
    }

    private void initRefresh() {
        mNovelRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestNovel(page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestNovel(page);
            }
        });
    }

    private void initTabLayout() {
        for (int i = 0; i < SpaceData.BaseData.getNovel().size(); i++) {
            mNovelTab.addTab(mNovelTab.newTab().setText(SpaceData.BaseData.getNovel().get(i).getTilte()), i);
        }

        mNovelTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                novelTypePosition = tab.getPosition();
                novelAdapter.clear();
                novelAdapter.notifyDataSetChanged();
                requestNovel(1);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initRcyclerView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(mContext);
        mNovelRcyc.setLayoutManager(virtualLayoutManager);
        final RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(0, 10);
        mNovelRcyc.setRecycledViewPool(pool);

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);
        novelAdapter = new VBaseAdapter<HtmlBean>(mContext)
                .setData(new ArrayList<HtmlBean>())
                .setLayout(R.layout.item_pic_zone)
                .setHolder(NovelHolder.class)
                .setLayoutHelper(new LinearLayoutHelper())
                .setListener(new ItemListener<HtmlBean>() {
                    @Override
                    public void onItemClick(View view, int position, HtmlBean data) {
                        NovelReadActivity.startAction(mContext, TAG, data);
                    }
                });

        delegateAdapter.addAdapter(novelAdapter);
        mNovelRcyc.setAdapter(delegateAdapter);
    }

    @Override
    public void showLoadProgressDialog(String str) {
        
    }

    @Override
    public void disDialog() {

    }



    @Override
    public void onFailed(String message) {
        if (page != 1) {
            page--;
            mNovelRefresh.finishLoadmore(500);
        } else {
            mNovelRefresh.finishRefresh(500);
        }
    }

    @Override
    public void requestNovelSuccess(final ArrayList<HtmlBean> mData) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (page == 1) {
                    //停止下拉刷新
                    mNovelRefresh.finishRefresh(500);
                    novelAdapter.clear();
                    novelAdapter.addAllData(mData);
                    novelAdapter.notifyDataSetChanged();
                } else {
                    //停止上拉加载更多
                    int preSize = novelAdapter.getItemCount();
                    mNovelRefresh.finishLoadmore(500);
                    novelAdapter.addAllData(mData);
                    novelAdapter.notifyItemRangeChanged(preSize, preSize + mData.size());
                    //picAdapter.notifyDataSetChanged();
                }

            }
        });
    }

    @Override
    public void getNovelWebHtml(String mData) {
        
    }


}
