package com.project.onefood.PagerSystem.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.project.onefood.PagerSystem.data.FoodOrders

class FoodOrderListAdapter(private val context: Context, private val foodOrders: FoodOrders): BaseAdapter() {

    // Get the number of food orders
    override fun getCount(): Int {
        return foodOrders.foodOrderList.size
    }

    // Get the food order
    override fun getItem(position: Int): Any {
        return foodOrders.foodOrderList[position]
    }

    // Get the item id
    override fun getItemId(position: Int): Long {
        return foodOrders.foodOrderList[position].orderNumber.toLong()
    }

    // Get the view
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView: TextView = View.inflate(context, android.R.layout.simple_list_item_1, null) as TextView

        textView.text = "HHH"

        return textView
    }
}