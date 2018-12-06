package dev.olog.basil.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class StoppableVerticalViewPager extends VerticalViewPager {

    private boolean isSwipeEnabled = true;

    public StoppableVerticalViewPager(Context context) {
        super(context);
    }

    public StoppableVerticalViewPager(Context context, AttributeSet attrs) {
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
