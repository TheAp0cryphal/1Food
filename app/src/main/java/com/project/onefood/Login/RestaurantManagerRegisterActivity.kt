/*
 * File Name: RestaurantManagerRegisterActivity.kt
 * File Description: For restaurant manager to register
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.onefood.MainMenu.MainMenuActivity
import com.project.onefood.R
import com.project.onefood.databinding.ActivityRestaurantManagerRegisterBinding

private const val TAG: String = "RestaurantManagerRegisterActivity"

class RestaurantManagerRegisterActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityRestaurantManagerRegisterBinding

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityRestaurantManagerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Set the listeners
    private fun setListeners() {
        binding.submitButton.setOnClickListener {
            clickSubmitButton()
        }
    }

    // Click the submit button
    private fun clickSubmitButton() {
        if (!checkRestaurantName()) {
            Toast.makeText(this, R.string.restaurant_manager_register_activity_toast_empty_restaurant_name, Toast.LENGTH_SHORT).show()
            return
        }

        if (!checkEmailAddress()) {
            Toast.makeText(this, R.string.restaurant_manager_register_activity_toast_empty_email_address, Toast.LENGTH_SHORT).show()
            return
        }

        if (!checkPassword()) {
            Toast.makeText(this, R.string.restaurant_manager_register_activity_toast_empty_password, Toast.LENGTH_SHORT).show()
            return
        }

        val intent: Intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    // Check the restaurant name
    // Returns
    // true: valid restaurant name, false: invalid restaurant name
    private fun checkRestaurantName(): Boolean {
        return binding.restaurantNameEditText.text.toString().isNotEmpty()
    }

    // Check the email address
    // Returns
    // true: valid email address, false: invalid email address
    private fun checkEmailAddress(): Boolean {
        return binding.emailAddressEditText.text.toString().isNotEmpty()
    }

    // Check the password
    // Returns
    // true: valid password, false: invalid password
    private fun checkPassword(): Boolean {
        return binding.passwordEditText.text.toString().isNotEmpty()
    }
}