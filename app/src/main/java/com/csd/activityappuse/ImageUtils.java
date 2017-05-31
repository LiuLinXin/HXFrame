package com.csd.activityappuse;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.csd.activitybase.activity.HApplication;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by sober_philer on 2017/5/17.
 */

public class ImageUtils {
    private static ImageUtils utils = new ImageUtils();
    private ImageUtils(){
        glideCircleTransform = new CropCircleTransformation(HApplication.getInstance());
        glideRoundTransform = new RoundedCornersTransformation(HApplication.getInstance(), 5, 0);
    }

    public static ImageUtils getInstance(){
        return utils;
    }
    private CropCircleTransformation glideCircleTransform;
    private RoundedCornersTransformation glideRoundTransform;

    public void bindImage(ImageView iv, String url){
        Glide.with(iv.getContext()).load(url).into(iv);
    }

    public void bindImageCircle(ImageView iv, String url){
        Glide.with(iv.getContext()).load(url).bitmapTransform(glideCircleTransform).into(iv);
    }

    public void bindImageRound(ImageView iv, String url){
        Glide.with(iv.getContext()).load(url).
                bitmapTransform(
                        new CenterCrop(HApplication.getInstance()),
                        glideRoundTransform).into(iv);
    }

    public void bindImageBlur(ImageView iv, String url){
        BlurTransformation blurTransformation = new BlurTransformation(iv.getContext());
        Glide.with(iv.getContext()).load(url).
                bitmapTransform(
                        new CenterCrop(iv.getContext()),
                        (blurTransformation)).into(iv);
    }
}
