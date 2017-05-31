package com.csd.activitybase.adapers;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sober_philer on 2017/3/15.
 */

public abstract class HPagerAdapter<T> extends PagerAdapter implements View.OnClickListener {
    protected List<T> dates = new ArrayList<>();
    protected Context context;

    public List<T> getDates() {
        return dates;
    }

    public HPagerAdapter(Context context) {
        this.context = context;
    }

    public void replaceAll(List<T> dates) {
        this.dates.clear();
        this.dates.addAll(dates);
        notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = createView(dates.get(position));
        container.addView(view);
        view.setTag(dates.get(position));
        view.setOnClickListener(itemClick);
        return view;
    }

    View.OnClickListener itemClick = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            onItemClick(v, (T) v.getTag());
        }
    };

    protected abstract View createView(T date);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View)
            container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void onClick(View v) {
    }

    protected void onItemClick(View v, T date){

    }

}
