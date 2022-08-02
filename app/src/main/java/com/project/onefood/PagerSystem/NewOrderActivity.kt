/*
 * File Name: NewOrderActivity.kt
 * File Description: Create a new food order
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.project.onefood.PagerSystem.databases.FoodOrder
import com.project.onefood.PagerSystem.viewmodels.FoodOrderDatabaseViewModel
import com.project.onefood.R
import com.project.onefood.databinding.ActivityNewOrderBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val TAG: String = "NewOrderActivity"

class NewOrderActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityNewOrderBinding

    // View models
    private lateinit var foodOrderDatabaseViewModel: FoodOrderDatabaseViewModel

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityNewOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        foodOrderDatabaseViewModel = ViewModelProvider(this).get(FoodOrderDatabaseViewModel::class.java)
    }

    // Set the listeners
    private fun setListeners() {
        binding.submitButton.setOnClickListener {
            clickSubmitButton()
        }
    }

    // Click the submit button
    private fun clickSubmitButton() {

        GlobalScope.launch {
            val foodOrder: FoodOrder? = sendRequestToBackend()
            if (foodOrder != null) {
                val id: Long = foodOrderDatabaseViewModel.insertFoodOrder(foodOrder)

                MainScope().launch {
                    Toast.makeText(binding.root.context, R.string.new_order_activity_toast_success, Toast.LENGTH_SHORT).show()
                }

                val intent: Intent = Intent(binding.root.context, FoodOrderActivity::class.java)
                intent.putExtra(FoodOrdersActivity.ID_KEY, id)
                intent.putExtra(FoodOrdersActivity.TIME_KEY, foodOrder.time)
                intent.putExtra(FoodOrdersActivity.CODE_KEY, foodOrder.code)
                intent.putExtra(FoodOrdersActivity.REMARK_KEY, foodOrder.remark)

                startActivity(intent)
            }
            else {
                MainScope().launch {
                    Toast.makeText(binding.root.context, R.string.new_order_activity_toast_failure, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Send a request to backend
    private suspend fun sendRequestToBackend(): FoodOrder? {
        val time: Long = Calendar.getInstance().timeInMillis
        val remark: String = binding.remarkEditText.text.toString()
        var code: String = ""

        for (i in 1..6) {
            code += (0..9).random().toString()
        }

        return FoodOrder(
            0,
            time,
            code, remark
        )
    }

    // Actions on back pressed
    override fun onBackPressed() {
        val intent: Intent = Intent(this, FoodOrdersActivity::class.java)
        startActivity(intent)
    }
}