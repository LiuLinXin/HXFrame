package com.csd.activitybase.adapers;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csd.hx.frame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sober_philer on 2017/5/27.
 */

public abstract class BaseRecycleAdapter<H, X extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<X> implements View.OnClickListener {
    protected List<H> dates = new ArrayList<>();
    protected List<H> selected = new ArrayList<>();
    private int layoutId;
    protected Activity activity;
    protected boolean selectMode;//是否是选择模式

    public BaseRecycleAdapter(Activity activity, int layoutId) {
        this.activity = activity;
        this.layoutId = layoutId;
    }

    public void setSelectMode(boolean selectMode) {
        this.selectMode = selectMode;
    }

    public void replaceAll(List<H> dates) {
        this.dates.clear();
        this.dates.addAll(dates);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(X holder, int position) {
        H bean = dates.get(position);
        holder.itemView.setTag(layoutId, bean);
        holder.itemView.setOnClickListener(this);
        setContent(holder, bean, position);
    }

    abstract protected void setContent(X holder, H bean, int position);

    @Override
    public X onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(activity).inflate(layoutId, parent, false);
        return createHolder(inflate, viewType);
    }

    abstract protected X createHolder(View inflate, int type);

    @Override
    public int getItemCount() {
        return dates.size();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Object bean = v.getTag(layoutId);
        if(bean != null)
            itemClick((H) bean);
    }

    protected void itemClick(H bean) {
        if (selectMode) {
            if (selected.contains(bean)) {
                selected.remove(bean);
            } else {
                selected.add(bean);
            }
            notifyDataSetChanged();
        }
    }

    protected boolean isSelcted(H bean){
        return selected.contains(bean);
    }
}
