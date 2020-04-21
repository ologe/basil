package dev.olog.basil.presentation.detail

import android.animation.Animator
import android.animation.ValueAnimator
import android.transition.TransitionValues
import android.transition.Visibility
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.view.*

class ScaleTransition : Visibility() {

    companion object {
        private const val SCALE_START = 1f
        private const val SCALE_END = .85f
    }

    override fun onAppear(
        sceneRoot: ViewGroup,
        v: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val view = sceneRoot.slidingSheet
        val animator = ValueAnimator.ofFloat(SCALE_START, SCALE_END)
        animator.addUpdateListener {
            view.scaleX = it.animatedValue as Float
            view.scaleY = it.animatedValue as Float
        }
        return animator
    }

    override fun onDisappear(
        sceneRoot: ViewGroup,
        v: View,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator {
        v.visibility = View.GONE // not sure why, but works
        val view = sceneRoot.slidingSheet
        val animator = ValueAnimator.ofFloat(SCALE_END, SCALE_START)
        animator.addUpdateListener {
            view.scaleX = it.animatedValue as Float
            view.scaleY = it.animatedValue as Float
        }
        return animator
    }

}