package org.vanguardmatrix.engine.utils;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.initializer.engine.R;

public class Animations {

    public static void pushOutToBottom(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_bottom));
    }

    public static void pullInFromBottom(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_bottom));
    }

    public static void pushOutToTop(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_top));
    }

    public static void pullInFromTop(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_top));
    }

    public static void pushOutToLeft(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_left));
    }

    public static void pushOutToLeft_75percent(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_left_75percent));
    }

    public static void pushOutToRight(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_right));
    }

    public static void pushOutToRight_50percent(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_right_50percent));
    }

    public static void pushOutToRight_75percent(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_right_75percent));
    }

    public static void pullInFromRight(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_right));
    }

    public static void pullInFromRight_50percent(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_right_50percent));
    }

    public static void pullInFromRight_75percent(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_right_75percent));
    }

    public static void pullInFromLeft(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_left));
    }

    public static void pullInFromLeft_75percent(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_left_75percent));
    }

    public static void pullDownIn(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_down_in));
    }

    public static void pushUpOut(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_up_out));
    }

    public static void pushOutToBottomSlow(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.push_out_to_bottom_slow));
    }

    public static void pullInFromBottomSlow(View view, Activity activity) {
        view.startAnimation(AnimationUtils.loadAnimation(activity,
                R.anim.pull_in_from_bottom_slow));
    }

}
