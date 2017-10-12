package com.spkj.supai.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.spkj.supai.R;
import com.zhy.autolayout.utils.AutoUtils;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by aa on 2017/5/5.
 */

public class ImagesUtils {

    public static void disImg(Context context, String imgUrl, ImageView imgv) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.mipmap.hoder)
                .error(R.mipmap.hoder)
                .into(imgv);
    }

    public static void disImg2(Context context, int imgUrl, ImageView imgv) {
        Glide.with(context)
                .load(imgUrl)
                .placeholder(R.mipmap.hoder)
                .error(R.mipmap.hoder)
                .into(imgv);
    }

    public static void disImgCircle(Context context, String imgUrl, ImageView imgv) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context)
                    .load(imgUrl)
                    .placeholder(R.mipmap.hoder)
                    .error(R.mipmap.hoder)
                    .transform(new GlideCircleTransform(context)).into(imgv);

        }
    }

    public static void disImgCircleNo(Context context, String imgUrl, ImageView imgv) {
        if (!TextUtils.isEmpty(imgUrl)) {
            Glide.with(context)
                    .load(imgUrl)
                    .transform(new GlideCircleTransform(context)).into(imgv);

        }
    }

    public static void disImgCircleNo2(Context context, int imgUrl, ImageView imgv) {
        Glide.with(context)
                .load(imgUrl)
                .transform(new GlideCircleTransform(context)).into(imgv);

    }

    public static void disImgCircle2(Context context, int imgUrl, ImageView imgv) {
        Glide.with(context).load(imgUrl).placeholder(R.mipmap.hoder).error(R.mipmap.hoder).transform(new GlideCircleTransform(context)).into(imgv);
    }

    public static void disImgRound(Context context, String imgUrl, ImageView imgv) {
        Glide.with(context).load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.hoder)
                .error(R.mipmap.hoder)
                .centerCrop().override(1090, 1090 * 3 / 4)
                .bitmapTransform(new RoundedCornersTransformation(context, AutoUtils.getPercentHeightSize(20), 0, RoundedCornersTransformation.CornerType.ALL))
                .crossFade().into(imgv);

    }
}
