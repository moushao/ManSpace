package com.fuck.manspace.mvp.model;

import android.util.Log;

import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.data.SpaceConfigs;
import com.fuck.manspace.event.MVPCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Created by MouShao on 2018/3/27.
 */

public class PicZoneModel {

    public void requestPictureList(final String herf, final MVPCallBack<ArrayList<HtmlBean>> mvpCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(SpaceConfigs.BaseUrl + herf).get();
                    Elements tables = doc.select("ul.textList a");//获取名称为datatb1的表格  
                    ArrayList<HtmlBean> listData = new ArrayList<>();
                    for (Element element : tables) {
                        String herf = element.attr("href");
                        String text = element.text();
                        String data = text.substring(0, 4);
                        String picNumber = text.substring(text.length() - 4, text.length() - 2);
                        String title = text.substring(5, text.length() - 6).replace("[原创]", "原创·")
                                .replace("[", "").replace("]", "").toString();
                        listData.add(new HtmlBean(title, herf, picNumber,data));
                    }
                    mvpCallBack.succeed(listData);
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpCallBack.failed("获取失败");
                }

            }
        }).start();
    }
}
