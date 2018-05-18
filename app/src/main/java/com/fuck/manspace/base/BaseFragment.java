package com.fuck.manspace.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.fuck.manspace.mvp.view.BaseView;
import com.fuck.manspace.utils.ToastUtils;
import com.pvirtech.androidlib.R;

import java.lang.reflect.Field;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.naturs.library.statusbar.StatusBarHelper;
import me.naturs.library.statusbar.StatusBarUtil;


/**
 * Created by zwl on 16/9/30.
 */

public abstract class BaseFragment<T extends BasePresenter<BaseView>> extends Fragment implements IBase {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected StatusBarHelper mStatusBarHelper;
    protected Context mActivity;
    //是否可见状态
    private boolean isVisible;
    //View已经初始化完成
    private boolean isPrepared;
    //是否第一次加载完
    private boolean isFirstLoad = true;

    protected BasePresenter mPresenter;
    private View mView;
    //    private LoadProgressDialog progressDialog;
    private ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        this.mActivity = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            unbinder = ButterKnife.bind(this, mView);
            return mView;
        }
        isFirstLoad = true;
        //绑定View
        View view = null;
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        isPrepared = true;

        initInjector();//dagger2注解,子类实现initInjector()方法 进行inject()
        if (savedInstanceState != null) {
            initStateData(savedInstanceState);
        }
        mPresenter = getPresenter();
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.attach((BaseView) this);
        }
        //初始化事件和获取数据, 在此方法中获取数据不是懒加载模式
        initEventAndData();
        //在此方法中获取数据为懒加载模式,如不需要懒加载,请在initEventAndData获取数据,GankFragment有使用实例
        lazyLoad();
        mView = view;
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.detachView();
            mPresenter = null;
        }
        unbinder.unbind();
        ToastUtils.relase();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void initStateData(Bundle savedInstanceState) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad)
            return;
        isFirstLoad = false;
        lazyLoadData();
    }

    protected abstract int getLayoutId();

    protected abstract void initInjector();

    protected abstract void initEventAndData();

    protected abstract void lazyLoadData();

    public void onTintStatusBar() {
        if (mStatusBarHelper == null) {
            mStatusBarHelper = new StatusBarHelper(getActivity(), StatusBarHelper.LEVEL_19_TRANSLUCENT,
                    StatusBarHelper.LEVEL_21_VIEW);
        }
        //        //状态栏的设置
        mStatusBarHelper.setColor(getResources().getColor(R.color.transparent));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarUtil.setColor(getActivity(), ContextCompat.getColor(getActivity(), R.color.actionsheet_blue));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    public void SetStatusBarColor(int color) {
        StatusBarUtil.setStatusBarColor(getActivity(), color);
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarUtil.translucentStatusBar(getActivity(), false);
    }

    public void showLoading(String string) {
    }

    public void diLoadDialog() {
    }


    public void showToast(String message) {
        ToastUtils.showToast(getActivity(), message);
    }

    public void showToast(int ResID) {
        ToastUtils.showToast(getActivity(), ResID);
    }

    public void showBaseMessageDialog(final String message) {
        if (TextUtils.isEmpty(message))
            return;
        /*getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog(getActivity()).setWidthRatio(0.7f).setMessageGravity(Gravity.CENTER).builder()
                        .hideTitleLayout().setMsg(message).setNegativeButton(("确定"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
            }
        });*/
    }


}
