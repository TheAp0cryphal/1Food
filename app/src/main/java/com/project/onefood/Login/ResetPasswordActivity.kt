/*
 * File Name: ResetPasswordActivity.kt
 * File Description: For users to reset their passwords
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/03
 */
package com.project.onefood.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.project.onefood.R
import com.project.onefood.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityResetPasswordBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth

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

        firebaseAuth = FirebaseAuth.getInstance()
    }

    // Set the listeners
    private fun setListeners() {
        binding.logoImageView.setOnClickListener {
            clickLogoImageView()
        }

        binding.resetPasswordButton.setOnClickListener {
            clickResetPasswordButton()
        }
    }

    // Click the logo image view
    private fun clickLogoImageView() {
        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    // Click the reset password button
    private fun clickResetPasswordButton() {
        if (!LoginActivity.checkEmailAddress(resources, binding.emailAddressEditText))
            return

        requestToResetPassword()
    }

    // Request to reset the password
    private fun requestToResetPassword() {
        val emailAddressString: String = binding.emailAddressEditText.text.toString().trim()

        firebaseAuth.sendPasswordResetEmail(emailAddressString).addOnCompleteListener {
            // Succeed to send a password reset email
            if (it.isSuccessful) {
                Toast.makeText(this, R.string.reset_password_activity_toast_succeed_send_password_reset_email, Toast.LENGTH_SHORT).show()

                val intent: Intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            // Failed to send a password reset email
            else {
                Toast.makeText(this, R.string.reset_password_activity_toast_fail_send_password_reset_email, Toast.LENGTH_SHORT).show()
            }
        }
    }
}