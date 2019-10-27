package dev.olog.basil.presentation.main

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dev.olog.basil.presentation.DrawsOnTop
import dev.olog.basil.presentation.R
import dev.olog.basil.presentation.base.BaseActivity
import dev.olog.basil.presentation.collapse
import dev.olog.basil.presentation.topMostFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = VerticalPagerAdapter(this)
        pager.orientation = ViewPager2.ORIENTATION_VERTICAL
        pager.setCurrentItem(1, false)
    }

    override fun onBackPressed() {
        when {
            supportFragmentManager.topMostFragment() is DrawsOnTop -> super.onBackPressed()
            pager.currentItem == 0 -> pager.currentItem = 1
            !(pager.currentItem == 1 && findSlidingPanel()?.collapse() == true) -> {
                super.onBackPressed()
            }
            else -> super.onBackPressed()
        }
    }

    private fun findSlidingPanel(): SlidingUpPanelLayout? {
        return findViewById(R.id.slidingPanel)
    }

}
