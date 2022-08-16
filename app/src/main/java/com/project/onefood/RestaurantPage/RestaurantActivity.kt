package com.project.onefood.RestaurantPage

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.project.onefood.R
import com.project.onefood.RestaurantPage.FragmentAdapters.ReviewItem
import com.project.onefood.RestaurantPage.FragmentAdapters.TabAdapter
import com.project.onefood.RestaurantPage.Fragments.MenuFragment
import com.project.onefood.RestaurantPage.Fragments.ReviewsFragment
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate

class RestaurantActivity : AppCompatActivity() {

    private var restaurantName : String = ""
    private var restaurantAddress : String = ""
    private lateinit var latLng : LatLng
    private var restaurantRating : String = ""
    private lateinit var reviewsArr: ArrayList<ReviewItem>
    private lateinit var photosArr: ArrayList<SlideModel>


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

        var addressTextView = findViewById<TextView>(R.id.restaurant_address)
        addressTextView.setOnClickListener {
            val intent = Intent(this, ReservationMapsActivity::class.java)
            intent.putExtra("restaurant_coordinates", latLng)
            startActivity(intent)
        }

    }
    val thread = Thread {
        try {
            callRestaurantDetailsApi(intent.getStringExtra("place_id").toString())
        }
        catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        thread.interrupt()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        reviewsArr = ArrayList<ReviewItem>()
        photosArr = ArrayList<SlideModel>()

        thread.start()

        initFragment()

        restaurantName = intent.getStringExtra("restaurant_name").toString()

        val restaurantNameTextView :TextView = findViewById(R.id.restaurant_name)
        restaurantNameTextView.text = restaurantName

        restaurantAddress = intent.getStringExtra("restaurant_address").toString()

        val restaurantAddressTextView : TextView = findViewById(R.id.restaurant_address)
        restaurantAddressTextView.text = restaurantAddress

        val restaurant_img = intent.getStringExtra("restaurant_img").toString()

        //val restaurantImgImageView : ImageView = findViewById(R.id.imageView3)
        //Picasso.get().load(restaurant_img).into(restaurantImgImageView);



        latLng = intent.getParcelableExtra<LatLng>("restaurant_coordinates")!!

        restaurantRating = intent.getStringExtra("restaurant_rating").toString()

        val restaurantRatingTextView : TextView = findViewById(R.id.restaurant_rating)
        restaurantRatingTextView.text = restaurantRating

//        val restaurantStatus = "Status : Open"
//
//        val restaurantStatusTextView : TextView = findViewById(R.id.restaurant_status)
//        restaurantStatusTextView.text = restaurantStatus

        val restaurantDistanceTextView : TextView = findViewById(R.id.restaurant_distance)
        restaurantDistanceTextView.text = (intent.getStringExtra("restaurant_distance"))

    }

    private lateinit var menuFragment: MenuFragment
    private lateinit var reviewsFragment: ReviewsFragment
    private lateinit var fragments: ArrayList<Fragment>
    private lateinit var tab: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var tabAdapter: FragmentStateAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy
    private val tabText = arrayOf("Menu","Reviews")

    private fun initFragment() {
        menuFragment = MenuFragment()
        reviewsFragment= ReviewsFragment()

        val bundleReviews = Bundle()
        bundleReviews.putSerializable("reviewsArr", reviewsArr)
        reviewsFragment.arguments = bundleReviews

        fragments = ArrayList()

        fragments.add(menuFragment)
        fragments.add(reviewsFragment)

        tab= findViewById(R.id.tabLayout)
        viewPager= findViewById(R.id.viewPager)
        tabAdapter = TabAdapter(this,fragments)
        viewPager.adapter = tabAdapter
        tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy(){
                tab:TabLayout.Tab, position:Int->
            tab.text= tabText[position]
        }
        tabLayoutMediator = TabLayoutMediator(tab,viewPager,tabConfigurationStrategy)
        tabLayoutMediator.attach()
    }

    private fun callRestaurantDetailsApi(placeid: String){
        val request = Request.Builder().url("https://maps.googleapis.com/maps/api/place/details/json?placeid=${placeid}&key=").build()


        val response = OkHttpClient().newCall(request).execute().body?.string()
        val jsonObject = JSONObject(response!!) // This will make the json below as an object

        val results = jsonObject.getString("result")
        val jsonResults = JSONObject(results)

        val restaurantReviews = jsonResults.optJSONArray("reviews")

        val restaurantOpeningHours = jsonResults.optString("opening_hours")
        val openingHoursResults = JSONObject(restaurantOpeningHours)
        val openNow = openingHoursResults.optString("open_now")
        val weekdayText = openingHoursResults.optJSONArray("weekday_text")
        updateTimings(weekdayText)

        val restaurantPhotos = jsonResults.optJSONArray("photos")
        if (restaurantPhotos != null){
            for (i in 0 until restaurantPhotos.length()){
                val photoJson = JSONObject(restaurantPhotos[i].toString())
                val photo_reference = photoJson.optString("photo_reference")
                if (photo_reference!=""){
                    photosArr.add(SlideModel("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=${photo_reference}&key=", ""))
                }
            }
        }
        val imageSlider = findViewById<ImageSlider>(R.id.image_slider)
        runOnUiThread{
            imageSlider.setImageList(photosArr, ScaleTypes.FIT)
        }



        val isOpen = if (openNow.toBoolean()) "Open" else "Not open"
        val restaurantStatusTextView : TextView = findViewById(R.id.restaurant_status)
        restaurantStatusTextView.text = "Status: $isOpen"



        if (restaurantReviews != null){
            for (i in 0 until restaurantReviews.length()){
                val reviewJson = JSONObject(restaurantReviews[i].toString())
                val author_name = reviewJson.getString("author_name")
                val profile_photo_url = reviewJson.getString("profile_photo_url")
                val rating = reviewJson.getString("rating")
                val relative_time_description = reviewJson.getString("relative_time_description")
                val text = reviewJson.getString("text")

                reviewsArr.add(ReviewItem(author_name, profile_photo_url, rating, text, relative_time_description))
            }
        }
    }

    private fun updateTimings(weekdayText: JSONArray?) {

        var openTimes = findViewById<TextView>(R.id.openTimes)

        if (weekdayText != null) {
            Log.d("weekdayText",weekdayText.toString())
            openTimes.text = weekdayText.getString(LocalDate.now().dayOfWeek.value - 1)
        }
    }
}