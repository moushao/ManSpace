package com.fuck.manspace.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.fuck.manspace.bean.BitmapInfo;
import com.fuck.manspace.bean.HtmlBean;
import com.pvirtech.androidlib.R;

import java.util.HashMap;

import butterknife.BindView;

/**
 * Created by MouShao on 2018/3/27.
 */

public class PicShowHolder extends VBaseHolder<HtmlBean> {
    @BindView(R.id.item_image) ImageView mPicZoneImage;
    HashMap<Integer, BitmapInfo> sizeMap = new HashMap<>();
    RequestOptions op = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .placeholder(R.mipmap.ic_launcher)
            .priority(Priority.HIGH);

    public PicShowHolder(View itemView) {
        super(itemView);

    }

    @Override
    public void setData(final int position, HtmlBean herf) {
        super.setData(position, mData);
        //mPicZoneImage.setTag(position);

        Glide.with(mContext).load(herf.getHerf()).apply(op).into(mPicZoneImage);
        //GlideApp.with(mContext).load(herf).apply(op).transition(new DrawableTransitionOptions()
        // .crossFade
        // (300)).into(mPicZoneImage); 
        // requestBuilder.apply(op);
        /*Glide.with(mContext)
                .load(herf.getHerf())
                .apply(op)
                .transition(new DrawableTransitionOptions().crossFade(300))
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        if (!sizeMap.containsKey(position)) {
                            int width = resource.getIntrinsicWidth();
                            int height = resource.getIntrinsicHeight();
                            sizeMap.put(position, new BitmapInfo(width, height));
                            ViewGroup.LayoutParams params = mPicZoneImage.getLayoutParams();
                            params.height = mPicZoneImage.getWidth() * height / width;
                            mPicZoneImage.setLayoutParams(params);
                        } else {
                           
                        } 
                        if ((int) mPicZoneImage.getTag() == position) {
                            ViewGroup.LayoutParams params = mPicZoneImage.getLayoutParams();
                            BitmapInfo info = sizeMap.get(position);
                            params.height = mPicZoneImage.getWidth() * info.height / info.width;
                            mPicZoneImage.setLayoutParams(params);
                            mPicZoneImage.setImageDrawable(resource);
                        } else {
                            Log.e("test", "原来的位置" + mPicZoneImage.getTag() + ";现在的位置：" + position);
                        }
                        
                    }
                });*/


    }


}
