package com.project.onefood.RestaurantPage.FragmentAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.project.onefood.R


class MenuAdapter(var context : Context) : BaseAdapter() {

    private val inflater: LayoutInflater
            = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    var foodItems = arrayOf("pizza", "pasta", "chicken noodles")

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(p0: Int): Any {
        return foodItems[p0]
    }

    override fun getItemId(p0: Int): Long {
        return foodItems[p0].toLong()
    }

    override fun getView(position: Int, view : View?, parent : ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.menu_row, parent, false)

        val foodImage = rowView.findViewById<ImageView>(R.id.food_img)
        foodImage.setImageResource(R.drawable.fake_promo)

        val foodName = rowView.findViewById<TextView>(R.id.food_name)
        foodName.text = foodItems[position]

        val foodPrice =  rowView.findViewById<TextView>(R.id.food_price)
        foodPrice.text = "$10.99"

        return rowView
    }
}