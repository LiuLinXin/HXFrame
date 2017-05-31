package com.csd.activitybase.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.MotionEvent;

/**
 * Created by Sober_philer on 2017/3/1 09:35
 */

public abstract class BasePlugin {

    protected PluginActivity activity;
    public void onResume() {

    }

    public void onStart() {
    }

    public void onRestart() {
    }

    public void onPause(){

    }

    protected Activity getParentActivity(){
        Activity ac = activity;
        while (ac.getParent() != null){
            ac = ac.getParent();
        }
        return ac;
    }

    public void onstop() {
    }

    public void onDestroy() {
    }

    public void onConfigurationChanged() {
    }

    public void bindActivity(PluginActivity activity){
        this.activity = activity;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){}

    /**
     * 是否需要拦截系统的回退键
     * @return true 拦截
     */
    public boolean onBackPress() {
        return false;
    }
}
