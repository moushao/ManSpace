package com.fuck.manspace.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.fuck.manspace.adapter.VBaseAdapter;
import com.fuck.manspace.base.BaseFragment;
import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.data.SpaceData;
import com.fuck.manspace.event.ItemListener;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.fuck.manspace.mvp.presenter.NovelPresenter;
import com.fuck.manspace.mvp.presenter.PicZonePresent;
import com.fuck.manspace.mvp.view.PicZoneView;
import com.fuck.manspace.ui.PicShowActivity;
import com.pvirtech.androidlib.R;
import com.fuck.manspace.adapter.PicZoneHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by MouShao on 2018/3/26.
 */

public class PicZoneFragment extends BaseFragment implements PicZoneView {
    @BindView(R.id.pic_zone_tab) TabLayout mPicZoneTab;
    @BindView(R.id.pic_zone_refresh) SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.pic_zone_rcyc) RecyclerView mPicZoneRcyc;

    public static final String TAG = "PicZoneFragment";

    private Context mContext;
    private VBaseAdapter picAdapter;
    private int picTypePosition = 0;
    private int page = 1;

    @Override
    public BasePresenter getPresenter() {
        return new PicZonePresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pic_zone;
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
        requestPicture(1);
    }

    private void requestPicture(int page) {
        String herf = SpaceData.BaseData.getPicZone().get(picTypePosition)
                .getHerf();
        if (page != 1) {
            herf = herf + page + ".htm";
        }
        ((PicZonePresent) mPresenter).requestPictureList(herf);
    }

    private void initRefresh() {
        mRefreshLayout.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                requestPicture(page);
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                page = 1;
                requestPicture(page);
            }
        });
    }

    private void initTabLayout() {
        for (int i = 0; i < SpaceData.BaseData.getPicZone().size(); i++) {
            mPicZoneTab.addTab(mPicZoneTab.newTab().setText(SpaceData.BaseData.getPicZone().get(i).getTilte()), i);
        }

        mPicZoneTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                picTypePosition = tab.getPosition();
                picAdapter.clear();
                picAdapter.notifyDataSetChanged();
                requestPicture(1);
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
        mPicZoneRcyc.setLayoutManager(virtualLayoutManager);
        final RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        pool.setMaxRecycledViews(0, 10);
        mPicZoneRcyc.setRecycledViewPool(pool);

        DelegateAdapter delegateAdapter = new DelegateAdapter(virtualLayoutManager, false);

        picAdapter = new VBaseAdapter<HtmlBean>(mContext)
                .setData(new ArrayList<HtmlBean>())
                .setLayout(R.layout.item_pic_zone)
                .setHolder(PicZoneHolder.class)
                .setLayoutHelper(new LinearLayoutHelper())
                .setListener(new ItemListener<HtmlBean>() {
                    @Override
                    public void onItemClick(View view, int position, HtmlBean data) {
                        PicShowActivity.startAction(mContext, TAG, data);
                    }
                });
        delegateAdapter.addAdapter(picAdapter);
        mPicZoneRcyc.setAdapter(delegateAdapter);
    }

    @Override
    protected void lazyLoadData() {

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
            mRefreshLayout.finishLoadmore(500);
        } else {
            mRefreshLayout.finishRefresh(500);
        }
    }

    @Override
    public void requestPicSuccess(final ArrayList<HtmlBean> mData) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (page == 1) {
                    //停止下拉刷新
                    mRefreshLayout.finishRefresh(500);
                    picAdapter.clear();
                    picAdapter.addAllData(mData);
                    picAdapter.notifyDataSetChanged();
                } else {
                    //停止上拉加载更多
                    int preSize = picAdapter.getItemCount();
                    mRefreshLayout.finishLoadmore(500);
                    picAdapter.addAllData(mData);
                    picAdapter.notifyItemRangeChanged(preSize, preSize + mData.size());
                    //picAdapter.notifyDataSetChanged();
                }

            }
        });
    }
}
