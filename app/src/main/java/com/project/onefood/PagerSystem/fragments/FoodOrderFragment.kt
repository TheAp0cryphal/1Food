/*
 * File Name: FoodOrderFragment.kt
 * File Description: show the content of a food order
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/08
 */
package com.project.onefood.PagerSystem.fragments

import android.app.AlertDialog
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.PagerSystem.data.FoodOrders
import com.project.onefood.PagerSystem.viewmodels.FoodOrderViewModel
import com.project.onefood.PagerSystem.viewmodels.FoodOrdersViewModel
import com.project.onefood.R
import com.project.onefood.databinding.FragmentFoodOrderBinding

class FoodOrderFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentFoodOrderBinding

    // View models
    private lateinit var viewModel: FoodOrderViewModel
    private lateinit var foodOrdersViewModel: FoodOrdersViewModel

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val root: View = initFragment(inflater, container)

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (savedInstanceState == null) {
            loadDataFromPrevious()
            generateQrCode()
        }

        setListeners()
        setTexts()
        setQrCode()

        return root
    }

    // Actions on destroy view
    override fun onDestroyView() {
        super.onDestroyView()

        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
    }

    // Initialize the fragment
    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFoodOrderBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(FoodOrderViewModel::class.java)
        foodOrdersViewModel = ViewModelProvider(requireActivity()).get(FoodOrdersViewModel::class.java)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))

        return binding.root
    }

    // Load the data from previous
    private fun loadDataFromPrevious() {
        val arguments: Bundle? = this.arguments

        if (arguments != null) {
            viewModel.orderNumber = arguments.getInt(FoodOrdersActivity.ORDER_NUMBER_KEY)
            viewModel.orderTime = arguments.getLong(FoodOrdersActivity.ORDER_TIME_KEY)
            viewModel.code = arguments.getString(FoodOrdersActivity.CODE_KEY).toString()
            viewModel.remarks = arguments.getString(FoodOrdersActivity.REMARKS_KEY).toString()
        }
    }

    // Generate the qr code
    private fun generateQrCode() {
        val width = 200
        val height = 200
        val qrcodeWriter: QRCodeWriter = QRCodeWriter()
        val bitMatrix: BitMatrix = qrcodeWriter.encode(viewModel.code, BarcodeFormat.QR_CODE, width, height)

        viewModel.qrCode = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                viewModel.qrCode.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
    }

    // Set the listeners
    private fun setListeners() {
        binding.doneButton.setOnClickListener {
            clickDoneButton()
        }

        binding.remarksActionButton.setOnClickListener {
            clickRemarksActionButton()
        }
    }

    // Set the texts
    private fun setTexts() {
        binding.orderNumberTextView.text = viewModel.orderNumber.toString()
        binding.orderTimeTextView.text = FoodOrdersActivity.getFoodOrderTimeString(binding.root.context, viewModel.orderTime)
    }

    // Set the qr code
    private fun setQrCode() {
        binding.qrCodeImageView.setImageBitmap(viewModel.qrCode)
    }

    // Click the done button
    private fun clickDoneButton() {
        val uid: String = firebaseAuth.currentUser!!.uid

        // Delete a food order
        deleteFoodOrder(uid, R.string.food_order_activity_toast_notification)

        // Delete a food order notification
        firebaseDatabase.getReference(getString(R.string.firebase_database_food_order_notifications)).child(viewModel.code).removeValue()
    }

    // Click the remarks action button
    private fun clickRemarksActionButton() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(binding.root.context)

        builder.setTitle(R.string.new_order_activity_text_remark)
        builder.setMessage(viewModel.remarks)

        builder.show()
    }

    // Delete a food order
    private fun deleteFoodOrder(uid: String, @StringRes message: Int) {
        var targetIndex: Int = -1

        for (i in foodOrdersViewModel.foodOrders.foodOrderList.indices) {
            if (foodOrdersViewModel.foodOrders.foodOrderList[i].code == viewModel.code) {
                foodOrdersViewModel.foodOrders.foodOrderList.removeAt(i)
                targetIndex = i
                break
            }
        }

        firebaseDatabase.getReference(getString(R.string.firebase_database_food_orders)).child(uid).setValue(foodOrdersViewModel.foodOrders).addOnCompleteListener {
            // Successfully delete the food order
            if (it.isSuccessful) {
                Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()

                FoodOrdersActivity.switchToFoodOrderListFragment(requireActivity().supportFragmentManager)
            }
            // Unsuccessfully delete the food order
            else {
                Toast.makeText(binding.root.context, R.string.new_order_activity_toast_failure, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Click the delete action button
    private fun clickDeleteActionButton() {
        val uid: String = firebaseAuth.currentUser!!.uid

        // Delete a food order
        deleteFoodOrder(uid, R.string.food_order_activity_toast_delete)

        // Delete a food order notification
        firebaseDatabase.getReference(getString(R.string.firebase_database_food_order_notifications)).child(viewModel.code).removeValue()
    }
}