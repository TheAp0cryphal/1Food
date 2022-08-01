package com.project.onefood.RestaurantsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.R

class RestaurantsListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants_list)

        val list: ArrayList<RestaurantItem> = ArrayList()
        list.add(RestaurantItem("King Pad", "Vancouver", 39.2, ""))
        list.add(RestaurantItem("Paramount", "Vancouver", 22.2, ""))
        list.add(RestaurantItem("KFC", "Vancouver", 3.2, ""))
        list.add(RestaurantItem("Domino`s", "Vancouver", 234.2, ""))
        list.add(RestaurantItem("Yo", "Vancouver", 22.2, ""))
        list.add(RestaurantItem("Wat", "Vancouver", 3.2, ""))
        list.add(RestaurantItem("Rest to rant", "asda", 123.2, ""))


        val recyclerView : RecyclerView = findViewById(R.id.restaurantsRecycler)
        val adapter = RestaurantsRecyclerView(this, list)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}