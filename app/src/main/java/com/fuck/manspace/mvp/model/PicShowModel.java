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
 * Created by MouShao on 2018/3/28.
 */

public class PicShowModel {
    public void getPictureList(final String herf, final MVPCallBack<ArrayList<String>> mvpCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(SpaceConfigs.BaseUrl + herf).get();
                    Element table = doc.select("div.picContent").first();//获取名称为datatb1的表格  
                    Elements imgs = table.getElementsByTag("img");
                    ArrayList<String> listData = new ArrayList<>();
                    for (int i = 0; i < imgs.size(); i++) {
                        Element firstChild = imgs.get(i);
                        listData.add(firstChild.attr("src"));
                    }
                    mvpCallBack.succeed(listData);
                } catch (Exception e) {
                    e.printStackTrace();
                    mvpCallBack.failed("图片列表解析失败");
                }

            }
        }).start();
    }

    public void getWebHtml(final HtmlBean bean, final MVPCallBack<String> mvpCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(SpaceConfigs.BaseUrl + bean.getHerf()).get();
                    doc.head().html("");

                    Elements table = doc.select("div.picContent img");//获取名称为datatb1的表格  
                    table.attr("width", "100%");
                    table.attr("padding", "10");
                    StringBuffer buffer = new StringBuffer("<html><body>");

                    for (int i = 0; i < table.size(); i++) {
                        buffer.append(table.get(i));
                    }
                    buffer.append("</body></html>");


//                    table.get(0).html("");
//                    doc.select("div.position").html("");
                    mvpCallBack.succeed(buffer.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private String getHtml() {
        String dat1 = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, " +
                "minimum-scale=1.0, user-scalable=no\">" +
                "<style type=\"text/css\">\n" +
                "body\n" +
                "{ \n" +
                "background: " + "#FFFFFF" + " no-repeat fixed center; \n" +
                "color: " + "#353C46" +
                "}\n" +
                "</style>" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"app\" style=\"width:100%;height:100%;word-wrap: break-word;word-break:break-all;\">";
        String dat2 = "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>";


        String data = "<div style = \"width:100%\"class=\"picContent\">" +
                "<img style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-1.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-2.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-3.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-4.jpg\">"
                + "<br> "
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-5.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-6.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-7.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-8.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-9.jpg\">"
                + "<br>"
                + "<img  style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-10.jpg\">"
                + "<br>"
                + "<img style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-11.jpg\">"
                + "<br>"
                + "<img style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-12.jpg\">"
                + "<br>"
                + "<img style = \"width:100%\" src=\"https://img.581gg.com/picdata-watermark/a1/332/33224-13.jpg\">"
                + "<br>"
                + "</div>";
        return dat1 + data + dat2;
    }


}
