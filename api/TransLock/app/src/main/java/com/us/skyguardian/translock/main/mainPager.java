package com.us.skyguardian.translock.main;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

public class mainPager extends ViewPager {

    public mainPager(@NonNull Context context) {
        super(context);
        setMyScroller();
    }

    public mainPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    private void setMyScroller() {
        try {
            Class<?> pager = ViewPager.class;
            Field scroller = pager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new mainScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public class mainScroller extends Scroller {

        public mainScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 0);
        }
    }
}

