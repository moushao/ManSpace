package com.fuck.manspace.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fuck.manspace.base.BaseActivity;
import com.fuck.manspace.bean.BaseHtml;
import com.fuck.manspace.bean.HtmlBean;
import com.fuck.manspace.data.SpaceConfigs;
import com.fuck.manspace.data.SpaceData;
import com.fuck.manspace.fragment.NovelFragment;
import com.fuck.manspace.fragment.PicZoneFragment;
import com.fuck.manspace.fragment.VideoFragment;
import com.fuck.manspace.mvp.presenter.BasePresenter;
import com.pvirtech.androidlib.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.main_content_page) ViewPager mContentPage;
    @BindView(R.id.main_tab_video_icon) ImageView mTabVideoIcon;
    @BindView(R.id.main_tab_video_tv) TextView mTabVideoTv;
    @BindView(R.id.main_tab_video) LinearLayout mTabVideo;
    @BindView(R.id.main_tab_piczone_icon) ImageView mTabPiczoneIcon;
    @BindView(R.id.main_tab_piczone_tv) TextView mTabPiczoneTv;
    @BindView(R.id.main_tab_piczone) LinearLayout mTabPiczone;
    @BindView(R.id.main_tab_novel_icon) ImageView mTabNovelIcon;
    @BindView(R.id.main_tab_novel_tv) TextView mTabNovelTv;
    @BindView(R.id.main_tab_novel) LinearLayout mTabNovel;
    private int lastIndex;
    private PagerAdapter pagerAdapter;
    private Context mContext;
    private BaseHtml baseData = new BaseHtml();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void initInjector() {
        mContext = this;
    }

    @Override
    protected void initEventAndData() {
        //        onTintStatusBar();
        SetStatusBarColor(R.color.black);
        requestData();


    }

    private void initFragment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Fragment> fragmentList = new ArrayList<>();
                fragmentList.add(new PicZoneFragment());
                fragmentList.add(new VideoFragment());
                fragmentList.add(new NovelFragment());
                pagerAdapter = new PagerAdapter(getSupportFragmentManager(), fragmentList);
                mContentPage.setAdapter(pagerAdapter);
                mContentPage.setOffscreenPageLimit(3);
                mContentPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {
                        changeIconState(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }
        });

    }

    /**
     * 底部导航栏的改变
     */
    private void changeIconState(int position) {
        if (lastIndex == position)
            return;
        switch (lastIndex) {
            case 0:
                mTabPiczoneIcon.setImageResource(R.drawable.icon_sex_un);
                mTabPiczoneTv.setTextColor(Color.parseColor("#8a8a8a"));
                break;
            case 1:

                mTabVideoIcon.setImageResource(R.drawable.icon_video_un);
                mTabVideoTv.setTextColor(Color.parseColor("#8a8a8a"));
                break;
            case 2:
                mTabNovelIcon.setImageResource(R.drawable.icon_novel_un);
                mTabNovelTv.setTextColor(Color.parseColor("#8a8a8a"));
                break;
        }
        switch (position) {
            case 0:
                mTabPiczoneIcon.setImageResource(R.drawable.icon_sex);
                mTabPiczoneTv.setTextColor(Color.parseColor("#1296db"));
                break;
            case 1:
                mTabVideoIcon.setImageResource(R.drawable.icon_video);
                mTabVideoTv.setTextColor(Color.parseColor("#1296db"));

                break;
            case 2:
                mTabNovelIcon.setImageResource(R.drawable.icon_novel);
                mTabNovelTv.setTextColor(Color.parseColor("#1296db"));
                break;
        }
        lastIndex = position;
    }

    private void requestData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect(SpaceConfigs.BaseUrl + "/htm/index.htm").get();
                    Element table = doc.select("div.layout1").first();//获取名称为datatb1的表格  
                    Elements ul = table.getElementsByTag("ul");
                    for (int i = 0; i < ul.size(); i++) {
                        Element firstChild = ul.get(i);
                        Elements elements = firstChild.getElementsByTag("a");
                        for (int j = 0; j < 6; j++) {
                            Element secondChild = elements.get(j);
                            String title = secondChild.text();
                            String herf = secondChild.attr("href");

                            //Log.e("test", "title:" + title + ",href:" + herf);
                            if (!herf.equals("/")) {
                                saveBaseData(i, title, herf);
                            }

                        }
                    }
                    SpaceData.BaseData = baseData;
                    initFragment();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void saveBaseData(int i, String title, String herf) {
        HtmlBean bean = new HtmlBean(title, herf, "数量", "日期");
        switch (i) {
            case 0:
                baseData.addVideoItem(bean);
                break;
            case 1:

                break;
            case 2:
            case 3:
                baseData.addPicZoneItem(bean);
                break;
            case 4:
                baseData.addNovelItem(bean);
                break;
            case 5:
                break;
            case 6:
                break;

        }
    }

    @OnClick({R.id.main_tab_video, R.id.main_tab_piczone, R.id.main_tab_novel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.main_tab_piczone:
                mContentPage.setCurrentItem(0);
                break;
            case R.id.main_tab_video:
                mContentPage.setCurrentItem(1);
                break;
            case R.id.main_tab_novel:
                mContentPage.setCurrentItem(2);
                break;
        }
    }


    private class PagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fList;

        public PagerAdapter(FragmentManager fm, ArrayList<Fragment> fList) {
            super(fm);
            this.fList = fList;
        }

        @Override
        public Fragment getItem(int position) {
            return fList.get(position);
        }


        @Override
        public int getCount() {
            return fList.size();
        }

    }

    public static void startAction(Context mContext, String from) {
        Intent itt = new Intent(mContext, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(SpaceConfigs.FROM, from);
        itt.putExtras(bundle);
        mContext.startActivity(itt);
    }
}
