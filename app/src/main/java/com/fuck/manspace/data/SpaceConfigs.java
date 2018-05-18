package com.fuck.manspace.data;


import android.os.Environment;

import java.io.File;

/**
 * 类名: {@link SpaceConfigs}
 * <br/> 功能描述: 存储全局数据的基本类
 */
public class SpaceConfigs {
    /**
     * 从哪儿来
     */
    public static String FROM = "";


    /**
     * 手机存储路径
     * Environment.getExternalStorageDirectory();
     */
    public final static String PHONE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File
            .separator + "ManSpace" + File.separator;

    /**
     * 基础连接
     */
    public static String BaseUrl = "https://www.816aa.com";

    /**
     * DiskCache
     */
    public static final long CACHE_MAXSIZE = 10 * 1024 * 1024;
    public static String HTML_BEAN = "HTML_BEAN";
}
