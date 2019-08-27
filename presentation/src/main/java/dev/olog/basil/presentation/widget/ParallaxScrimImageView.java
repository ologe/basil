package dev.olog.basil.presentation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import dev.olog.basil.presentation.R;

public class ParallaxScrimImageView extends AppCompatImageView implements ViewTreeObserver.OnScrollChangedListener {

//    parallax
    private int[] viewLocation = new int[]{0, 0};

//    scrim
    private Rect rect;
    private boolean drawScrim = false;
    private Paint paint;

    public ParallaxScrimImageView(Context context) {
        this(context, null);
    }

    public ParallaxScrimImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ParallaxScrimImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        rect = new Rect();
        paint = new Paint();

        int scrimColor = ContextCompat.getColor(context, R.color.basil_green_50);
        paint.setColor(scrimColor);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnScrollChangedListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnScrollChangedListener(this);
    }

    public void setScrimTop(int top){
        rect.set(0, top, getRight(), getBottom());
        invalidate();
    }

    public void setDrawScrim(boolean drawScrim){
        if (this.drawScrim != drawScrim){
            this.drawScrim = drawScrim;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawParallax(canvas);
        super.onDraw(canvas);
        if (drawScrim){
            canvas.drawRect(rect, paint);
        }
    }

    private void drawParallax(Canvas canvas){
        getLocationInWindow(viewLocation);
        if (getDrawable() != null) {
            float translationX = ((float) getLeft() - ((float) viewLocation[0])) / 3;

            float translationY = ((float) getTop() - ((float) viewLocation[1])) / 10;

            canvas.translate(translationX, translationY);
        }
    }


    @Override
    public void onScrollChanged() {
        invalidate();
    }
}
