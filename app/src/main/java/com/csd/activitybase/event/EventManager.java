package com.csd.activitybase.event;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.SparseArray;

import com.csd.activitybase.helper.HOToMoreMap;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Sober_philer on 2017/3/8 13:51
 */

public class EventManager {
    private static EventManager eventManager;
    private HOToMoreMap<Integer, EventListener> code2listeners = new HOToMoreMap<>();
    private SparseArray<EventRunner> code2EventRunner = new SparseArray<>();
    private ArrayList<Integer> code2EventRuning = new ArrayList<>();
    private int CODE_RUNEND = 1;
    private int CODE_ERROR = 2;
    private ExecutorService mExecutorService;
    private Handler hand = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CODE_RUNEND) {
                Event event = (Event) msg.obj;
                eventRunend(event);
                if (event.isSingleRequest())
                    code2EventRuning.remove((Integer) event.getEventCode());
            } else if (msg.what == CODE_ERROR) {
                Event event = (Event) msg.obj;
                eventRunendError(event);
            }
        }
    };

    private EventManager() {
        mExecutorService = Executors.newCachedThreadPool();
    }

    public static EventManager getInstance() {
        if (eventManager == null) {
            eventManager = new EventManager();
        }
        return eventManager;
    }

    public void registEventRunner(int code, EventRunner runner) {
        code2EventRunner.append(code, runner);
    }

    public void unregistEventRunner(int code) {
        code2EventRunner.remove(code);
    }

    public void registEventListener(int code, EventListener listener) {
        code2listeners.put(code, listener);
    }

    public void unregistEventListener(int code, EventListener listener) {
        code2listeners.remove(code, listener);
    }

    public void pushEvent(int code) {
        pushEvent(new Event(code));
    }

    public void pushEvent(final Event event) {
        if (event.isSingleRequest() && code2EventRuning.contains((Integer) event.getEventCode()))
            return;
        if (event.isSingleRequest())
            code2EventRuning.add(event.getEventCode());
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EventRunner eventRunner = code2EventRunner.get(event.getEventCode());
                    if (eventRunner != null) {
                        eventRunner.runEvent(event);
                    } else
                        event.setIsSuccess(true);
                    Message message = hand.obtainMessage();
                    if (event.isSuccess())
                        message.what = CODE_RUNEND;
                    else
                        message.what = CODE_ERROR;
                    message.obj = event;
                    hand.sendMessage(message);
                } catch (Throwable e) {
                    Message message = hand.obtainMessage();
                    message.what = CODE_ERROR;
                    message.obj = event;
                    event.setIsSuccess(false);
                    if (e instanceof Exception)
                        event.setException((Exception) e);
                    hand.sendMessage(message);
                }
            }
        });
    }

    private void eventRunend(Event event) {
        List<EventListener> value = code2listeners.getValue(event.getEventCode());
        if (value == null)
            return;
        if (value.size() > 1)
            Log.i("hx", event.getEventCode() + " listenner count : " + value.size());
        for (EventListener listener : value) {
            listener.runEnd(event);
        }
    }

    private void eventRunendError(Event event) {
        List<EventListener> value = code2listeners.getValue(event.getEventCode());
        if (value == null)
            return;
        if (value.size() > 1)
            Log.i("hx", event.getEventCode() + " listenner count : " + value.size());
        for (EventListener listener : value) {
            listener.runEndError(event);
        }
    }

    public interface EventRunner {
        Gson g = new Gson();

        public abstract void runEvent(Event event) throws Throwable;
    }

    public interface EventListener {
        void runEnd(Event event);

        void runEndError(Event event);
    }
}
