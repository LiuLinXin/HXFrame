package com.csd.activitybase.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.csd.activitybase.activity.HApplication;

/**
 * Created by Sober_philer on 2017/3/1 11:20
 */

public class HeightWidthUtils {

    /**
     * @return int[0] 适配后高度 int[1] 适配后宽度
     */
    public static void videoHeigtWidth(float parentHeight, float parentWidth, float videoHeight, float videoWidth, View surfaceView) {
        int trueHeight, trueWidth;
        if (parentWidth / parentHeight < videoWidth / videoHeight) {
            trueWidth = (int) parentWidth;
            trueHeight = (int) (trueWidth * (videoHeight / videoWidth));
        } else {
            trueHeight = (int) parentHeight;
            trueWidth = (int) (trueHeight * (videoWidth / videoHeight));
        }
        ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
        layoutParams.height = trueHeight;
        layoutParams.width = trueWidth;
        surfaceView.setLayoutParams(layoutParams);
    }

    public static int dp2px(int dpValue) {
        final float scale = HApplication.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private static int statusBarHeight;
    public static int getStatusBarHeight(Context context){
        if(statusBarHeight==0){
            int resourceId =context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if(resourceId>0){
                statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return statusBarHeight;
    }
}
