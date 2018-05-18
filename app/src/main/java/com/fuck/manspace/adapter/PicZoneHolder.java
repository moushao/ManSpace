package com.fuck.manspace.adapter;

import android.view.View;
import android.widget.TextView;

import com.fuck.manspace.bean.HtmlBean;
import com.pvirtech.androidlib.R;

import butterknife.BindView;

/**
 * Created by MouShao on 2018/3/27.
 */

public class PicZoneHolder extends VBaseHolder<HtmlBean> {
    @BindView(R.id.pic_zone_title) TextView mPicTitle;
    @BindView(R.id.publis_time) TextView mTime;
    @BindView(R.id.pic_number) TextView mPicNum;

    public PicZoneHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void setData(int position, HtmlBean mData) {
        super.setData(position, mData);
        mPicTitle.setText(mData.getTilte());
        mTime.setText(mData.getData());
        mPicNum.setText(mData.getPicNumber()+"张");
    }

}
