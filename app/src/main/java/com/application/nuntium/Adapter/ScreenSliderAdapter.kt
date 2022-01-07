package com.application.nuntium.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.nuntium.Fragment.*

class ScreenSlidingAdapter(fa: Fragment) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment = when(position){
        0 -> RandomHomeFragment()
        1 -> SportFragment()
        2 -> GamingFragment()
        3 -> BusinessFragment()
        4 -> PoliticsFragment()
        else -> HealthFragment()
    }

}