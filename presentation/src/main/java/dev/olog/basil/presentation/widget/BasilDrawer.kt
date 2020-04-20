package dev.olog.basil.presentation.widget

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.forEachIndexed
import dev.olog.basil.core.RecipeCategory
import dev.olog.basil.presentation.R
import kotlin.math.abs

class BasilDrawer (
    context: Context,
    attrs: AttributeSet
) : LinearLayout(context, attrs), GestureDetector.OnGestureListener {

    var listener : ((RecipeCategory) -> Unit)? = null

    private var currentCategory = RecipeCategory.Entree
        private set(value) {
            listener?.invoke(value)
            updateCurrentCategory(value)
            field = value
        }

    private val gestureDetector = GestureDetectorCompat(context, this)

    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER

        for (category in RecipeCategory.values()) {
            addView(createView(category))
        }
        updateCurrentCategory(currentCategory)
    }

    private fun createView(category: RecipeCategory): View {
        val title = getTitle(category)

        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_category, this, false) as TextView

        view.text = title

        return view
    }

    private fun updateCurrentCategory(category: RecipeCategory) {
        if (isInEditMode) {
            return
        }

        val position = RecipeCategory.values().indexOf(category)
        forEachIndexed { index, view ->
            val textView = view as TextView
            if (index == position) {
                textView.setTextColor(ContextCompat.getColor(context, R.color.basil_green_800))
                textView.typeface = ResourcesCompat.getFont(context, R.font.montserrat_bold)
            } else {
                textView.setTextColor(ContextCompat.getColor(context, R.color.basil_green_500))
                textView.typeface = ResourcesCompat.getFont(context, R.font.montserrat)
            }
        }
    }

    private fun getTitle(category: RecipeCategory): String {
        val stringRes = when (category) {
            RecipeCategory.Appetizer -> R.string.category_appetizers
            RecipeCategory.Entree -> R.string.category_entrees
            RecipeCategory.Dessert -> R.string.category_desserts
            RecipeCategory.Cocktail -> R.string.category_cocktails
        }
        return context.getString(stringRes)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    // TODO simplify
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        val position = e.y

        val delta = height / RecipeCategory.values().size
        for (index in 0..RecipeCategory.values().size) {
            val current = index * delta
            if (abs(current - position) < delta) {
                currentCategory = RecipeCategory.values()[index]
                break
            }
        }

        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {

    }

    override fun setTranslationY(translationY: Float) {
        forEachIndexed { index, view ->
            view.translationY = computeTranslationY(translationY, childCount - index)
        }
    }

    fun computeTranslationY(translationY: Float, index: Int): Float {
        return translationY * index
    }

}