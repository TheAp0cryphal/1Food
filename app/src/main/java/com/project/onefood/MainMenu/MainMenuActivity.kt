package com.project.onefood.MainMenu

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.onefood.FavouriteList.FavouriteActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.onefood.Login.LoginActivity
import com.project.onefood.Login.data.AccountType
import com.project.onefood.Login.data.User
import com.project.onefood.MainMenu.PromoAdapter.PromoRecyclerView
import com.project.onefood.MainMenu.viewmodels.MainMenuViewModel
import com.project.onefood.MainMenu.viewmodels.ReservationItem
import com.project.onefood.PagerSystem.FoodOrdersActivity
import com.project.onefood.R
import com.project.onefood.RestaurantsList.RestaurantsListActivity
import com.project.onefood.databinding.ActivityMainMenuBinding
import pub.devrel.easypermissions.EasyPermissions
import java.util.*

class MainMenuActivity : AppCompatActivity(){

    // UI
    private lateinit var binding: ActivityMainMenuBinding

    // View models
    private lateinit var viewModel: MainMenuViewModel

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var promosList = arrayListOf<promoItem>()

    lateinit var reservationItemAdapter: PromoRecyclerView

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        if (viewModel.accountType.value == null || intent.getBooleanExtra(LoginActivity.DATA_IS_CHANGED_KEY, false)) {
            loadDataFromPrevious()
        }

        setUserAccount()
        setListeners()

        binding.promorecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        reservationItemAdapter = PromoRecyclerView(this, promosList)
        binding.promorecycler.adapter = reservationItemAdapter

        rqPerms()

        getFromFirebase()
    }


    private fun getFromFirebase() : ArrayList<promoItem> {

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))

        val uid: String = firebaseAuth.currentUser!!.uid


        Log.d("asdasdasdheh","asd")

        firebaseDatabase.getReference("Promos").addListenerForSingleValueEvent(
            object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(p0: DataSnapshot) {
                    promosList.clear()
                    Log.d("asdasdasdheh1",p0.children.toString())
                    for (postSnapshot : DataSnapshot in p0.children){
                        Log.d("asdasdasdheh1123",postSnapshot.toString())
                        val promoItem = postSnapshot.getValue(promoItem::class.java)
                        Log.d("asdasdasdheh1promoItem",postSnapshot.toString())

                        promoItem?.firebaseKey = postSnapshot.key.toString()

                        Log.d("checkReturn", promoItem.toString() + " " + p0.childrenCount)

                        if (promoItem != null) {
                            //val emptyText: TextView = findViewById(R.id.empty)
                            //emptyText.isVisible = false

                            promosList.add(promoItem)
                            reservationItemAdapter.replace(promosList)
                            reservationItemAdapter.notifyDataSetChanged()
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    //TODO("Not yet implemented")
                }
            }
        )
        return promosList
    }

    // Actions on back pressed
    override fun onBackPressed() {
        finishAffinity()
    }

    // Actions on resume
    override fun onResume() {
        super.onResume()

        getUserLocation()

        val qrScan: ImageView = findViewById(R.id.qr_scan)
        qrScan.setOnClickListener {
            val intent = Intent(this, QRScanActivity::class.java)
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

    // Get the user location
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

    // Set the location text
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

    // Actions on request permissions result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
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

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainMenuViewModel::class.java)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))
    }

    // Load the data from the previous
    private fun loadDataFromPrevious() {
        viewModel.accountType.value = AccountType.values()[intent.getIntExtra(LoginActivity.ACCOUNT_TYPE_KEY, 0)]
        viewModel.firstName.value = intent.getStringExtra(LoginActivity.FIRST_NAME_KEY)
        viewModel.lastName.value = intent.getStringExtra(LoginActivity.LAST_NAME_KEY)
        viewModel.restaurantName.value = intent.getStringExtra(LoginActivity.RESTAURANT_NAME_KEY)
        viewModel.emailAddress.value = intent.getStringExtra(LoginActivity.EMAIL_ADDRESS_KEY)
        viewModel.homeAddress.value = intent.getStringExtra(LoginActivity.HOME_ADDRESS_KEY)
    }

    // Set the user account
    private fun setUserAccount() {
        // User name
        if (viewModel.accountType.value == AccountType.RESTAURANT_MANAGER) {
            binding.userNameTextView.text = viewModel.restaurantName.value
        } else {
            binding.userNameTextView.text = viewModel.firstName.value
        }

        // Food orders image view
        if (viewModel.accountType.value == AccountType.RESTAURANT_MANAGER || viewModel.accountType.value == AccountType.DEVELOPER) {
            binding.foodOrdersCardView.visibility = View.VISIBLE
        } else {
            binding.foodOrdersCardView.visibility = View.GONE
        }
    }

    // Set the listeners
    private fun setListeners() {
        loadDataFromDatabase()

        binding.userProfile.setOnClickListener {
            clickUserProfileButton()
        }
    }

    // Load the data from database
    private fun loadDataFromDatabase() {
        val uid: String = firebaseAuth.currentUser!!.uid

        firebaseDatabase.getReference(getString(R.string.firebase_database_users)).child(uid).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val user: User? = p0.getValue(User::class.java)

                    if (user != null) {
                        viewModel.accountType.value = user.accountType
                        viewModel.firstName.value = user.firstName
                        viewModel.lastName.value = user.lastName
                        viewModel.restaurantName.value = user.restaurantName
                        viewModel.emailAddress.value = user.emailAddress
                        viewModel.homeAddress.value = user.homeAddress

                        setUserAccount()
                    }
                }

                override fun onCancelled(p0: DatabaseError) {}
            }
        )
    }

    // Click the user profile button
    private fun clickUserProfileButton() {
        val intent = Intent(this, UserProfileActivity::class.java)

        intent.putExtra(LoginActivity.ACCOUNT_TYPE_KEY, viewModel.accountType.value!!.ordinal)
        intent.putExtra(LoginActivity.FIRST_NAME_KEY, viewModel.firstName.value)
        intent.putExtra(LoginActivity.LAST_NAME_KEY, viewModel.lastName.value)
        intent.putExtra(LoginActivity.RESTAURANT_NAME_KEY, viewModel.restaurantName.value)
        intent.putExtra(LoginActivity.EMAIL_ADDRESS_KEY, viewModel.emailAddress.value)
        intent.putExtra(LoginActivity.HOME_ADDRESS_KEY, viewModel.homeAddress.value)

        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        finishAffinity()
    }
}