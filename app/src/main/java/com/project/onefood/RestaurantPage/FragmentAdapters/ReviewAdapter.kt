package com.project.onefood.RestaurantPage.FragmentAdapters

import android.annotation.SuppressLint
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
import com.squareup.picasso.Picasso

class ReviewAdapter(var context : Context, var list: ArrayList<ReviewItem>) : BaseAdapter(){

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(p0: Int): Any {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return list[p0].text.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, view : View?, parent : ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.review_row, parent, false)


        val customerName = rowView.findViewById<TextView>(R.id.customer_name)
        customerName.text = list[position].author_name

        val customerReview =  rowView.findViewById<TextView>(R.id.customer_review)
        customerReview.text = list[position].text

        val reviewRating =  rowView.findViewById<TextView>(R.id.reviewRating)
        reviewRating.text = "Rating: " + list[position].rating

        val timeOfReview =  rowView.findViewById<TextView>(R.id.timeOfReview)
        timeOfReview.text = list[position].relative_time_description

        val userImg =  rowView.findViewById<ImageView>(R.id.userImg)
        Picasso.get().load(list[position].profile_photo_url).into(userImg)

//        val customerRating= rowView.findViewById<ImageView>(R.id.star_rating)
//        customerRating.setImageResource(list[position].rating.toInt())

        return rowView
    }
}