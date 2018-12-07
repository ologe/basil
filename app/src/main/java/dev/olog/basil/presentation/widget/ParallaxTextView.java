package dev.olog.basil.presentation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatTextView;

public class ParallaxTextView extends AppCompatTextView implements ViewTreeObserver.OnScrollChangedListener {

    //    parallax
    private int[] viewLocation = new int[]{0, 0};

    public ParallaxTextView(Context context) {
        super(context);
    }

    public ParallaxTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    public void draw(Canvas canvas) {
        getLocationInWindow(viewLocation);
        float translationX = ((float) getLeft() - ((float) viewLocation[0])) / 4;
        canvas.translate(-translationX, 0f);
        super.draw(canvas);
    }

    @Override
    public void onScrollChanged() {
        invalidate();
    }
}
