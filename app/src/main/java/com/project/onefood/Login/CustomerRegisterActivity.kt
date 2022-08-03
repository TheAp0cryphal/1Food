/*
 * File Name: CustomerRegisterActivity.kt
 * File Description: For customers to register
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
import com.project.onefood.Login.data.Customer
import com.project.onefood.R
import com.project.onefood.databinding.ActivityCustomerRegisterBinding

private const val TAG: String = "CustomerRegisterActivity"

class CustomerRegisterActivity : AppCompatActivity() {

    //UI
    private lateinit var binding: ActivityCustomerRegisterBinding

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
        binding = ActivityCustomerRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))
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
        if (!LoginActivity.checkFirstName(resources, binding.firstNameEditText))
            return

        if (!LoginActivity.checkEmailAddress(resources, binding.emailAddressEditText))
            return

        if (!LoginActivity.checkPassword(resources, binding.passwordEditText))
            return

        createCustomerAccount()
    }

    // Create a customer account
    private fun createCustomerAccount() {
        val firstNameString: String = binding.firstNameEditText.text.toString().trim()
        val lastNameString: String = binding.lastNameEditText.text.toString().trim()
        val emailAddressString: String = binding.emailAddressEditText.text.toString().trim()
        val passwordString: String = binding.passwordEditText.text.toString().trim()

        firebaseAuth.createUserWithEmailAndPassword(emailAddressString, passwordString).addOnCompleteListener {
            // Succeed to create
            if (it.isSuccessful) {
                val customer: Customer = Customer(firstNameString, lastNameString, emailAddressString)
                val uid: String = FirebaseAuth.getInstance().currentUser!!.uid

                firebaseDatabase.getReference(getString(R.string.firebase_database_customers)).child(uid).setValue(customer).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(this, R.string.customer_register_activity_toast_succeed_create_customer_account, Toast.LENGTH_SHORT).show()

                        val intent: Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Toast.makeText(this, R.string.customer_register_activity_toast_fail_create_customer_account, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // Fail to create
            else {
                Toast.makeText(this, R.string.customer_register_activity_toast_fail_create_customer_account, Toast.LENGTH_SHORT).show()
            }
        }
    }
}