package com.csd.activitybase.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.csd.hx.frame.R;

import java.util.ArrayList;
import java.util.List;

public abstract class PagerActivity extends CommenActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    protected List<View> selectsViews = new ArrayList<>();
    protected ViewPager viewPager;
    private List<View> mViews = new ArrayList<>();
    private HPagerAdapter adapter = new HPagerAdapter(mViews);
    private LocalActivityManager manager;
    private List<Class> activities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivitys(activities);
//        setContentView(R.layout.activity_main2);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(activities.size());
        manager = new LocalActivityManager(this, false);
        manager.dispatchCreate(savedInstanceState);

        for (int i = 0; i < activities.size(); i++) {
            Class c = activities.get(i);
            Intent intent = new Intent(PagerActivity.this, c);
            View v = manager.startActivity(c.getSimpleName() + i, intent).getDecorView();
            mViews.add(v);
        }

        for (int i = 0; i < activities.size(); i++) {
            Class c = activities.get(i);
            Activity activity = manager.getActivity(c.getSimpleName() + i);
            if (activity != null && activity instanceof CommenActivity) {
                ((CommenActivity) activity).setActivity(activity);
            }
        }

        for (View v : selectsViews) {
            v.setOnClickListener(this);
        }

        viewPager.setAdapter(adapter);// 配置适配器
        viewPager.addOnPageChangeListener(this);
        onPageSelected(0);
    }

    protected void addActivitys(Class z, View vp) {
        activities.add(z);
        selectsViews.add(vp);
    }

    protected void setCurrentItem(int position) {
        viewPager.setCurrentItem(position);
    }

    protected abstract void initActivitys(List<Class> activities);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        boolean isSelect;
        for (int i = 0; i < selectsViews.size(); i++) {
            View vpt = selectsViews.get(i);
            if (position == i) {
                isSelect = true;
            } else
                isSelect = false;
            vpt.setSelected(isSelect);
            if (vpt instanceof ViewGroup) {
                ViewGroup vp = (ViewGroup) vpt;
                for (int j = 0; j < vp.getChildCount(); j++) {
                    vp.getChildAt(j).setSelected(isSelect);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < selectsViews.size(); i++) {
            if (v == selectsViews.get(i)) {
                setCurrentItem(i);
            }
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        for (int i = 0; i < activities.size(); i++) {
            Class c = activities.get(i);
            Activity activity = manager.getActivity(c.getSimpleName() + i);
            if (activity != null && activity instanceof CommenActivity) {
                ((CommenActivity) activity).onRestart();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i = 0; i < activities.size(); i++) {
            Class c = activities.get(i);
            Activity activity = manager.getActivity(c.getSimpleName() + i);
            if (activity != null && activity instanceof CommenActivity) {
                ((CommenActivity) activity).onResume();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        for (int i = 0; i < activities.size(); i++) {
            Class c = activities.get(i);
            Activity activity = manager.getActivity(c.getSimpleName() + i);
            if (activity != null && activity instanceof CommenActivity) {
                ((CommenActivity) activity).onPause();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        for (int i = 0; i < activities.size(); i++) {
            Class c = activities.get(i);
            Activity activity = manager.getActivity(c.getSimpleName() + i);
            if (activity != null && activity instanceof CommenActivity) {
                ((CommenActivity) activity).onStop();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < activities.size(); i++) {
            Class c = activities.get(i);
            Activity activity = manager.getActivity(c.getSimpleName() + i);
            if (activity != null && activity instanceof CommenActivity) {
                ((CommenActivity) activity).onDestroy();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("hx", "onActivityResult at pageractivity");
        super.onActivityResult(requestCode, resultCode, data);
//        for(int i=0;i<activities.size();i++){
        int currentItem = viewPager.getCurrentItem();
        Class c = activities.get(currentItem);
        Activity activity = manager.getActivity(c.getSimpleName() + currentItem);
        if (activity != null && activity instanceof CommenActivity) {
            ((CommenActivity) activity).onActivityResult(requestCode, resultCode, data);
        }
//        }
    }

    private class HPagerAdapter extends PagerAdapter {

        HPagerAdapter(List<View> v) {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
