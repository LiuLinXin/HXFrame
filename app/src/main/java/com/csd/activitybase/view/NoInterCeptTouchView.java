package com.csd.activitybase.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by sober_philer on 2017/5/3.
 */

public class NoInterCeptTouchView extends LinearLayout {
    public NoInterCeptTouchView(Context context) {
        super(context);
    }

    public NoInterCeptTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoInterCeptTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }
}
