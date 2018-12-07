package dev.olog.basil.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class StoppableVerticalViewPager extends VerticalViewPager {

    private float downY;

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
        if (getCurrentItem() == 1) {
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    onActionDown(ev);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (onActionMove(ev)){
                        return false;
                    }
            }
        }


        if (isSwipeEnabled){
            return super.onInterceptTouchEvent(ev);
        }
        return false;
    }

    private void onActionDown(MotionEvent ev){
        downY = ev.getY();
    }

    /*
        returns true if if scrolling up, don't intercept touch
     */
    private boolean onActionMove(MotionEvent ev){
        return ev.getY() < downY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isSwipeEnabled){
            return super.onTouchEvent(ev);
        }
        return false;
    }

}
