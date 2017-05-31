package com.csd.activitybase.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sober_philer on 2017/3/27.
 */

public class NoScrollViewPager extends ViewPager {

    private boolean canScroo;

    public void setCanScroo(boolean canScroo) {
        this.canScroo = canScroo;
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canScroo && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canScroo && super.onInterceptTouchEvent(ev);
    }
}
