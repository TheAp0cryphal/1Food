package com.project.onefood.RestaurantPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.onefood.R
import com.project.onefood.RestaurantPage.FragmentAdapters.TabAdapter
import com.project.onefood.RestaurantPage.Fragments.MenuFragment
import com.project.onefood.RestaurantPage.Fragments.ReviewsFragment
import com.squareup.picasso.Picasso

class RestaurantActivity : AppCompatActivity() {

    private var restaurantName : String = ""
    private var restaurantAddress : String = ""
    private lateinit var latLng : LatLng
    private var restaurantRating : String = ""

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
            intent.putExtra("restaurant_coordinates", latLng)
            startActivity(intent)
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        initFragment()

        restaurantName = intent.getStringExtra("restaurant_name").toString()

        var restaurantNameTextView :TextView = findViewById(R.id.restaurant_name)
        restaurantNameTextView.text = restaurantName

        restaurantAddress = intent.getStringExtra("restaurant_address").toString()

        var restaurantAddressTextView : TextView = findViewById(R.id.restaurant_address)
        restaurantAddressTextView.text = restaurantAddress

        val restaurant_img = intent.getStringExtra("restaurant_img").toString()

        var restaurantImgImageView : ImageView = findViewById(R.id.imageView3)
        Picasso.get().load(restaurant_img).into(restaurantImgImageView);



        latLng = intent.getParcelableExtra<LatLng>("restaurant_coordinates")!!

        restaurantRating = intent.getStringExtra("restaurant_rating").toString()

        var restaurantRatingTextView : TextView = findViewById(R.id.restaurant_rating)
        restaurantRatingTextView.text = restaurantRating

        var restaurantStatus = "Status : Open"

        var restaurantStatusTextView : TextView = findViewById(R.id.restaurant_status)
        restaurantStatusTextView.text = restaurantStatus
        Log.d("Coords", latLng.toString())

    }

    private lateinit var menuFragment: MenuFragment
    private lateinit var reviewsFragment: ReviewsFragment
    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var tab: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var tabAdapter: FragmentStateAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy
    private val TAB_TEXT = arrayOf("Menu","Reviews")

    private fun initFragment() {
        menuFragment = MenuFragment()
        reviewsFragment= ReviewsFragment()

        fragments = ArrayList()

        fragments.add(menuFragment)
        fragments.add(reviewsFragment)

        tab= findViewById(R.id.tabLayout)
        viewPager= findViewById(R.id.viewPager)
        tabAdapter = TabAdapter(this,fragments)
        viewPager.adapter = tabAdapter
        tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy(){
                tab:TabLayout.Tab, position:Int->
            tab.text= TAB_TEXT[position]
        }
        tabLayoutMediator = TabLayoutMediator(tab,viewPager,tabConfigurationStrategy)
        tabLayoutMediator.attach()
    }
}