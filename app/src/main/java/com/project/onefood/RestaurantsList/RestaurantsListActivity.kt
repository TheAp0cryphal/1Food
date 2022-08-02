package com.project.onefood.RestaurantsList

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.project.onefood.R
import com.project.onefood.RestaurantPage.RestaurantActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import org.json.JSONObject

class RestaurantsListActivity : AppCompatActivity() {

    lateinit var list: ArrayList<RestaurantItem>
    lateinit var recyclerView : RecyclerView
    private lateinit var adapter: RestaurantsRecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_list)

        // Initialize the SDK
        Places.initialize(applicationContext, "AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA")

        // Create a new PlacesClient instance
        val placesClient = Places.createClient(this)

        list = ArrayList()
        list.add(RestaurantItem("King Pad", "Vancouver", 39.2, ""))

        recyclerView = findViewById(R.id.restaurantsRecycler)
        adapter = RestaurantsRecyclerView(this, list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        thread.start();
    }

    private var thread = Thread {
        try {
            val request = Request.Builder().url("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyDArS6HnLH9ggPb3wnZ1P08HNb2RhwNSoA").build()

            val response = OkHttpClient().newCall(request).execute().body?.string()
            val jsonObject = JSONObject(response!!) // This will make the json below as an object for you

            val json = JSONObject(jsonObject.toString()) // String instance holding the above json
            val results = json.getJSONArray("results")

            //list.clear()
            for (i in 0 until results.length()){
                val jsonRestaurant = JSONObject(results[i].toString())
                val restaurantName = jsonRestaurant.getString("name")

                val restaurantAddress = jsonRestaurant.getString("plus_code")
                val parseAddress = JSONObject(restaurantAddress.toString())
                val restaurantAccurateAddress = parseAddress.getString("compound_code")

                val restaurantRating = jsonRestaurant.getString("rating")

                list.add(RestaurantItem(restaurantName, restaurantAccurateAddress, 123.2, ""))

                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
                //adapter.notifyItemInserted(list.size - 1)
               //adapter.notifyDataSetChanged()
            }
            //recyclerView.adapter!!.notifyDataSetChanged()

            //recyclerView.adapter = RestaurantsRecyclerView(this, list)
            //recyclerView.invalidate();

            Log.d("listlist", list.toString())
        }
        catch (e: Exception) {
          e.printStackTrace()
        }
        /*
        adapter.setOnItemClickListener(object : RestaurantsRecyclerView.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@RestaurantsListActivity, RestaurantActivity::class.java)
                intent.putExtra("restaurant_name", list[position].name)
                intent.putExtra("restaurant_address", list[position].address)
                startActivity(intent)
                //intent.putExtra("restaurant_rating")
            }
        })

         */
    }
}