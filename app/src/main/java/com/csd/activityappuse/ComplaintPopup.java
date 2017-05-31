package com.csd.activityappuse;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.csd.activitybase.utils.AppUtil;
import com.csd.hx.frame.R;

/**
 * Created by sober_philer on 2017/5/26.
 */

public class ComplaintPopup {


    //创建并弹出投诉弹窗
    public static void showPupWindow(Context context) {
        final PopupWindow popupWindow = new PopupWindow(context);
        View view = View.inflate(context, R.layout.compainshowing, null);
        popupWindow.setContentView(view);
        popupWindow.setHeight(AppUtil.getDensity_Height(context) / 8);
        popupWindow.setWidth(AppUtil.getDensity_Width(context) / 2);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
    }
}
