package com.fuck.manspace.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.fuck.manspace.common.AppManager;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.fuck.manspace.mvp.view.BaseView;
import com.fuck.manspace.utils.ToastUtils;
import com.fuck.manspace.utils.permission.CheckPermListener;
import com.fuck.manspace.utils.permission.EasyPermissions;
import com.pvirtech.androidlib.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.naturs.library.statusbar.StatusBarHelper;
import me.naturs.library.statusbar.StatusBarUtil;


public abstract class BaseActivity<T extends BasePresenter<BaseView>> extends AppCompatActivity implements IBase{
    protected static final int RC_PERM = 123;
    public BasePresenter mPresenter;
    // public RxManager mRxManager;
    protected StatusBarHelper mStatusBarHelper;
    protected InputMethodManager inputManager;
    /**
     * 权限回调接口
     */
    private CheckPermListener mPermissionListener;
    /**
     * 传入标示符
     */
    public String fromTag;
    //    private LoadProgressDialog progressDialog;
    public static boolean isNewCar = false;
    // private ProgressDialog progressDialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        AppManager.getAppManager().addActivity(this);
        // mRxManager = new RxManager();
        //        setBaseConfig();
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //        onTintStatusBar();
        //        SetStatusBarColor();
        mPresenter = getPresenter();
        initInjector();
        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.attach((BaseView) this);
        }
        onSaveState(savedInstanceState);
        //注册一个监听连接状态的listener
        initEventAndData();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        if (mPresenter != null && this instanceof BaseView) {
            mPresenter.detachView();
            mPresenter = null;
        }
        //mRxManager.clear();
        unbinder.unbind();
        AppManager.getAppManager().finishActivity(this);
        disLoadDialog();
        ToastUtils.relase();
        super.onDestroy();
    }

    public void onSaveState(Bundle savedInstanceState) {
    }


    private void setBaseConfig() {
        initTheme();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //          SetStatusBarColor();
        //        SetStatusBarColor(R.color.colorAccent);
    }
    
    public void onTintStatusBar() {
        if (mStatusBarHelper == null) {
            mStatusBarHelper = new StatusBarHelper(this, StatusBarHelper.LEVEL_19_TRANSLUCENT, StatusBarHelper
                    .LEVEL_21_VIEW);
        }
        //        //状态栏的设置
        mStatusBarHelper.setColor(getResources().getColor(R.color.actionsheet_blue));
     }

     /**
     * 获取布局
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected abstract void initInjector();

    /**
     * 设置监听
     */
    protected abstract void initEventAndData();

    private void initTheme() {
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void SetStatusBarColor() {
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.actionsheet_blue));
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    public void SetStatusBarColor(int color) {
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(color));
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar() {
        StatusBarUtil.translucentStatusBar(this, false);
    }


    public void initToolBarConfig() {
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上  
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            decorView.setFitsSystemWindows(true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0  
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }


    public void showLoading(String string) {
        //        progressDialog = ProgressDialog.getInstance(this);
        //        progressDialog.setMessage(TextUtils.isEmpty(string) ? "加载中..." : string);
        //        progressDialog.show();
    }

    public void disLoadDialog() {
        //        if (progressDialog != null) {
        //            progressDialog.dismissHUD();
        //        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void showToast(String message) {
        ToastUtils.showToast(BaseActivity.this, message);
    }

    public void showToast(int ResID) {
        ToastUtils.showToast(BaseActivity.this, ResID);
    }


    public void showBaseMessageDialog(final String message) {
        if (TextUtils.isEmpty(message))
            return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            /*    new AlertDialog(BaseActivity.this).setWidthRatio(0.7f).builder()
                        .hideTitleLayout().setMsg(message).setNegativeButton(("确 定"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).setMessageGravity(Gravity.CENTER).show();*/
            }
        });
    }

    /**
     * hide
     */
    protected void hideKeyboard() {
        if (this.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams
                .SOFT_INPUT_STATE_HIDDEN) {
            if (this.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void checkPermission(CheckPermListener listener, int resString, String... mPerms) {
        mPermissionListener = listener;
        if (EasyPermissions.hasPermissions(this, mPerms)) {
            if (mPermissionListener != null)
                mPermissionListener.superPermission();
        } else {
            EasyPermissions.requestPermissions(this, getString(resString), RC_PERM, mPerms);
        }
    }

    /**
     * 用户权限处理,
     * 如果全部获取, 则直接过.
     * 如果权限缺失, 则提示Dialog.
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

  

}
