package com.csd.activitybase.utils;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by sober_philer on 2017/5/27.
 */
public class ToastUtils {
    private static ToastUtils ourInstance = new ToastUtils();

    public static ToastUtils getInstance() {
        return ourInstance;
    }

    private ToastUtils() {
    }
    private Context context;
    private Toast toast;

    public void init(Context context){
        this.context = context;
    }

    public void toastContent(String content) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        Log.i("hx", "toast : "+content);
        toast.setText(content);
        toast.show();
    }

    public void toastContent(int id) {
        try {
            String content = context.getString(id);
            toastContent(content);
        } catch (Exception e) {
            Log.e("hx", "exception in baseapplication stoastContent : " + e.getCause() + " : " +
                    e.getMessage());
        }
    }
}
