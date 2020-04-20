package dev.olog.basil.presentation.main

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import dev.olog.basil.presentation.R

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        pager.adapter = VerticalPagerAdapter(this)
//        pager.orientation = ViewPager2.ORIENTATION_VERTICAL
//        pager.setCurrentItem(1, false)
    }

//    override fun onBackPressed() {
//        when {
//            supportFragmentManager.topMostFragment() is DrawsOnTop -> super.onBackPressed()
//            pager.currentItem == 0 -> pager.currentItem = 1
//            !(pager.currentItem == 1 && findSlidingPanel()?.collapse() == true) -> {
//                super.onBackPressed()
//            }
//            else -> super.onBackPressed()
//        }
//    }

}
