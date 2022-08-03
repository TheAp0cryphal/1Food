package com.project.onefood.RestaurantPage.FragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.onefood.R

class ReviewAdapter(var context : Context) : BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var reviewComments = arrayOf("Great food.. awesome vibe", "Awesome Food.. shit vibe", "It be bitchin")
    var reviewerName = arrayOf("Shiela Shah")
    //rating

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(p0: Int): Any {
        return reviewComments[p0]
    }

    override fun getItemId(p0: Int): Long {
        return reviewComments[p0].toLong()
    }

    override fun getView(position: Int, view : View?, parent : ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.review_row, parent, false)


        val foodName = rowView.findViewById<TextView>(R.id.customer_name)
        foodName.text = "Shiela Shah"

        val foodPrice =  rowView.findViewById<TextView>(R.id.customer_review)
        foodPrice.text = reviewComments[position]

        return rowView
    }
}