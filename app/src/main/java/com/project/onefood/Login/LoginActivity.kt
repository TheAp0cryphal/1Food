/*
 * File Name: LoginActivity.kt
 * File Description: For users to login
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/03
 */
package com.project.onefood.Login

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.project.onefood.MainMenu.MainMenuActivity
import com.project.onefood.R
import com.project.onefood.databinding.ActivityLoginBinding

private const val TAG: String = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityLoginBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListeners()
    }

    // Actions on back press
    override fun onBackPressed() {
        super.onBackPressed()

        finishAffinity()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    // Set the listeners
    private fun setListeners() {
        binding.logoImageView.setOnClickListener {
            clickLogoImageView()
        }

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

    // Click the logo image view
    private fun clickLogoImageView() {
        val intent: Intent = Intent(Intent.ACTION_VIEW, getString(R.string.one_food_web_address).toUri())
        startActivity(intent)
    }

    // Click the login button
    private fun clickLoginButton() {
        if (!checkEmailAddress(resources, binding.emailAddressEditText))
            return

        if (!checkPassword(resources, binding.passwordEditText))
            return

        login()
    }

    // Login to an account
    private fun login() {
        val emailAddressString: String = binding.emailAddressEditText.text.toString().trim()
        val passwordString: String = binding.passwordEditText.text.toString().trim()

        firebaseAuth.signInWithEmailAndPassword(emailAddressString, passwordString).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, R.string.customer_register_activity_toast_succeed_login, Toast.LENGTH_SHORT).show()

                val intent: Intent = Intent(this, MainMenuActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, R.string.customer_register_activity_toast_fail_login, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Click the register textview
    private fun clickRegisterTextView() {
        val intent: Intent = Intent(this, WhoActivity::class.java)
        startActivity(intent)
    }

    // Click the register textview
    private fun clickResetPasswordTextView() {
        val intent: Intent = Intent(this, ResetPasswordActivity::class.java)
        startActivity(intent)
    }

    // Click the register textview
    private fun clickAnonymousUserTextView() {
        val intent: Intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    companion object {

        // Check the email address
        // Returns
        // true: valid email address, false: invalid email address
        fun checkEmailAddress(resources: Resources, editText: EditText): Boolean {
            val emailAddressString: String = editText.text.toString().trim()

            if (emailAddressString.isEmpty()) {
                editText.error = resources.getString(R.string.login_activity_error_empty_email_address)
                editText.requestFocus()

                return false
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(emailAddressString).matches()) {
                editText.error = resources.getString(R.string.login_activity_error_invalid_email_address)
                editText.requestFocus()

                return false
            }

            return true
        }

        // Check the password
        // Returns
        // true: valid password, false: invalid password
        fun checkPassword(resources: Resources, editText: EditText): Boolean {
            val passwordString: String = editText.text.toString().trim()

            if (passwordString.length < 6) {
                editText.error = resources.getString(R.string.login_activity_error_invalid_password)
                editText.requestFocus()

                return false
            }

            return true
        }

        // Check the first name
        // Returns
        // true: valid first name, false: first name
        fun checkFirstName(resources: Resources, editText: EditText): Boolean {
            val firstNameString: String = editText.text.toString().trim()

            if (firstNameString.isEmpty()) {
                editText.error = resources.getString(R.string.login_activity_error_empty_first_name)
                editText.requestFocus()

                return false
            }

            return true
        }

        // Check the restaurant name
        // Returns
        // true: valid restaurant name, false: restaurant name
        fun checkRestaurantName(resources: Resources, editText: EditText): Boolean {
            val restaurantNameString: String = editText.text.toString().trim()

            if (restaurantNameString.isEmpty()) {
                editText.error = resources.getString(R.string.login_activity_error_empty_restaurant_name)
                editText.requestFocus()

                return false
            }

            return true
        }
    }
}