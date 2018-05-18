package com.fuck.manspace.utils;

import android.os.Environment;

import java.io.File;

/**
 * 类名: {@link SDCardStatu}
 * <br/> 功能描述: SD卡状态
 * <br/> 作者: MouTao
 * <br/> 时间: 2017/5/17
 * <br/> 最后修改者:
 * <br/> 最后修改内容:
 */
public class SDCardStatu {

    public static boolean isSDCardAvailable() {
        // 获得sd卡的状态
        String sdState = Environment.getExternalStorageState();
        // 判断SD卡是否存在
        return sdState.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 得到手机内存路径
     */
    public static String get_SdCard_Categories() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }


}