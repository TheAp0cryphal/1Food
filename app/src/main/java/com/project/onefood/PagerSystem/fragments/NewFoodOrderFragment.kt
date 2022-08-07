package com.project.onefood.PagerSystem.fragments

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.PagerSystem.data.FoodOrder
import com.project.onefood.PagerSystem.viewmodels.FoodOrdersViewModel
import com.project.onefood.R
import com.project.onefood.databinding.FragmentNewFoodOrderBinding

class NewFoodOrderFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentNewFoodOrderBinding

    // View models
    private lateinit var viewModel: FoodOrdersViewModel

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
        binding = FragmentNewFoodOrderBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(FoodOrdersViewModel::class.java)

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
        val calendar: Calendar = Calendar.getInstance()
        val uid: String = firebaseAuth.currentUser!!.uid
        val codeString: String = "$uid,${calendar.timeInMillis}"

        viewModel.foodOrders.value!!.maxOrderNumber += 1

        val foodOrder: FoodOrder = FoodOrder(viewModel.foodOrders.value!!.maxOrderNumber, calendar.timeInMillis, codeString, binding.remarkEditText.text.toString())
        viewModel.foodOrders.value!!.foodOrderList.add(foodOrder)

        firebaseDatabase.getReference(getString(R.string.firebase_database_food_orders)).child(uid).setValue(viewModel.foodOrders.value).addOnCompleteListener {
            // Successfully store customer info
            if (it.isSuccessful) {
                Toast.makeText(binding.root.context, R.string.new_order_activity_toast_success, Toast.LENGTH_SHORT).show()

                FoodOrdersActivity.switchToFoodOrderFragment(requireActivity().supportFragmentManager, foodOrder)
            }
            // Unsuccessfully store customer info
            else {
                Toast.makeText(binding.root.context, R.string.new_order_activity_toast_failure, Toast.LENGTH_SHORT).show()
            }
        }
    }
}