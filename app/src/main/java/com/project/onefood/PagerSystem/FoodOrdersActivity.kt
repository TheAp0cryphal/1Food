/*
 * File Name: FoodOrdersActivity.kt
 * File Description: Show the list of food orders
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.project.onefood.MainMenu.MainMenuActivity
import com.project.onefood.PagerSystem.adapters.FoodOrdersAdapter
import com.project.onefood.PagerSystem.databases.FoodOrder
import com.project.onefood.PagerSystem.viewmodels.FoodOrderDatabaseViewModel
import com.project.onefood.R
import com.project.onefood.databinding.ActivityFoodOrdersBinding

private const val TAG: String = "FoodOrdersActivity"

class FoodOrdersActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityFoodOrdersBinding

    // View models
    private lateinit var foodOrderDatabaseViewModel: FoodOrderDatabaseViewModel

    // Food orders list view
    private lateinit var foodOrdersAdapter: FoodOrdersAdapter

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListView()
        setObservers()
        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityFoodOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodOrderDatabaseViewModel = ViewModelProvider(this).get(FoodOrderDatabaseViewModel::class.java)
    }

    // Set the list view
    private fun setListView() {
        foodOrdersAdapter = FoodOrdersAdapter(binding.root.context)
        binding.foodOrdersListView.adapter = foodOrdersAdapter
    }

    // Set the observers
    private fun setObservers() {
        foodOrderDatabaseViewModel.allFoodOrdersLiveData.observe(this, Observer {
            foodOrdersAdapter.updateFoodOrdersList(it)
        })
    }

    // Set the listeners
    private fun setListeners() {
        binding.foodOrdersListView.setOnItemClickListener { parent, view, position, id ->
            clickFoodOrderItem(position)
        }
    }

    // Click the food order item
    private fun clickFoodOrderItem(position: Int) {
        val foodOrder: FoodOrder = foodOrdersAdapter.foodOrdersList[position]
        val intent: Intent = Intent(binding.root.context, FoodOrderActivity::class.java)

        intent.putExtra(ID_KEY, foodOrder.id)
        intent.putExtra(TIME_KEY, foodOrder.time)
        intent.putExtra(CODE_KEY, foodOrder.code)
        intent.putExtra(REMARK_KEY, foodOrder.remark)

        startActivity(intent)
    }

    // Actions on create options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_food_orders, menu)

        return true
    }

    // Actions on options item selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_new_order) {
            val intent: Intent = Intent(this, NewOrderActivity::class.java)
            startActivity(intent)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val ID_KEY: String = "ID_KEY"
        const val TIME_KEY: String = "TIME_KEY"
        const val CODE_KEY: String = "CODE_KEY"
        const val REMARK_KEY: String = "REMARK_KEY"

        // Get the food order time string
        fun getFoodOrderTimeString(context: Context, time: Long): String {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = time

//            val timeFormat: SimpleDateFormat = if (SettingsFragment.is24HourFormat(context))
//                SimpleDateFormat("HH:mm:ss")
//            else
//                SimpleDateFormat("h:mm:ss a")
            val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm:ss")

            return timeFormat.format(calendar.time)
        }
    }

    // Actions on back pressed
    override fun onBackPressed() {
        val intent: Intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}