package dev.olog.basil.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class StoppableViewPager extends ViewPager {

    private boolean isSwipeEnabled = true;

    public StoppableViewPager(@NonNull Context context) {
        super(context);
    }

    public StoppableViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSwipeEnabled(boolean enabled){
        this.isSwipeEnabled = enabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isSwipeEnabled){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSwipeEnabled){
            return super.onTouchEvent(ev);
        }
        return false;
    }
}
