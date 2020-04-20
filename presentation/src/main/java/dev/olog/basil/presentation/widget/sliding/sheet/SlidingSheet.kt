package dev.olog.basil.presentation.widget.sliding.sheet

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.extensions.dip
import dev.olog.basil.presentation.extensions.dipf
import dev.olog.basil.presentation.extensions.setHeight
import kotlin.math.abs

class SlidingSheet(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private val listeners = mutableListOf<Callback>()

    private val presenter = SlidingSheetPresenter(
        view = this,
        onStateChanged = { state -> listeners.forEach { it.onStateChanged(this, state) } },
        onOffsetChanged = { offset -> listeners.forEach { it.onOffsetChanged(this, offset) } }
    )

    val topOffset: Int
    val bottomOffset: Int
    private var downX = 0f
    private var downY = 0f
    private val configuration = ViewConfiguration.get(context)
    private var downEvent: MotionEvent? = null
    var isDraggable = true

    private val topView: View
        get() = getChildAt(0)

    private val bottomView: View
        get() = getChildAt(2)

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.SlidingSheet)
        topOffset = a.getDimension(R.styleable.SlidingSheet_top_offset, context.dipf(32)).toInt()
        bottomOffset = a.getDimension(R.styleable.SlidingSheet_bottom_offset, context.dipf(32)).toInt()
        a.recycle()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (!isInEditMode) {
            require(childCount == 3)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val height = MeasureSpec.getSize(heightMeasureSpec).toFloat()

        topView.setHeight(height.toInt() + topOffset)
        topView.translationY = -height
        bottomView.translationY = height - bottomOffset
    }

    fun addListener(listener: Callback) {
        listeners.add(listener)
    }

    fun removeListener(listener: Callback) {
        listeners.remove(listener)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = ev.x
                downY = ev.y
                downEvent = MotionEvent.obtain(ev)
            }
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                downX = 0f
                downY = 0f
                downEvent?.recycle()
                downEvent = null
            }
            MotionEvent.ACTION_MOVE -> {
                val diffX = abs(ev.x - downX)
                val diffY = abs(ev.y - downY)
                val canHandle = diffY > configuration.scaledTouchSlop && diffY > diffX
                if (canHandle) {
                    presenter.onTouchEvent(downEvent!!)
                    downEvent!!.recycle()
                    downEvent = null
                    return true
                }
            }
        }

        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return presenter.onTouchEvent(ev)
    }

    fun scroll(dy: Int) {
        presenter.scrollY += dy
        scrollInternal()
    }

    override fun computeScroll() {
        super.computeScroll()
        if (presenter.computeScrollOffset()) {
            presenter.scrollY = presenter.scrollerCurrentY()
            scrollInternal()
            postInvalidateOnAnimation()

            presenter.checkScrollerFinished()
        }
    }

    private fun scrollInternal() {
        if (!isDraggable) {
            return
        }
        presenter.onScrollChanged()
        val deltaY = presenter.scrollY
        if (deltaY > 0) {
            val defaultBottomTranslation = height - bottomOffset
            val defaultTopTranslation = -height
            bottomView.translationY = defaultBottomTranslation - deltaY.toFloat()
            topView.translationY = defaultTopTranslation - deltaY.toFloat()
        } else if (deltaY < 0){
            scrollTo(0, deltaY)
        }
    }

    interface Callback {

        fun onStateChanged(view: SlidingSheet, state: ScrollState){}
        fun onOffsetChanged(view: SlidingSheet, offset: Float){}

    }

    enum class ScrollState {
        IDLE,
        DRAGGING,
        SETTLING
    }

}
