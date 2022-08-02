/*
 * File Name: LoginActivity.kt
 * File Description: For users to login
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
import com.project.onefood.databinding.ActivityLoginBinding

private const val TAG: String = "LoginActivity"

class LoginActivity : AppCompatActivity() {

    // View models
    private lateinit var binding: ActivityLoginBinding

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        if (!checkEmailAddress()) {
            Toast.makeText(this, R.string.login_activity_toast_empty_email_address, Toast.LENGTH_SHORT).show()
            return
        }

        if (!checkPassword()) {
            Toast.makeText(this, R.string.login_activity_toast_empty_password, Toast.LENGTH_SHORT).show()
            return
        }

        val intent: Intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
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
}