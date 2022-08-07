/*
 * File Name: ResetPasswordFragment.kt
 * File Description: For users to reset password
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
import com.project.onefood.Login.LoginActivity
import com.project.onefood.R
import com.project.onefood.databinding.FragmentResetPasswordBinding

private const val TAG: String = "ResetPasswordFragment"

class ResetPasswordFragment : Fragment() {
    // UI
    private lateinit var binding: FragmentResetPasswordBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth

    // Actions on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val root: View = initFragment(inflater, container)

        setListeners()

        return root
    }

    // Initialize the fragment
    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        return binding.root
    }

    // Set the listeners
    private fun setListeners() {
        binding.resetPasswordButton.setOnClickListener {
            clickResetPasswordButton()
        }
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
            // Successfully send a password reset email
            if (it.isSuccessful) {
                Toast.makeText(binding.root.context, R.string.reset_password_activity_toast_succeed_send_password_reset_email, Toast.LENGTH_SHORT).show()

                LoginActivity.switchToLoginFragment(requireActivity().supportFragmentManager)
            }
            // Unsuccessfully send a password reset email
            else {
                Toast.makeText(binding.root.context, R.string.reset_password_activity_toast_fail_send_password_reset_email, Toast.LENGTH_SHORT).show()
            }
        }
    }
}