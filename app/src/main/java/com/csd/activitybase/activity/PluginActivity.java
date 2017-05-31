package com.csd.activitybase.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.csd.activitybase.utils.AppUtil;
import com.csd.activitybase.utils.HeightWidthUtils;
import com.csd.hx.frame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sober_philer on 2017/3/1 09:35
 */

public abstract class PluginActivity extends HttpActivity implements View.OnClickListener {
    List<BasePlugin> plugins = new ArrayList<>();
    ViewParams params = new ViewParams();
    protected View llTitle, ivBack;
    protected TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public <T extends BasePlugin> T findPlugin(Class<T> c) {
        for (BasePlugin p : plugins) {
            if (c.isInstance(p)) {
                return (T) p;
            }
        }
        return null;
    }

    protected void initView() {
        initParams(params);
        if (params.navBarTrans) {
            navBarTrans();
        }
        setContentView(params.contentId);
        transition(params.transition);
        boolean huawei = AppUtil.getSystem() == AppUtil.SYS_EMUI;
        if(huawei){
            getWindow().getDecorView().setFitsSystemWindows(true);
        }else if(params.navBarTrans && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            View navHeight = findViewById(R.id.navHeight);
            if (navHeight != null) {
                ViewGroup.LayoutParams layoutParams = navHeight.getLayoutParams();
                layoutParams.height = HeightWidthUtils.getStatusBarHeight(this);
                navHeight.setLayoutParams(layoutParams);
            }
        }
        llTitle = findViewById(R.id.rlTitle);
        if (!params.showTitle) {
            if (llTitle != null)
                llTitle.setVisibility(View.GONE);
            return;
        }

        ivBack = findViewById(R.id.ivBack);
        tvTitle = (TextView) findViewById(R.id.tvTitle);

        llTitle.setVisibility(View.VISIBLE);
        if (params.titleId != 0) {
            tvTitle.setText(params.titleId);
            tvTitle.setOnClickListener(this);
        }
        if (params.showBack) {
            ivBack.setVisibility(View.VISIBLE);
            ivBack.setOnClickListener(this);
        } else {
            ivBack.setVisibility(View.GONE);
        }
    }

    protected void transition(Transition transition){
        if (transition == null){
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(transition);
        }
    }

    public void navBarTrans() {
        boolean huawei = AppUtil.getSystem() == AppUtil.SYS_EMUI;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && !huawei) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    protected abstract void initParams(ViewParams params);

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            onBackPressed();
        } else if (v.getId() == R.id.tvTitle) {
            onTitleClick();
        }
    }

    protected void onTitleClick() {

    }

    public static class ViewParams {
        public int contentId, titleId;
        public boolean showBack = true, showTitle = false, navBarTrans = false;
        public Transition transition;
    }

    protected BasePlugin onRegistPlugin(BasePlugin plugin) {
        plugins.add(plugin);
        plugin.bindActivity(this);
        return plugin;
    }

    @Override
    public void onResume() {
        super.onResume();
        for (BasePlugin p : plugins)
            p.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
        for (BasePlugin p : plugins)
            p.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();
        for (BasePlugin p : plugins)
            p.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        for (BasePlugin p : plugins)
            p.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        for (BasePlugin p : plugins)
            p.onstop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (BasePlugin p : plugins)
            p.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        for (BasePlugin p : plugins)
            p.onConfigurationChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (BasePlugin p : plugins) {
            if (p.onTouchEvent(event))
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        for (BasePlugin p : plugins) {
            if (p.onBackPress())
                return;
        }
        super.onBackPressed();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (BasePlugin p : plugins) {
            p.onActivityResult(requestCode, resultCode, data);
        }
    }
}
