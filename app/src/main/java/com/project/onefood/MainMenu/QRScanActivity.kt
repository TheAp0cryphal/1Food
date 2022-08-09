package com.project.onefood.MainMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.PagerSystem.data.FoodOrderNotification
import com.project.onefood.PagerSystem.services.PagerSystemService
import com.project.onefood.R
import com.project.onefood.databinding.ActivityQrscanBinding

class  QRScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQrscanBinding

    private lateinit var qrScanner: CodeScanner

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    override fun onResume() {
        super.onResume()

        qrScanner.startPreview()

        qrScanner.decodeCallback = DecodeCallback {
            val code: String = it.text.toString()

            if (code.contains('.') || code.contains('#') || code.contains('$') || code.contains('[') || code.contains(']')) {
                runOnUiThread {
                    Toast.makeText(binding.root.context, R.string.qr_scan_activity_toast_invalid, Toast.LENGTH_SHORT).show()
                }
                qrScanner.startPreview()
            }
            else {
                val uid: String = firebaseAuth.currentUser!!.uid

                firebaseDatabase.getReference(getString(R.string.firebase_database_food_order_notifications)).child(code).addListenerForSingleValueEvent(
                    object: ValueEventListener {
                        override fun onDataChange(p0: DataSnapshot) {
                            val foodOrderNotification: FoodOrderNotification? = p0.getValue(FoodOrderNotification::class.java)

                            if (foodOrderNotification != null) {
                                if (uid in foodOrderNotification.receivers) {
                                    Toast.makeText(binding.root.context, R.string.qr_scan_activity_toast_repeat, Toast.LENGTH_SHORT).show()

                                    switchMainMenuActivity()
                                    return
                                } else {
                                    foodOrderNotification.receiverNumber += 1
                                    foodOrderNotification.receivers.add(uid)

                                    firebaseDatabase.getReference(getString(R.string.firebase_database_food_order_notifications)).child(code).setValue(foodOrderNotification).addOnCompleteListener {
                                        // Successfully create a food order
                                        if (it.isSuccessful) {
                                            Toast.makeText(binding.root.context, R.string.new_order_activity_toast_success, Toast.LENGTH_SHORT).show()

                                            startNotification(code)

                                            switchOrderPlacedActivity()
                                        }
                                        // Unsuccessfully create a food order
                                        else {
                                            Toast.makeText(binding.root.context, R.string.new_order_activity_toast_failure, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(binding.root.context, R.string.qr_scan_activity_toast_invalid, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(p0: DatabaseError) {}
                    }
                )
            }
        }

        qrScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Permission Denied: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    //
    private fun startNotification(code: String) {
        val intent = Intent(this, PagerSystemService::class.java)
        intent.putExtra(FoodOrdersActivity.CODE_KEY, code)
        startService(intent)
    }

    // Switch to order placed activity
    private fun switchOrderPlacedActivity() {
        val intent = Intent(this, OrderPlacedActivity::class.java)
        startActivity(intent)
    }

    // Switch to main menu activity
    private fun switchMainMenuActivity() {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    // Switch to QR scan activity
    private fun switchQRScanActivity() {
        val intent = Intent(this, QRScanActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrscanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scannerView : CodeScannerView = findViewById(R.id.scanner_view)
        qrScanner = CodeScanner(this, scannerView)

        qrScanner.autoFocusMode = AutoFocusMode.SAFE
        qrScanner.scanMode = ScanMode.SINGLE

        qrScanner.camera = CodeScanner.CAMERA_BACK
        qrScanner.formats = CodeScanner.ALL_FORMATS

        qrScanner.isFlashEnabled = false
        qrScanner.isAutoFocusEnabled = true

        scannerView.setOnClickListener {
            qrScanner.startPreview()
        }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))
    }

    override fun onPause() {
        qrScanner.releaseResources()
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()


    }
}