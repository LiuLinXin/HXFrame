package com.csd.activitybase.activity;

import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.csd.activitybase.utils.ToastUtils;

import org.xutils.x;

/**
 * Created by Sober_philer on 2017/3/2 10:34
 */

public class HApplication extends Application {
    private static HApplication baseApplication;



    public static HApplication getInstance() {
        return baseApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
    }

    private void initPlugin(){
        x.Ext.init(this);
        ToastUtils.getInstance().init(this);
    }
}
