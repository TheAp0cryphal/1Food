/*
 * File Name: FoodOrdersAdapter.kt
 * File Description: An adapter for food orders fragment
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.PagerSystem.databases.FoodOrder

class FoodOrdersAdapter(private val context: Context): BaseAdapter() {

    // Food orders list
    var foodOrdersList: List<FoodOrder> = listOf()

    // Get the number of food orders
    override fun getCount(): Int {
        return foodOrdersList.size
    }

    // Get the food order
    override fun getItem(position: Int): Any {
        return foodOrdersList[position]
    }

    // Get the food order id
    override fun getItemId(position: Int): Long {
        return foodOrdersList[position].id
    }

    // Get the view
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val textView: TextView = View.inflate(context, android.R.layout.simple_list_item_1, null) as TextView

        val foodOrder: FoodOrder = foodOrdersList[position]
        val foodOrderIdString: String = foodOrder.id.toString()
        val foodOrderTimeString: String = FoodOrdersActivity.getFoodOrderTimeString(context, foodOrder.time)
        val foodOrderCodeString: String = foodOrder.code.toString()

        textView.text = "Order $foodOrderIdString   $foodOrderTimeString   $foodOrderCodeString"

        return textView
    }

    // Update the food orders list
    fun updateFoodOrdersList(foodOrdersList: List<FoodOrder>) {
        this.foodOrdersList = foodOrdersList

        notifyDataSetChanged()
    }
}