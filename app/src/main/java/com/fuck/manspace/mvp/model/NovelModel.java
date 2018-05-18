package com.fuck.manspace.mvp.model;

import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.data.SpaceConfigs;
import com.fuck.manspace.event.MVPCallBack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by MouShao on 2018/4/2.
 */

public class NovelModel {

    public void requestNovelList(final String herf, final MVPCallBack<ArrayList<HtmlBean>> mvpCallBack) {
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
                        // String picNumber = text.substring(text.length() - 4, text.length() - 2);
                        String title = text.substring(5, text.length()).toString();
                        listData.add(new HtmlBean(title, herf, "", data));
                    }
                    mvpCallBack.succeed(listData);
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpCallBack.failed("获取失败");
                }

            }
        }).start();
    }

    public void getNovelWebHtml(final HtmlBean bean, final MVPCallBack<String> mvpCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(SpaceConfigs.BaseUrl + bean.getHerf()).get();
                    doc.head().html("");

                    Element table = doc.select("div.novelContent").first();//获取名称为datatb1的表格  
                    table.attr("width", "100%");
                    table.attr("padding", "10");
                    StringBuffer buffer = new StringBuffer("<html><body><font size=\"10\"><div >");
                    buffer.append(table.text().replace(" 　　","<br><br>").replace(" ","<br><br>"));
                    buffer.append("</div></font></body></html>");

                    mvpCallBack.succeed(buffer.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
