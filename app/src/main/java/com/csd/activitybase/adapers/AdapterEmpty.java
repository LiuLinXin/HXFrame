package com.csd.activitybase.adapers;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.csd.activitybase.utils.HeightWidthUtils;

/**
 * Created by sober_philer on 2017/5/27.
 */

public class AdapterEmpty extends ItemRecycleAdapter {
    private View inflate;
    public AdapterEmpty(Activity context, int height) {
        super(context);
        inflate = new View(context);
        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, HeightWidthUtils.dp2px(height));
        inflate.setLayoutParams(params);
    }

    @Override
    public View getView(ViewGroup parent) {
        return inflate;
    }
}
