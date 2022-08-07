package com.project.onefood.PagerSystem.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.PagerSystem.adapters.FoodOrderListAdapter
import com.project.onefood.PagerSystem.data.FoodOrder
import com.project.onefood.PagerSystem.data.FoodOrders
import com.project.onefood.PagerSystem.viewmodels.FoodOrdersViewModel
import com.project.onefood.R
import com.project.onefood.databinding.FragmentFoodOrderListBinding

class FoodOrderListFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentFoodOrderListBinding

    // View models
    private lateinit var viewModel: FoodOrdersViewModel

    // food order list
    private lateinit var foodOrderListAdapter: FoodOrderListAdapter

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val root: View = initFragment(inflater, container)

        setFoodOrderList()
        setListeners()

        return root
    }

    // Initialize the fragment
    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFoodOrderListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(FoodOrdersViewModel::class.java)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))

        return binding.root
    }

    // Set the food order list
    private fun setFoodOrderList() {
        foodOrderListAdapter = FoodOrderListAdapter(binding.root.context, viewModel.foodOrders.value!!)
        binding.foodOrderListView.adapter = foodOrderListAdapter

        changeVisibility()
    }

    // Change the visibility
    private fun changeVisibility() {
        if (viewModel.foodOrders.value!!.foodOrderList.isEmpty()) {
            binding.foodOrderListView.visibility = View.GONE
            binding.foodOrderListTextView.visibility = View.VISIBLE
        } else {
            binding.foodOrderListView.visibility = View.VISIBLE
            binding.foodOrderListTextView.visibility = View.GONE
        }
    }

    // Set the listeners
    private fun setListeners() {
        loadDataFromDatabase()

        binding.newFoodOrderActionButton.setOnClickListener {
            clickNewFoodOrderActionButton()
        }

        binding.foodOrderListView.setOnItemClickListener { parent, view, position, id ->
            val foodOrder: FoodOrder = viewModel.foodOrders.value!!.foodOrderList[position]

            FoodOrdersActivity.switchToFoodOrderFragment(requireActivity().supportFragmentManager, foodOrder)
        }
    }

    // Load the data from database
    private fun loadDataFromDatabase() {
        val uid: String = firebaseAuth.currentUser!!.uid

        firebaseDatabase.getReference(getString(R.string.firebase_database_food_orders)).child(uid).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val foodOrders: FoodOrders? = p0.getValue(FoodOrders::class.java)

                    if (foodOrders != null) {
                        viewModel.foodOrders.value = foodOrders
                        foodOrderListAdapter.notifyDataSetChanged()
                        changeVisibility()
                    }
                }

                override fun onCancelled(p0: DatabaseError) {}
            }
        )
    }

    // Click the new food order action button
    private fun clickNewFoodOrderActionButton() {
        FoodOrdersActivity.switchToNewFoodOrderFragment(requireActivity().supportFragmentManager)
    }
}