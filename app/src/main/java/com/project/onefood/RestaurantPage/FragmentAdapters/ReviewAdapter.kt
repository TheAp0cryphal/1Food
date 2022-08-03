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

    var reviewerComments = arrayOf("Very expensive", "Poor service", "Nice food and good variety.","Yuummmyyyyy!!")
    var reviewerName = arrayOf("Shiela Shah", "Tangy Shawarma", "John Cena", "Mikaela")
    var reviewerRating= arrayOf(R.drawable.zero_star,R.drawable.one_star,R.drawable.two_star,R.drawable.three_star,R.drawable.four_star, R.drawable.five_star)

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(p0: Int): Any {
        return reviewerComments[p0]
    }

    override fun getItemId(p0: Int): Long {
        return reviewerComments[p0].toLong()
    }

    override fun getView(position: Int, view : View?, parent : ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.review_row, parent, false)


        val customerName = rowView.findViewById<TextView>(R.id.customer_name)
        customerName.text = reviewerName[position]

        val customerReview =  rowView.findViewById<TextView>(R.id.customer_review)
        customerReview.text = reviewerComments[position]

        val customerRating= rowView.findViewById<ImageView>(R.id.star_rating)
        customerRating.setImageResource(reviewerRating[position])

        return rowView
    }
}