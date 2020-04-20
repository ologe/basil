package dev.olog.basil.presentation.widget.sliding.sheet

import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.core.math.MathUtils
import androidx.core.math.MathUtils.clamp
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import dev.olog.basil.presentation.widget.sliding.sheet.SlidingSheet.ScrollState
import kotlin.math.abs

class SlidingSheetPresenter(
    private val view: SlidingSheet,
    private val onStateChanged: (ScrollState) -> Unit,
    private val onOffsetChanged: (Float) -> Unit
) {

    companion object {
        private const val SCROLL_DURATION_FACTOR = 2.6f
        private const val MIN_SCROLL_DURATION = 300
        private const val MAX_SCROLL_DURATION = 600
    }

    private var scrollState = ScrollState.IDLE
        set(value) {
            if (field != value) {
                onStateChanged(value)
                field = value
            }
        }

    private var initialMotionX = 0f
    private var initialMotionY = 0f
    private var lastMotionX = 0f
    private var lastMotionY = 0f

    private var currentPage = 1
    private val configuration = ViewConfiguration.get(view.context)
    private var velocityTracker: VelocityTracker? = null

    var scrollY = 0

    private val scroller = Scroller(view.context, LinearOutSlowInInterpolator())

    fun onTouchEvent(ev: MotionEvent): Boolean {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain()
        }
        velocityTracker?.addMovement(ev)

        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> onDown(ev)
            MotionEvent.ACTION_MOVE -> onMove(ev)
            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> onUp(ev)
        }
        return true
    }

    private fun onDown(ev: MotionEvent) {
        scroller.abortAnimation()
        initialMotionX = ev.x
        initialMotionY = ev.y
        lastMotionX = ev.x
        lastMotionY = ev.y
    }

    private fun onMove(ev: MotionEvent) {
        scrollState = ScrollState.DRAGGING

        if (!isOverScroll(ev)) {
            val max = 30f
            val deltaY = clamp(lastMotionY - ev.y, -max, max)

            view.scroll(deltaY.toInt())
        }

        lastMotionX = ev.x
        lastMotionY = ev.y
    }

    private fun onUp(ev: MotionEvent) {
        if (isOverScroll(ev)) {
            velocityTracker?.recycle()
            velocityTracker = null
            scrollState = ScrollState.SETTLING
            scrollState = ScrollState.IDLE
            return
        }
        val totalDelta = initialMotionY - ev.y

        scrollState = ScrollState.SETTLING

        velocityTracker!!.computeCurrentVelocity(1000, configuration.scaledMaximumFlingVelocity.toFloat())
        val initialVelocity = velocityTracker!!.yVelocity
        if (abs(initialVelocity) > configuration.scaledMinimumFlingVelocity) {

            currentPage = when {
                initialVelocity > 0f && totalDelta < 0 -> currentPage + 1
                initialVelocity < 0f && totalDelta > 0 -> currentPage - 1
                else -> currentPage
            }
            val dy = when (currentPage) {
                0 -> view.height - view.bottomOffset
                1 -> 0
                2 -> -view.height
                else -> throw IllegalStateException("page $currentPage")
            }
            val deltaScroll = dy - scrollY
            val duration = abs(deltaScroll) / SCROLL_DURATION_FACTOR

            scroller.scrollY(scrollY, dy - scrollY, duration.toInt())
        } else {
            scroller.scrollY(scrollY, -scrollY, MIN_SCROLL_DURATION)
        }

        velocityTracker?.recycle()
        velocityTracker = null
        view.postInvalidateOnAnimation()
    }

    fun onScrollChanged() {
        val offset = when {
            scrollY == 0 -> 0f
            scrollY > 0 ->  -(abs(scrollY.toFloat()) / abs(view.height - view.bottomOffset))
            else -> abs(scrollY.toFloat()) / abs(view.height)
        }
        onOffsetChanged(offset)
    }

    private fun isOverScroll(ev: MotionEvent): Boolean {
        val totalDelta = initialMotionY - ev.y
        return currentPage == 0 && totalDelta > 0 ||
                currentPage == 2 && totalDelta < 0
    }

    private fun Scroller.scrollY(start: Int, dy: Int, duration: Int) {
        startScroll(
            0,
            start,
            0,
            dy,
            clamp(duration, MIN_SCROLL_DURATION, MAX_SCROLL_DURATION)
        )
    }

    fun computeScrollOffset(): Boolean {
        return scroller.computeScrollOffset()
    }

    fun scrollerCurrentY(): Int {
        return scroller.currY
    }

    fun checkScrollerFinished() {
        if (scroller.isFinished) {
            scrollState = ScrollState.IDLE
        }
    }

}