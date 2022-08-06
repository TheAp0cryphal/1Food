/*
 * File Name: WhoFragment.kt
 * File Description: For users to select a customer account and a restaurant manager account
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/06
 */
package com.project.onefood.Login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.onefood.Login.LoginActivity
import com.project.onefood.databinding.FragmentWhoBinding

private const val TAG: String = "WhoFragment"

class WhoFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentWhoBinding

    // Actions on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val root: View = initFragment(inflater, container)

        setListeners()

        return root
    }

    // Initialize the fragment
    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentWhoBinding.inflate(inflater, container, false)

        return binding.root
    }

    // Set the listeners
    private fun setListeners() {
        binding.customerButton.setOnClickListener {
            clickCustomerButton()
        }

        binding.restaurantManagerButton.setOnClickListener {
            clickRestaurantManagerButton()
        }
    }

    // Click the customer button
    private fun clickCustomerButton() {
        LoginActivity.switchToCustomerRegisterFragment(requireActivity().supportFragmentManager)
    }

    // Click the restaurant manager button
    private fun clickRestaurantManagerButton() {
        LoginActivity.switchToRestaurantManagerRegisterFragment(requireActivity().supportFragmentManager)
    }
}