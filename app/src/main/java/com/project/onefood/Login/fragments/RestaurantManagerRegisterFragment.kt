/*
 * File Name: RestaurantManagerRegisterFragment.kt
 * File Description: For users to register a restaurant manager account
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/06
 */
package com.project.onefood.Login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.onefood.Login.LoginActivity
import com.project.onefood.Login.data.AccountType
import com.project.onefood.Login.data.User
import com.project.onefood.R
import com.project.onefood.databinding.FragmentRestaurantManagerRegisterBinding

private const val TAG: String = "RestaurantManagerRegisterFragment"

class RestaurantManagerRegisterFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentRestaurantManagerRegisterBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val root: View = initFragment(inflater, container)

        setListeners()

        return root
    }

    // Initialize the fragment
    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentRestaurantManagerRegisterBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))

        return binding.root
    }

    // Set the listeners
    private fun setListeners() {
        binding.submitButton.setOnClickListener {
            clickSubmitButton()
        }
    }

    // Click the submit button
    private fun clickSubmitButton() {
        if (!LoginActivity.checkRestaurantName(resources, binding.restaurantNameEditText))
            return

        if (!LoginActivity.checkEmailAddress(resources, binding.emailAddressEditText))
            return

        if (!LoginActivity.checkPassword(resources, binding.passwordEditText))
            return

        createRestaurantManagerAccount()
    }

    // Create a restaurant manager account
    private fun createRestaurantManagerAccount() {
        val restaurantNameString: String = binding.restaurantNameEditText.text.toString().trim()
        val emailAddressString: String = binding.emailAddressEditText.text.toString().trim()
        val passwordString: String = binding.passwordEditText.text.toString().trim()

        firebaseAuth.createUserWithEmailAndPassword(emailAddressString, passwordString).addOnCompleteListener {
            // Successfully create a customer account
            if (it.isSuccessful) {
                val user: User = User(AccountType.RESTAURANT_MANAGER, "", "", restaurantNameString, emailAddressString, "")
                val uid: String = firebaseAuth.currentUser!!.uid

                firebaseDatabase.getReference(getString(R.string.firebase_database_users)).child(uid).setValue(user).addOnCompleteListener {
                    // Successfully store customer info
                    if (it.isSuccessful) {
                        Toast.makeText(binding.root.context, R.string.restaurant_manager_register_activity_toast_succeed_create_restaurant_manager_account, Toast.LENGTH_SHORT).show()

                        LoginActivity.switchToLoginFragment(requireActivity().supportFragmentManager)
                    }
                    // Unsuccessfully store customer info
                    else {
                        Toast.makeText(binding.root.context, R.string.restaurant_manager_register_activity_toast_fail_create_restaurant_manager_customer_account, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            // Unsuccessfully create
            else {
                Toast.makeText(binding.root.context, R.string.restaurant_manager_register_activity_toast_fail_create_restaurant_manager_customer_account, Toast.LENGTH_SHORT).show()
            }
        }
    }
}