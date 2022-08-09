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

    var foodnames = arrayOf("pizza", "pasta", "chicken noodles","yam fries")
    var foodPrices = arrayOf("$19.99", "$15.99", "$17.99","$9.99")
    var foodImages = arrayOf(R.drawable.fake_promo, R.drawable.fake_promo2, R.drawable.fake_promo3, R.drawable.fake_promo4)
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(p0: Int): Any {
        return foodnames[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, view : View?, parent : ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.menu_row, parent, false)

        val foodImage = rowView.findViewById<ImageView>(R.id.food_img)
        foodImage.setImageResource(foodImages[position])

        val foodName = rowView.findViewById<TextView>(R.id.food_name)
        foodName.text = foodnames[position]

        val foodPrice =  rowView.findViewById<TextView>(R.id.food_price)
        foodPrice.text = foodPrices[position]

        return rowView
    }
}