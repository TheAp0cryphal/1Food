package com.project.onefood.MainMenu

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.MainMenu.PromoAdapter.HorizontalRecyclerView
import com.project.onefood.R
import pub.devrel.easypermissions.EasyPermissions

class MainMenuActivity : AppCompatActivity() {

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()

        val qrScan : ImageView = findViewById(R.id.qr_scan)
        qrScan.setOnClickListener {
            val intent = Intent(this, QRScanActivity::class.java)
            startActivity(intent)
        }

        val userProfile : ImageView = findViewById(R.id.userProfile)
        userProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val recyclerView : RecyclerView = findViewById(R.id.promorecycler)
        val adapter = HorizontalRecyclerView()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        rqPerms()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults,
            this )
    }

    //Requesting Permissions
    private fun rqPerms() {
        val perms = { Manifest.permission.CAMERA}

        if (EasyPermissions.hasPermissions(this, perms.toString())){
            //
        }
        else{
            EasyPermissions.requestPermissions(this,
                "Camera required for QR Scanning...",
                101,
                Manifest.permission.CAMERA)
        }
    }

}