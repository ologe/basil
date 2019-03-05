package dev.olog.basil.presentation.main

import android.os.Bundle
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import dev.olog.basil.R
import dev.olog.basil.presentation.DrawsOnTop
import dev.olog.basil.presentation.base.BaseActivity
import dev.olog.basil.utils.collapse
import dev.olog.basil.utils.topMostFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    val viewModel by lazy { MainFragmentViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pager.adapter = VerticalPagerAdapter(supportFragmentManager)
        pager.currentItem = 1
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
        return supportFragmentManager.fragments
                .find { it is MainFragment }
                ?.view
                ?.findViewById(R.id.slidingPanel)
    }

}
