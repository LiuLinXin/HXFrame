package com.csd.activitybase.adapers;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.csd.hx.frame.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sober_philer on 2017/5/27.
 */

public class MultypeRecycleAdapter<H> extends BaseRecycleAdapter<H, RecyclerView.ViewHolder> {

    protected List<RecycleType> types = new ArrayList<>();

    public void addType(RecycleType type) {
        type.multypeRecycleAdapter = this;
        types.add(type);
    }

    public MultypeRecycleAdapter(Activity activity) {
        super(activity, R.string.app_name);
    }

    @Override
    protected void setContent(RecyclerView.ViewHolder beforHolder, H bean, int position) {
        int itemViewType = getItemViewType(position);
        types.get(itemViewType).setContent(beforHolder, bean, position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return types.get(viewType).getHolder(parent);
    }

    /**
     * 在使用multypeRecycleAdapter时这个方法无效
     */
    @Override
    protected RecyclerView.ViewHolder createHolder(View inflate, int viewType) {
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        H bean = dates.get(position);
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).accept(bean, position)) {
                return i;
            }
        }
        Log.e("hx", "types error : "+position);
        return super.getItemViewType(position);
    }

    public static abstract class RecycleType {
        protected MultypeRecycleAdapter multypeRecycleAdapter;

        protected boolean isSelected(Object bean) {
            return multypeRecycleAdapter.isSelcted(bean);
        }

        protected abstract boolean accept(Object bean, int position);

        protected abstract RecyclerView.ViewHolder getHolder(ViewGroup parent);

        protected abstract void setContent(RecyclerView.ViewHolder holder, Object bean, int position);
    }
}












