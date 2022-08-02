/*
 * File Name: FoodOrderActivity.kt
 * File Description: Show the content of the food order
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.project.onefood.PagerSystem.viewmodels.FoodOrderDatabaseViewModel
import com.project.onefood.PagerSystem.viewmodels.FoodOrderViewModel
import com.project.onefood.R
import com.project.onefood.databinding.ActivityFoodOrderBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

private const val TAG: String = "FoodOrderActivity"

class FoodOrderActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityFoodOrderBinding

    // View models
    private lateinit var viewModel: FoodOrderViewModel
    private lateinit var foodOrderDatabaseViewModel: FoodOrderDatabaseViewModel

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        if (savedInstanceState == null) {
            loadDataFromCall()
            generateQrCode()
        }

        setListeners()
        setTexts()
        setQrCode()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityFoodOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(FoodOrderViewModel::class.java)
        foodOrderDatabaseViewModel = ViewModelProvider(this).get(FoodOrderDatabaseViewModel::class.java)
    }

    // Load the data from call
    private fun loadDataFromCall() {
        viewModel.id = intent.getLongExtra(FoodOrdersActivity.ID_KEY, 0)
        viewModel.time = intent.getLongExtra(FoodOrdersActivity.TIME_KEY, 0)
        viewModel.code = intent.getStringExtra(FoodOrdersActivity.CODE_KEY).toString()
        viewModel.remark = intent.getStringExtra(FoodOrdersActivity.REMARK_KEY).toString()
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
        binding.idTextView.text = viewModel.id.toString()
        binding.timeTextView.text = FoodOrdersActivity.getFoodOrderTimeString(this, viewModel.time)
        binding.numericalCodeTextView.text = viewModel.code
    }

    // Set the qr code
    private fun setQrCode() {
        binding.qrCodeImageView.setImageBitmap(viewModel.qrCode)
    }

    // Click the done button
    private fun clickDoneButton() {
        GlobalScope.launch {
            if (sendRequestToBackend()) {

                MainScope().launch {
                    Toast.makeText(binding.root.context, R.string.food_order_activity_toast_success, Toast.LENGTH_SHORT).show()
                }

                foodOrderDatabaseViewModel.deleteFoodOrder(viewModel.id)
                val intent: Intent = Intent(binding.root.context, FoodOrdersActivity::class.java)
                startActivity(intent)
            } else {
                MainScope().launch {
                    Toast.makeText(binding.root.context, R.string.food_order_activity_toast_failure, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Send a request to the backend
    private suspend fun sendRequestToBackend(): Boolean {
        return true
    }

    // Actions on create options menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_food_order, menu)

        return true
    }

    // Actions on options item selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete_order) {
            Toast.makeText(binding.root.context, R.string.food_order_activity_toast_delete, Toast.LENGTH_SHORT).show()

            foodOrderDatabaseViewModel.deleteFoodOrder(viewModel.id)
            val intent: Intent = Intent(binding.root.context, FoodOrdersActivity::class.java)
            startActivity(intent)

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // Actions on back pressed
    override fun onBackPressed() {
        val intent: Intent = Intent(this, FoodOrdersActivity::class.java)
        startActivity(intent)
    }
}