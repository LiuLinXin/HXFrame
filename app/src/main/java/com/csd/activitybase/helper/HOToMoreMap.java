package com.csd.activitybase.helper;

import com.csd.activitybase.event.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by administrato on 2017/4/20.
 */

public class HOToMoreMap<K, V> {

    Map<K, List<V>> dates = new HashMap<>();
    public void put(K key, V value) {
        if(dates.containsKey(key)){
            dates.get(key).add(value);
        }else {
            List<V> tt = new ArrayList<>();
            tt.add(value);
            dates.put(key, tt);
        }
    }

    public void remove(K key, V value) {
        if(dates.containsKey(key)){
            dates.get(key).remove(value);
        }
    }

    public List<V> getValue(K i) {
        return dates.get(i);
    }

    public boolean containsKey(K key) {
        return dates.containsKey(key);
    }

    public long getSize() {
        return dates.size();
    }
}
