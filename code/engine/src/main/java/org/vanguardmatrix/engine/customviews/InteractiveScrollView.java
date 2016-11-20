package org.vanguardmatrix.engine.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Uzair on 1/18/2016.
 */

public class InteractiveScrollView extends ScrollView {
    OnBottomReachedListener mListener; OnTopReachedListener mListener2;
    public InteractiveScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public InteractiveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InteractiveScrollView(Context context) {
        super(context);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        View view = (View) getChildAt(getChildCount()-1);
        int diff = (view.getBottom()-(getHeight()+getScrollY()));

        if (diff == 0 && mListener != null) {
            mListener.onBottomReached();
        }
        else if (getScrollY() == 0 && mListener2 != null) {
            mListener2.onTopReached();
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }


// Getters & Setters

    public OnBottomReachedListener getOnBottomReachedListener() {
        return mListener;
    }

    public void setOnBottomReachedListener(
            OnBottomReachedListener onBottomReachedListener) {
        mListener = onBottomReachedListener;
    }
    public OnTopReachedListener getOnTopReachedListener() {
        return mListener2;
    }

    public void setOnTopReachedListener(
            OnTopReachedListener onTopReachedListener) {
        mListener2 = onTopReachedListener;
    }


    /**
     * Event listener.
     */
    public interface OnBottomReachedListener{
        public void onBottomReached();
    }
    public interface OnTopReachedListener{
        public void onTopReached();
    }

}