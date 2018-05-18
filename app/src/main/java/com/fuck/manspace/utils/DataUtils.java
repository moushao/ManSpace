package com.fuck.manspace.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类名: {@link DataUtils}
 * <br/> 功能描述: 时间格式化
 * <br/> 作者: MouTao
 * <br/> 时间: 2017/5/25
 */
public class DataUtils {
    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getCircleTime(String time) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");

        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format.format(date);
    }

    public static String getBirthday(String date) {
        Date oldYear = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        try {
            oldYear = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int NowYear = Integer.parseInt(format.format(new Date()));
        int birthYear = Integer.parseInt(format.format(oldYear));
        return (NowYear - birthYear) + "";
    }
}
