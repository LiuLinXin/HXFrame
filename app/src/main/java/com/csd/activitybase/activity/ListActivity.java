package com.csd.activitybase.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.csd.activitybase.event.Event;
import com.csd.activitybase.adapers.SectionRecycleAdapter;
import com.csd.hx.frame.R;

/**
 * Created by Sober_philer on 2017/3/8 09:11
 */
public abstract class ListActivity extends PluginActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    protected RecyclerView listView;
    private SwipeRefreshLayout sfFresh;
    private int freshCode;
    protected TextView tvFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listView = (RecyclerView) findViewById(R.id.lv_content);
        sfFresh = (SwipeRefreshLayout) findViewById(R.id.sfFresh);
        tvFail = (TextView) findViewById(R.id.tvFail);

        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(setListContent(new SectionRecycleAdapter()));
        ////添加分割线
        listView.setItemAnimator(new DefaultItemAnimator());
        listView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));
        sfFresh.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);
        sfFresh.setOnRefreshListener(this);
        onRefresh();
    }

    @Override
    protected void initParams(ViewParams params) {
        params.contentId = R.layout.activity_list_commen;
        params.showTitle = false;
    }

    protected abstract SectionRecycleAdapter setListContent(SectionRecycleAdapter wraper);

    @Override
    protected void onTitleClick() {
        super.onTitleClick();
        sfFresh.setRefreshing(true);
        onRefresh();
    }

    @Override
    public void onRefresh() {

    }

    protected void pushEventRefresh(Event event) {
        sfFresh.setRefreshing(true);
        freshCode = event.getEventCode();
        pushEvent(event);
    }

    @Override
    public void runEnd(Event event) {
        super.runEnd(event);
        eventFinish(event);
    }

    @Override
    public void runEndError(Event event) {
        eventFinish(event);
    }

    protected void eventFinish(Event event) {
        if (event.getEventCode() == freshCode) {
            refreshEventFinish(event);
        }
    }

    protected void refreshEventFinish(Event event) {
        if (!event.isSuccess())
            refreshEventError(event);
        freshCode = 0;
        sfFresh.setRefreshing(false);
    }

    protected void refreshEventError(Event event) {
        if (tvFail != null) {
            if (checkShowError()) {
                tvFail.setText(R.string.loadingFail);
                tvFail.setVisibility(View.VISIBLE);
            } else
                tvFail.setVisibility(View.GONE);
        }
    }

    protected boolean checkShowError() {
        if (listView.getAdapter().getItemCount() == 0) {
            return true;
        }
        return false;
    }
}
