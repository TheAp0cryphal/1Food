package com.project.onefood.RestaurantPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.project.onefood.R

class RestaurantActivity : AppCompatActivity() {

    private var restaurantName : String = ""
    private var restaurantAddress : String = ""

    override fun onResume() {
        super.onResume()
        var back : FloatingActionButton = findViewById(R.id.backfab)

        back.setOnClickListener {
            finish()
        }

        var res : FloatingActionButton = findViewById(R.id.reservationfab)
        res.setOnClickListener {
            val intent = Intent(this, ReservationSetActivity::class.java)
            intent.putExtra("restaurant_name", restaurantName)
            intent.putExtra("restaurant_address", restaurantAddress)
            startActivity(intent)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        restaurantName = intent.getStringExtra("restaurant_name").toString()

        var restaurantNameTextView :TextView = findViewById(R.id.restaurant_name)
        restaurantNameTextView.text = restaurantName

        restaurantAddress = intent.getStringExtra("restaurant_address").toString()

        var restaurantAddressTextView : TextView = findViewById(R.id.restaurant_address)
        restaurantAddressTextView.text = restaurantAddress

    }
}