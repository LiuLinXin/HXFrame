package com.csd.activitybase.dynamic;

import android.animation.Animator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sober_philer on 2017/5/26.
 */

public class TransitionUtils implements Animator.AnimatorListener {
    private static TransitionUtils transitionUtils = new TransitionUtils();

    private TransitionUtils() {
    }

    public static TransitionUtils getInstance() {
        return transitionUtils;
    }

    private View nowView;

    public void circleVisible(final View view) {
        int x = view.getWidth();
        int y = view.getHeight();
        int maxRadio = (int) Math.hypot(x, y);
        circleVisible(view, x / 2, y / 2, maxRadio);
    }

    public void circleVisible(View view, int x, int y, float maxRadio) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            nowView = view;
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, x, y, 0, maxRadio);
            circularReveal.setDuration(1000);
            circularReveal.setInterpolator(new AccelerateInterpolator());
            circularReveal.addListener(this);
            circularReveal.start();
            return;
        }

        view.setVisibility(View.VISIBLE);
    }

    public void transitionStart(Activity activity, Intent intent){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(activity).toBundle();
            activity.startActivity(intent, bundle);
            return;
        }
        activity.startActivity(intent);
    }

    public void transitionStart(Activity context, Intent intent, Map<View, String> shareElements){
        Pair<View, String>[] pairs = new Pair[shareElements.size()];
        Set<Map.Entry<View, String>> entries = shareElements.entrySet();
        int index = 0;
        for(Map.Entry<View, String> entry : entries){
            pairs[index++] = (Pair.create(entry.getKey(), entry.getValue()));
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions transitionActivityOptions2 = ActivityOptions.makeSceneTransitionAnimation(context, pairs);
            context.startActivity(intent, transitionActivityOptions2.toBundle());
            return;
        }
        context.startActivity(intent);
    }

    @Override
    public void onAnimationStart(Animator animation) {
        nowView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }
}
