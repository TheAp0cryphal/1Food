package com.project.onefood.MainMenu

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.FavouriteList.FavouriteActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.project.onefood.MainMenu.PromoAdapter.PromoRecyclerView
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.R
import com.project.onefood.RestaurantsList.RestaurantsListActivity
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class MainMenuActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    override fun onResume() {
        super.onResume()

        getUserLocation()

        val qrScan: ImageView = findViewById(R.id.qr_scan)
        qrScan.setOnClickListener {
            val intent = Intent(this, QRScanActivity::class.java)
            startActivity(intent)
        }

        val userProfile: ImageView = findViewById(R.id.userProfile)
        userProfile.setOnClickListener {
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }

        val foodOrdersImageView: ImageView = findViewById(R.id.foodOrdersImageView)
        foodOrdersImageView.setOnClickListener {
            val intent = Intent(this, FoodOrdersActivity::class.java)
            startActivity(intent)
        }

        val restaurantsLocations: ImageView = findViewById(R.id.locations)
        restaurantsLocations.setOnClickListener {
            val intent = Intent(this, RestaurantsListActivity::class.java)
            startActivity(intent)
        }

        val favourites: ImageView = findViewById(R.id.favorites)
        favourites.setOnClickListener {
            val intent = Intent(this, FavouriteActivity::class.java)
            startActivity(intent)
        }

        val reservations: ImageView = findViewById(R.id.reservation)
        reservations.setOnClickListener {
            val intent = Intent(this, ReservationActivity::class.java)
            startActivity(intent)
        }
    }


    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        Log.d("here?", "yes")
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        var userLongitude = location.longitude
                        var userLatitude = location.latitude

                        Log.d("LatLng", "$userLongitude $userLatitude")
                        setLocationText(userLatitude, userLongitude)
                    }
                }
    }

    private fun setLocationText(userLatitude : Double, userLongitude : Double) {
        val mGeocoder = Geocoder(applicationContext, Locale.getDefault())

        var address = "Location Error."

        var addressString =
            mGeocoder.getFromLocation(userLatitude, userLongitude, 1)
        //Log.d("LatLngA", addressString.toString())

        if(addressString.size > 0) {
            address = addressString[0].getAddressLine(0)
                .split(",")[0] + ", " + addressString[0].getAddressLine(0)
                .split(",")[1]
        }
        else {

        }
        findViewById<TextView>(R.id.userAddress).text = address
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val recyclerView : RecyclerView = findViewById(R.id.promorecycler)
        val adapter = PromoRecyclerView()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        rqPerms()
        //rqPermsLocation()
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

        val perms = arrayOf("Manifest.permission.CAMERA", "Manifest.permission.ACCESS_FINE_LOCATION", "Manifest.permission.ACCESS_COARSE_LOCATION")

        if (EasyPermissions.hasPermissions(this, perms.toString())){
            getUserLocation()
        }
        else{
            EasyPermissions.requestPermissions(this,
                "Permissions are required for running the application components",
                101,
                Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
    /*

    private fun rqPermsLocation() {
        val perms = { Manifest.permission.ACCESS_FINE_LOCATION}
        val perms2 = { Manifest.permission.ACCESS_COARSE_LOCATION}

        if (EasyPermissions.hasPermissions(this, perms.toString()) && EasyPermissions.hasPermissions(this, perms2.toString())){
            //
        }
        else{
            EasyPermissions.requestPermissions(this,
                "Location required for displaying the restaurants...",
                103,
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

     */

}