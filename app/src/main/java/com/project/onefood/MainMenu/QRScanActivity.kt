package com.project.onefood.MainMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.project.onefood.R

class QRScanActivity : AppCompatActivity() {

    private lateinit var qrScanner: CodeScanner

    override fun onResume() {
        super.onResume()

        qrScanner.startPreview()

        qrScanner.decodeCallback = DecodeCallback {
            runOnUiThread{
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
            }
        }

        qrScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Permission Denied: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscan)

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
    }

    override fun onPause() {
        qrScanner.releaseResources()
        super.onPause()
    }
}