package dev.olog.basil.presentation.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import dev.olog.basil.R;

public class DottedSeparator extends View {

    public DottedSeparator(Context context) {
        this(context, null);
    }

    public DottedSeparator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DottedSeparator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        setBackgroundResource(R.drawable.dotted_line);
    }
}
