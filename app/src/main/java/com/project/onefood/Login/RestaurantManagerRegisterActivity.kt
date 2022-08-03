/*
 * File Name: RestaurantManagerRegisterActivity.kt
 * File Description: For restaurant manager to register
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/03
 */
package com.project.onefood.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.onefood.Login.data.RestaurantManager
import com.project.onefood.R
import com.project.onefood.databinding.ActivityRestaurantManagerRegisterBinding

private const val TAG: String = "RestaurantManagerRegisterActivity"

class RestaurantManagerRegisterActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityRestaurantManagerRegisterBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

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

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance("https://onefood-255ff-default-rtdb.firebaseio.com/")
    }

    // Set the listeners
    private fun setListeners() {
        binding.logoImageView.setOnClickListener {
            clickLogoImageView()
        }

        binding.submitButton.setOnClickListener {
            clickSubmitButton()
        }
    }

    // Click the logo image view
    private fun clickLogoImageView() {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    // Click the submit button
    private fun clickSubmitButton() {
        if (!LoginActivity.checkRestaurantName(resources, binding.restaurantNameEditText))
            return

        if (!LoginActivity.checkEmailAddress(resources, binding.emailAddressEditText))
            return

        if (!LoginActivity.checkPassword(resources, binding.passwordEditText))
            return

        connectToFirebase()
    }

    // Connect to the firebase
    private fun connectToFirebase() {
        val restaurantNameString: String = binding.restaurantNameEditText.text.toString().trim()
        val emailString: String = binding.emailAddressEditText.text.toString().trim()
        val passwordString: String = binding.passwordEditText.text.toString().trim()

        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener {
            // Succeed to create
            if (it.isSuccessful) {
                val restaurantManager: RestaurantManager = RestaurantManager(restaurantNameString, emailString)
                val uid: String = FirebaseAuth.getInstance().currentUser!!.uid

                firebaseDatabase.getReference(getString(R.string.firebase_database_restaurant_manager)).child(uid).setValue(restaurantManager).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, R.string.customer_register_activity_toast_succeed_create_restaurant_manager_account, Toast.LENGTH_SHORT).show()

                        val intent: Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, R.string.customer_register_activity_toast_fail_create_restaurant_manager_customer_account, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // Fail to create
            else {
                Toast.makeText(this, R.string.customer_register_activity_toast_fail_create_restaurant_manager_customer_account, Toast.LENGTH_SHORT).show()
            }
        }
    }
}