package dev.olog.basil.presentation

import com.sothree.slidinguppanel.SlidingUpPanelLayout

fun SlidingUpPanelLayout.collapse() : Boolean{
    val isExpanded = this.panelState == SlidingUpPanelLayout.PanelState.EXPANDED
    if (isExpanded){
        this.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }
    return isExpanded
}