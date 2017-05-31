package com.csd.activitybase.commenactivity;

import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;

import com.csd.activitybase.activity.PluginActivity;
import com.csd.hx.frame.R;

public class ActivityUserProtocol extends PluginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initParams(ViewParams params) {
        params.contentId = R.layout.activity_user_protocol;
        params.showTitle = true;
        params.showBack = true;
        params.titleId = R.string.userProduct;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            params.transition = new Fade();
        }
    }
}
