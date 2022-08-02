/*
 * File Name: ResetPasswordActivity.kt
 * File Description: For users to reset their passwords
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.onefood.R
import com.project.onefood.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityResetPasswordBinding

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Set the listeners
    private fun setListeners() {
        binding.resetPasswordButton.setOnClickListener {
            clickResetPasswordButton()
        }
    }

    // Click the reset password button
    private fun clickResetPasswordButton() {
        if (!checkEmailAddress()) {
            Toast.makeText(this, R.string.reset_password_activity_toast_empty_email_address, Toast.LENGTH_SHORT).show()
            return
        }

        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    // Check the email address
    // Returns
    // true: valid email address, false: invalid email address
    private fun checkEmailAddress(): Boolean {
        return binding.emailAddressEditText.text.toString().isNotEmpty()
    }
}