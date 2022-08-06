package com.project.onefood.RestaurantsList

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.project.onefood.R
import com.project.onefood.Databases.TinyDB
import com.project.onefood.findSimilarity
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import kotlin.collections.ArrayList


class RestaurantsListActivity : AppCompatActivity() {

    lateinit var list: ArrayList<RestaurantItem>
    lateinit var tinyDB : TinyDB
    lateinit var recyclerView : RecyclerView
    lateinit var adapter: RestaurantsRecyclerView
    lateinit var searchView: SearchView
    lateinit var nearestBtn: Button
    var isResultsForSearch = false
    var queryString: String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var userLongitude: String
    private lateinit var userLatitude: String
    var isNearestBtnPressed = false

    override fun onResume() {
        super.onResume()
        setRecommendations()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_list)

        // Initialize the SDK
        Places.initialize(applicationContext, "AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA")

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)

        list = ArrayList()
        // list.add(RestaurantItem("Pad", "236 Murodck", 30.0, "", LatLng(49.299170, -122.817300), "12345", "true", "2345"))
        //list.add(RestaurantItem("Pad", "236 Murodck", 30.0, "", LatLng(49.299170, -122.817300),))
        searchView = findViewById(R.id.searchVieww)
        nearestBtn = findViewById(R.id.nearestBtn)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastKnownLocation()
        userLongitude = ""
        userLatitude = ""

        //TinyDB init
        tinyDB = TinyDB(applicationContext)

        recyclerView = findViewById(R.id.restaurantsRecycler)
        adapter = RestaurantsRecyclerView(this, list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        nearestBtn.setOnClickListener {
            if (!isNearestBtnPressed){
                isNearestBtnPressed = true
                val nearestTh = Thread {
                    try {
                        callApi()
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                nearestBtn.text = getString(R.string.Clear)
                nearestTh.start()
            }else{
                isNearestBtnPressed = false
                val clearNearestTh = Thread {
                    try {
                        callApi()
                    }
                    catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                nearestBtn.text = getString(R.string.Nearest)
                clearNearestTh.start()
            }

        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                isResultsForSearch = true
                queryString = query

                addQueryToRecommendationList(queryString.toString())

                isNearestBtnPressed = false
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
                    isNearestBtnPressed = false
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
        if (isNearestBtnPressed){
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?types=restaurant&rankby=distance&location=${userLatitude},${userLongitude}&sensor=true&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA"
        } else if (!isResultsForSearch){
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${userLatitude},${userLongitude}&radius=500000&type=restaurant&keyword=restaurant&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA"
        }else {
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=${userLatitude},${userLongitude}&radius=500000&type=restaurant&keyword=${queryString}&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA"
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

//                val restaurantAddress = jsonRestaurant.optString("plus_code")
//                val parseAddress = JSONObject(restaurantAddress.toString())
//                val restaurantAccurateAddress = parseAddress.optString("compound_code")
                val restaurantAccurateAddress = jsonRestaurant.optString("vicinity")


                val isRestaurantOpen = jsonRestaurant.optString("opening_hours")
                var parseOpening: JSONObject? = null
                var restaurantOpeningResult = ""

                if (isRestaurantOpen == null){
                    parseOpening = JSONObject(isRestaurantOpen.toString())
                    restaurantOpeningResult = parseOpening.optString("open_now") //is restaurant open? true, false
                }


                val restaurantCoordinates = jsonRestaurant.optString("geometry")
                val parseCoordinates = JSONObject(restaurantCoordinates.toString())
                val restaurantLocation = parseCoordinates.optString("location")
                val parseLocation = JSONObject(restaurantLocation.toString())
                val restaurantLatResult = parseLocation.optString("lat").toDouble()
                val restaurantLngResult = parseLocation.optString("lng").toDouble()

                val restaurantRating = jsonRestaurant.optString("rating")
                val place_id = jsonRestaurant.optString("place_id")

                var restaurantPhotoReference = ""
                var restaurantPhoto = jsonRestaurant.optJSONArray("photos")
                if (restaurantPhoto != null){
                    restaurantPhoto = jsonRestaurant.optJSONArray("photos")
                    val parsePhoto = JSONObject(restaurantPhoto!![0].toString())
                    restaurantPhotoReference = parsePhoto.optString("photo_reference")
                }

                list.add(RestaurantItem(restaurantName,
                    restaurantAccurateAddress,
                    calculateDistanceUserToRestaurant(restaurantLatResult, restaurantLngResult).toDouble(),
                    "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${restaurantPhotoReference}&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA",
                    LatLng(restaurantLatResult,
                        restaurantLngResult),
                    restaurantRating, restaurantOpeningResult, place_id))

            }
        }
        runOnUiThread {
            adapter.notifyDataSetChanged()
        }
    }

    private fun calculateDistanceUserToRestaurant(restaurantLatResult: Double, restaurantLngResult: Double): String {
        var distance = FloatArray(1)
        Location.distanceBetween(restaurantLatResult, restaurantLngResult, userLatitude.toDouble(), userLongitude.toDouble(), distance)
        return "%.2f".format(distance[0] / 1000)
    }

    private fun addQueryToRecommendationList(queryString: String) {

        var similarityGradient : Double = 0.60

        var cuisineList = resources.getStringArray(R.array.cuisines)
        var recommendationList : ArrayList<Int> = arrayListOf()

        for(i in cuisineList.indices){
            recommendationList.add(0)
        }

        if(tinyDB.objectExists("recommendation_list")) {
            recommendationList = tinyDB.getListInt("recommendation_list")
        }

        for(i in cuisineList.indices) {
            //Log.d("simi", cuisineList[i] + " " + queryString + " " + findSimilarity(queryString, cuisineList[i]).toString())
            if (findSimilarity(queryString, cuisineList[i]) > similarityGradient){
                recommendationList[i]++
                break
            }
        }
        tinyDB.putListInt("recommendation_list", recommendationList)
        //Log.d("RecommendationList", recommendationList.toString())

        setRecommendations()
    }

    private fun setRecommendations(){

        var recommendationList : ArrayList<Int> = arrayListOf()

        if(tinyDB.objectExists("recommendation_list")) {
            recommendationList = tinyDB.getListInt("recommendation_list")
        }

        var topThreeIndices = arrayListOf<Int>()

        for (i in 0..2){
            val maxIndex = recommendationList.indices.maxByOrNull {
                recommendationList[it]
            } ?: 20

            topThreeIndices.add(maxIndex)

            if (recommendationList.isNotEmpty()) {
                recommendationList[maxIndex] = -1
            }

            //Log.d("recommendationMaxOriginalIndex", topThreeIndices.toString())
        }
        var top1 = findViewById<TextView>(R.id.top1)
        var top2 = findViewById<TextView>(R.id.top2)
        var top3 = findViewById<TextView>(R.id.top3)

        var cuisineList = resources.getStringArray(R.array.cuisines)

        top1.text = cuisineList[topThreeIndices[0]]
        top2.text = cuisineList[topThreeIndices[1]]
        top3.text = cuisineList[topThreeIndices[2]]
    }
}