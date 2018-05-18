package com.fuck.manspace.common;

import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.fuck.manspace.utils.CrashHandler;
import com.fuck.manspace.utils.SharedPreferencesHelper;

import java.io.File;
import java.util.Iterator;
import java.util.List;

/**
 * 类名: MyApplication
 * <br/> 功能描述:
 * <br/> 作者: MouTao
 * <br/> 时间: 2017/6/21
 */

public class MyApplication extends Application {
    /**
     * 系统通知栏提醒
     */
    public static NotificationManager notifyManager = null;
    private static MyApplication mMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication = this;
        CrashHandler.getInstance().init(mMyApplication);
    }




    public String getCachePath() {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
            cacheDir = getExternalCacheDir();
        else
            cacheDir = getCacheDir();
        if (cacheDir != null && !cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }


    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return processName;
    }

    public static MyApplication getApplication() {
        return mMyApplication;
    }

    @Override
    public void onTerminate() {
        if (this.notifyManager != null) {
            notifyManager.cancelAll();
        }
        super.onTerminate();
    }

    /**
     * 获取应用的版本号
     *
     * @return 应用版本号，默认返回1
     */
    public static int getAppVersionCode() {
        Context context = mMyApplication.getApplicationContext();
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
}


