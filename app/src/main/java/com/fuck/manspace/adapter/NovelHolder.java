package com.fuck.manspace.adapter;

import android.view.View;
import android.widget.TextView;

import com.fuck.manspace.bean.HtmlBean;
import com.pvirtech.androidlib.R;

import butterknife.BindView;

/**
 * Created by MouShao on 2018/3/27.
 */

public class NovelHolder extends VBaseHolder<HtmlBean> {
    @BindView(R.id.pic_zone_title) TextView novelTitle;
    @BindView(R.id.publis_time) TextView pubishTime;
    @BindView(R.id.pic_number) TextView mPicNum;

    public NovelHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void setData(int position, HtmlBean mData) {
        super.setData(position, mData);
        novelTitle.setText(mData.getTilte());
        pubishTime.setText(mData.getData());
        //mPicNum.setText(mData.getPicNumber()+"张");
    }

}
