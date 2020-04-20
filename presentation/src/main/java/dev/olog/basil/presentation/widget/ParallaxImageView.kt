package dev.olog.basil.presentation.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.math.MathUtils
import dev.olog.basil.presentation.R

class ParallaxImageView(
    context: Context,
    attrs: AttributeSet
) : AppCompatImageView(context, attrs), OnScrollChangedListener {

    private val viewLocation = intArrayOf(0, 0)

    var scrimPercentage: Float = 0f
        set(value) {
            field = MathUtils.clamp(value, 0f, 1f)
            invalidate()
        }

    private val paint = Paint()
    private val rect = Rect()
    private val verticalParallax: Int
    private val horizontalParallax: Int

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ParallaxImageView)

        paint.color = a.getColor(
            R.styleable.ParallaxImageView_scrim_color,
            ContextCompat.getColor(context, R.color.basil_scrim)
        )
        scrimPercentage = a.getFloat(R.styleable.ParallaxImageView_scrim_percentage, 0f)
        verticalParallax = a.getInt(R.styleable.ParallaxImageView_vertical_parallax, 10)
        horizontalParallax = a.getInt(R.styleable.ParallaxImageView_horizontal_parallax, 3)

        a.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnScrollChangedListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnScrollChangedListener(this)
    }

    override fun onDraw(canvas: Canvas) {
        drawParallax(canvas)
        super.onDraw(canvas)
        drawScrim(canvas)
    }

    private fun drawParallax(canvas: Canvas) {
        drawable ?: return

        getLocationInWindow(viewLocation)
        val (x, y) = viewLocation
        val translationX = (left.toFloat() - x.toFloat()) / horizontalParallax
        val translationY = (top.toFloat() - y.toFloat()) / verticalParallax
        canvas.translate(translationX, translationY)
    }

    private fun drawScrim(canvas: Canvas) {
        rect.set(0, (height * (1 - scrimPercentage)).toInt(), width, height)
        canvas.drawRect(rect, paint)
    }

    override fun onScrollChanged() {
        invalidate()
    }
}