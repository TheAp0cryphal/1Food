package com.project.onefood.RestaurantPage

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.onefood.RestaurantPage.Fragments.MenuFragment
import com.project.onefood.RestaurantPage.Fragments.ReviewsFragment

class TabAdapter(activity: FragmentActivity, private val tabNum: Int) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = tabNum

    override fun createFragment(which: Int): Fragment {
        return when (which){
            0 -> MenuFragment()
            1 -> ReviewsFragment()
            else -> MenuFragment()
        }
    }

}