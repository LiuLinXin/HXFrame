package com.csd.activitybase.adapers;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sober_philer on 2017/5/27.
 */

public abstract class ItemRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Activity context;

    public ItemRecycleAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    abstract public View getView(ViewGroup parent);


    @Override
    public int getItemCount() {
        return 1;
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(View itemView) {
            super(itemView);
        }
    }
}
