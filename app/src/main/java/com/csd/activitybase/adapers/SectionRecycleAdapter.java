package com.csd.activitybase.adapers;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sober_philer on 2017/5/27.
 */

public class SectionRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<RecyclerView.Adapter> sections = new ArrayList<>();

    public void addSection(RecyclerView.Adapter section) {
        sections.add(section);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int adapterPosition = viewType / 100;
        int adapterInnerPosition = viewType % 100;
        return sections.get(adapterPosition).onCreateViewHolder(parent, adapterInnerPosition);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int[] needPosition = getNeedPosition(position);
        RecyclerView.Adapter tempAdapter = sections.get(needPosition[0]);
        tempAdapter.onBindViewHolder(holder, needPosition[1]);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (RecyclerView.Adapter tempAdapter : sections) {
            count += tempAdapter.getItemCount();
        }
        Log.i("hx", "count : "+count);
        return count;
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        for(RecyclerView.Adapter tempAd : sections){
            tempAd.registerAdapterDataObserver(observer);
        }
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        for(RecyclerView.Adapter tempAd : sections){
            tempAd.unregisterAdapterDataObserver(observer);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int[] needPosition = getNeedPosition(position);
        RecyclerView.Adapter tempAdapter = sections.get(needPosition[0]);
        return tempAdapter.getItemViewType(needPosition[1])+100 * needPosition[0];
    }

    private int[] getNeedPosition(int position) {
        int[] result = new int[2];
        for (int i = 0; i < sections.size(); i++) {
            RecyclerView.Adapter tempAdapter = sections.get(i);
            if(position < tempAdapter.getItemCount()){
                result[0] = i;
                result[1] = position;
                return result;
            }else{
                position -= tempAdapter.getItemCount();
            }
        }
        Log.e("hx", "exception at SectionRecycleAdapter - getNeedPosition  : "+position);
        return result;
    }
}
