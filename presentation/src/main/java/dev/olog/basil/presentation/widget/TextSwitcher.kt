package dev.olog.basil.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.doOnLayout
import dev.olog.basil.presentation.R
import kotlinx.android.synthetic.main.item_text.view.*
import kotlin.math.abs

class TextSwitcher(
    context: Context,
    attrs: AttributeSet
) : FrameLayout(context, attrs) {

    private var currentPosition = 0
    private val texts = mutableListOf<String>()

    init {
        addView(createView())
        addView(createView())
        addView(createView())
        doOnLayout {
            getChildAt(0).translationX = -it.width.toFloat()
            getChildAt(2).translationX = it.width.toFloat()
        }
    }

    private fun createView(): View {
        return LayoutInflater.from(context)
            .inflate(R.layout.item_text, this, false)
    }

    fun updateTexts(texts: List<String>) {
        this.texts.clear()
        this.texts.addAll(texts)
        resetText()
    }


    fun translateOffset(translation: Float) {
        translateTo(translation)

        if (abs(translation) < 0.1) {
            resetText()
        }
    }

    fun updateCurrentPage(position: Int) {
        currentPosition = position
    }

    private fun translateTo(translationOffset: Float) {
        val delta = -translationOffset * width
        getChildAt(0).translationX = -width + delta
        getChildAt(1).translationX = delta
        getChildAt(2).translationX = width + delta
    }

    private fun resetText() {
        if (currentPosition - 1 >= 0) {
            getChildAt(0).title.text = texts[currentPosition - 1]
        }
        getChildAt(1).title.text = texts[currentPosition]

        if (currentPosition + 1 <= texts.lastIndex) {
            getChildAt(2).title.text = texts[currentPosition + 1]
        }
    }

}