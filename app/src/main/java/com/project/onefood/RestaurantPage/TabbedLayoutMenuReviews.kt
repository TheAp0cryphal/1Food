package com.project.onefood.RestaurantPage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.project.onefood.R
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class TabbedLayoutMenuReviews : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initTab()
    }

    private fun initTab() {
        var tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        val tabAdapter = TabAdapter(this, tabLayout.tabCount)
        var viewPager = findViewById<ViewPager2>(R.id.viewPager)

        viewPager.adapter = tabAdapter

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

        tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
        })
    }
}