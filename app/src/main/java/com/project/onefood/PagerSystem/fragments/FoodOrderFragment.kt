package com.project.onefood.PagerSystem.fragments

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.project.onefood.R
import com.project.onefood.databinding.FragmentFoodOrderBinding

class FoodOrderFragment : Fragment() {

    // UI
    private lateinit var binding: FragmentFoodOrderBinding

    // View models
    private lateinit var viewModel: FoodOrderViewModel

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on create view
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?, ): View? {
        val root: View = initFragment(inflater, container)

        if (savedInstanceState == null) {
            loadDataFromPrevious()
            generateQrCode()
        }

        setListeners()
        setTexts()
        setQrCode()

        return root
    }

    // Initialize the fragment
    private fun initFragment(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFoodOrderBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(FoodOrderViewModel::class.java)

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
        loadDataFromDatabase()
    }

    // Load the data from database
    private fun loadDataFromDatabase() {
        val uid: String = firebaseAuth.currentUser!!.uid

        firebaseDatabase.getReference(getString(R.string.firebase_database_food_orders)).child(uid).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val foodOrders: FoodOrders? = p0.getValue(FoodOrders::class.java)

                    if (foodOrders != null) {
                        for (foodOrder in foodOrders.foodOrderList) {
                            if (foodOrder.code == viewModel.code) {
                                foodOrders.foodOrderList.remove(foodOrder)

                                firebaseDatabase.getReference(getString(R.string.firebase_database_food_orders)).child(uid).setValue(foodOrders).addOnCompleteListener {
                                    // Successfully store customer info
                                    if (it.isSuccessful) {
                                        Toast.makeText(binding.root.context, R.string.new_order_activity_toast_success, Toast.LENGTH_SHORT).show()

                                        FoodOrdersActivity.switchToFoodOrderListFragment(requireActivity().supportFragmentManager)
                                    }
                                    // Unsuccessfully store customer info
                                    else {
                                        Toast.makeText(binding.root.context, R.string.new_order_activity_toast_failure, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                }

                override fun onCancelled(p0: DatabaseError) {}
            }
        )
    }
}