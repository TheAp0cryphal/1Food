/*
 * File Name: LoginFragment.kt
 * File Description: For users to login and registration
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.onefood.Login.LoginActivity
import com.project.onefood.Login.data.User
import com.project.onefood.R
import com.project.onefood.databinding.FragmentLoginBinding

private const val TAG: String = "LoginFragment"

class LoginFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentLoginBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = initFragment(inflater, container)

        setListeners()

        return root
    }

    // Initialize the fragment
    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))

        return binding.root
    }

    // Set the listeners
    private fun setListeners() {
        binding.loginButton.setOnClickListener {
            clickLoginButton()
        }

        binding.registerTextView.setOnClickListener {
            clickRegisterTextView()
        }

        binding.resetPasswordTextView.setOnClickListener {
            clickResetPasswordTextView()
        }

        binding.anonymousUserTextView.setOnClickListener {
            clickAnonymousUserTextView()
        }
    }

    // Click the login button
    private fun clickLoginButton() {
        if (!LoginActivity.checkEmailAddress(resources, binding.emailAddressEditText))
            return

        if (!LoginActivity.checkPassword(resources, binding.passwordEditText))
            return

        loginUserAccount()
    }

    // Click the register textview
    private fun clickRegisterTextView() {
        LoginActivity.switchToWhoFragment(requireActivity().supportFragmentManager)
    }

    // Click the reset password textview
    private fun clickResetPasswordTextView() {
        LoginActivity.switchToResetPasswordFragment(requireActivity().supportFragmentManager)
    }

    // Click the anonymous user textview
    private fun clickAnonymousUserTextView() {
        loginAnonymousAccount()
    }

    // Login to a user account
    private fun loginUserAccount() {
        val emailAddressString: String = binding.emailAddressEditText.text.toString().trim()
        val passwordString: String = binding.passwordEditText.text.toString().trim()

        firebaseAuth.signInWithEmailAndPassword(emailAddressString, passwordString).addOnCompleteListener {
            if (it.isSuccessful) {
                loadDataFromDatabase()
            }
            else {
                Toast.makeText(binding.root.context, R.string.login_activity_toast_fail_login_user_account, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Load the data from database
    private fun loadDataFromDatabase() {
        val uid: String = firebaseAuth.currentUser!!.uid

        firebaseDatabase.getReference(getString(R.string.firebase_database_users)).child(uid).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val user: User? = p0.getValue(User::class.java)

                    if (user != null) {
                        LoginActivity.switchToMainMenuActivity(binding.root.context, user, false)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {}
            }
        )
    }

    // Login anonymous account
    private fun loginAnonymousAccount() {
        firebaseAuth.signInAnonymously().addOnCompleteListener {
            if (it.isSuccessful) {
                storeAnonymousInfo()
            }
            else {
                Toast.makeText(binding.root.context, R.string.login_activity_toast_fail_login_anonymous_account, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Store the anonymous info
    private fun storeAnonymousInfo() {
        val uid: String = firebaseAuth.currentUser!!.uid
        val user: User = User()

        firebaseDatabase.getReference(getString(R.string.firebase_database_users)).child(uid).setValue(user).addOnCompleteListener {
            // Successfully store customer info
            if (it.isSuccessful) {
                LoginActivity.switchToMainMenuActivity(binding.root.context, user, false)
            }
            // Unsuccessfully store customer info
            else {
                Toast.makeText(binding.root.context, R.string.customer_register_activity_toast_fail_create_customer_account, Toast.LENGTH_SHORT).show()
            }
        }
    }
}