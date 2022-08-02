package com.project.onefood.RestaurantsList

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.project.onefood.R
import com.project.onefood.RestaurantPage.RestaurantActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject


class RestaurantsListActivity : AppCompatActivity() {

    lateinit var list: ArrayList<RestaurantItem>
    lateinit var recyclerView : RecyclerView
    lateinit var adapter: RestaurantsRecyclerView
    lateinit var searchView: SearchView
    var isResultsForSearch = false
    var queryString: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var userLongitude: String
    lateinit var userLatitude: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_list)

        // Initialize the SDK
        Places.initialize(applicationContext, "AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA")

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)

        list = ArrayList()
        list.add(RestaurantItem("Pad", "236 Murodck", 30.0, "", LatLng(49.299170, -122.817300)))
        searchView = findViewById(R.id.searchVieww)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastKnownLocation()


        recyclerView = findViewById(R.id.restaurantsRecycler)
        adapter = RestaurantsRecyclerView(this, list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                isResultsForSearch = true
                queryString = query
                if (query.isEmpty()){
                    queryString = null
                    isResultsForSearch = false
                }
                val searchTh = Thread {
                    try {
                        callApi()
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                searchTh.start()

                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()){
                    queryString = null
                    isResultsForSearch = false
                    val searchTh = Thread {
                        try {
                            callApi()
                        }
                        catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    searchTh.start()
                }
                return false
            }
        })

        thread.start();
    }

    private var thread = Thread {
        try {
            callApi()
        }
        catch (e: Exception) {
          e.printStackTrace()
        }

    }

    private var searchThread = Thread {
        try {
            callApi()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    userLongitude = location.longitude.toString()
                    userLatitude = location.latitude.toString()

                    val intiatedThread = Thread {
                        try {
                            callApi()
                        }
                        catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    intiatedThread.start()
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun callApi(){
        var url = ""
        if (!isResultsForSearch){
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${userLatitude},${userLongitude}&radius=50000&type=restaurant&keyword=cruise&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA"
        }else {
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${userLatitude},${userLongitude}&radius=50000&type=restaurant&keyword=${queryString}&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA"
        }

        list.clear()

        val request = Request.Builder().url(url).build()

        val response = OkHttpClient().newCall(request).execute().body?.string()
        val jsonObject = JSONObject(response!!) // This will make the json below as an object for you

        val json = JSONObject(jsonObject.toString()) // String instance holding the above json
        val results = json.getJSONArray("results")


        if (results.length() > 0) {
            for (i in 0 until results.length()) {
                val jsonRestaurant = JSONObject(results[i].toString())
                val restaurantName = jsonRestaurant.getString("name")

                val restaurantAddress = jsonRestaurant.getString("plus_code")
                val parseAddress = JSONObject(restaurantAddress.toString())
                val restaurantAccurateAddress = parseAddress.getString("compound_code")

                val isRestaurantOpen = jsonRestaurant.getString("opening_hours")
                val parseOpening = JSONObject(isRestaurantOpen.toString())
                val restaurantOpeningResult = parseOpening.getString("open_now") //is restaurant open? true, false

                val restaurantCoordinates = jsonRestaurant.getString("geometry")
                val parseCoordinates = JSONObject(restaurantCoordinates.toString())
                val restaurantLocation = parseCoordinates.getString("location")
                val parseLocation = JSONObject(restaurantLocation.toString())

                val restaurantLatResult = parseLocation.getString("lat")
                val restaurantLngResult = parseLocation.getString("lng")

                val restaurantRating = jsonRestaurant.getString("rating")

                list.add(RestaurantItem(restaurantName, restaurantAccurateAddress, 123.2, "", LatLng(restaurantLatResult.toDouble(), restaurantLngResult.toDouble())))

            }
        }
        runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }
}