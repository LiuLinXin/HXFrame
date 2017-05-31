package com.csd.activitybase.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;

import com.csd.activitybase.event.Event;
import com.csd.activitybase.event.EventManager;
import com.csd.activitybase.utils.ToastUtils;
import com.csd.hx.frame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sober_philer on 2017/3/7 17:44
 */
public class HttpActivity extends CommenActivity implements EventManager.EventListener {
    private EventManager eventManager = EventManager.getInstance();

    private List<Integer> listeners = new ArrayList<>();
    private AlertDialog dialog;
    private List<Integer> loadIngCodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(dialog == null) {
            Log.i("hx", "dialog == null");
            AlertDialog.Builder builder = new AlertDialog.Builder(getSupActivity());
            dialog = builder.create();
            dialog.show();
            Window window = dialog.getWindow();
            window.setGravity(Gravity.CENTER);
            window.setContentView(R.layout.layout_loading);
            dialog.dismiss();
        }else{
            Log.w("hx", "dialog != null");
        }
    }

    protected void unregistListener(Integer code) {
        if (listeners.contains(code)) {
            listeners.remove(code);
            eventManager.unregistEventListener(code, this);
        }
    }

    protected void registEventRunenr(int code, EventManager.EventRunner runner) {
        unregistEventRunner(code);
        eventManager.registEventRunner(code, runner);
    }

    protected void unregistEventRunner(int code) {
        eventManager.unregistEventRunner(code);
    }

    protected void pushEvent(Event event) {
        registListener(event.getEventCode());
        eventManager.pushEvent(event);
    }

    protected void pushEventLoading(Event event){
        loadIngCodes.add(event.getEventCode());
        if(dialog != null)
        dialog.show();
        pushEvent(event);
    }

    protected void registListener(int code) {
        unregistListener(code);
        eventManager.registEventListener(code, this);
        listeners.add(code);
    }

    @Override
    public void runEnd(Event event) {
        runOver(event);
        if (!event.isSuccess() && event.getException() != null) {
            ToastUtils.getInstance().toastContent(event.getException().getMessage());
        }
    }

    @Override
    public void runEndError(Event event) {
        runOver(event);
        Log.i("hx", "run end errror" + event.getException().getMessage());
    }

    protected void runOver(Event event){
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for(int i:listeners){
            eventManager.unregistEventListener(i,this);
        }
    }
}
