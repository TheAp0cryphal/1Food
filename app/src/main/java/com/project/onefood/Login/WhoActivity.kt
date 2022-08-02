/*
 * File Name: WhoActivity.kt
 * File Description: For users to select different mode to register
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.onefood.databinding.ActivityWhoBinding

private const val TAG: String = "WhoActivity"

class WhoActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityWhoBinding

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityWhoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Set the listeners
    private fun setListeners() {
        binding.customerButton.setOnClickListener {
            clickCustomerButton()
        }

        binding.restaurantManagerButton.setOnClickListener {
            clickRestaurantManagerButton()
        }
    }

    // Click the customer button
    private fun clickCustomerButton() {
        val intent: Intent = Intent(this, CustomerRegisterActivity::class.java)
        startActivity(intent)
    }

    // Click the restaurant manager button
    private fun clickRestaurantManagerButton() {
        val intent: Intent = Intent(this, RestaurantManagerRegisterActivity::class.java)
        startActivity(intent)
    }
}