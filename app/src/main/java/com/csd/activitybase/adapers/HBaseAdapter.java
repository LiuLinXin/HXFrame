package com.csd.activitybase.adapers;

import android.app.Activity;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sober_philer on 2017/3/27.
 */

public abstract class HBaseAdapter<H> extends BaseAdapter {

    protected List<H> dates = new ArrayList<>();
    protected Activity context;

    protected List<H> getDates(){
        return dates;
    }

    public HBaseAdapter(Activity context) {
        this.context = context;
    }

    public void replaceAll(List<H> dates){
        if(dates == null)
            return;
        this.dates.clear();
        this.dates.addAll(dates);
        notifyDataSetChanged();
    }

    public void clear(){
        this.dates.clear();
        notifyDataSetChanged();
    }

    public void addDates(List<H> dates) {
        this.dates.addAll(dates);
        notifyDataSetChanged();
    }

    public void addDate(H date){
        dates.add(date);
        notifyDataSetChanged();
    }

    public void addDate(H date, int position){
        if(position < dates.size()){
            dates.add(position, date);
            notifyDataSetChanged();
        }
    }

    public void removeDate(H date){
        for(H tempDate : dates){
            if(tempDate == date){
                dates.remove(tempDate);
                notifyDataSetChanged();
                return;
            }
        }
    }

    public void removeDate(int position){
        if(position < dates.size()){
            dates.remove(position);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public H getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
