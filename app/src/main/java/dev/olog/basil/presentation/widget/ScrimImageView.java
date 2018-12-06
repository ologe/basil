package dev.olog.basil.presentation.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import dev.olog.basil.R;

public class ScrimImageView extends AppCompatImageView {

    private Rect rect;
    private boolean drawScrim = false;
    private Paint paint;

    public ScrimImageView(Context context) {
        this(context, null);
    }

    public ScrimImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs){
        rect = new Rect();
        paint = new Paint();

        int scrimColor = ContextCompat.getColor(context, R.color.basil_green_50);
        paint.setColor(scrimColor);
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
        super.onDraw(canvas);
        if (drawScrim){
            System.out.println("draw");
            canvas.drawRect(rect, paint);
        }
    }
}
