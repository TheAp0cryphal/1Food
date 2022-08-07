package com.project.onefood.PagerSystem

import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.project.onefood.MainMenu.MainMenuActivity
import com.project.onefood.PagerSystem.data.FoodOrder
import com.project.onefood.PagerSystem.fragments.FoodOrderFragment
import com.project.onefood.PagerSystem.fragments.FoodOrderListFragment
import com.project.onefood.PagerSystem.fragments.NewFoodOrderFragment
import com.project.onefood.R
import com.project.onefood.databinding.ActivityFoodOrdersBinding

private const val TAG: String = "FoodOrdersActivity"

class FoodOrdersActivity : AppCompatActivity() {

    // UI
    private lateinit var binding: ActivityFoodOrdersBinding

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity(savedInstanceState)

        setListeners()
    }

    // Actions on back pressed
    override fun onBackPressed() {
        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.foodOrdersFrameLayout)
        when (fragment) {
            is NewFoodOrderFragment -> switchToFoodOrderListFragment(supportFragmentManager)
            is FoodOrderFragment -> switchToFoodOrderListFragment(supportFragmentManager)
            else -> switchToMainMenuActivity(this)
        }
    }

    // Initialize the activity
    private fun initActivity(savedInstanceState: Bundle?) {
        binding = ActivityFoodOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            switchToFoodOrderListFragment(supportFragmentManager)
        }
    }

    // Set the listeners
    private fun setListeners() {
        binding.restaurantPagerSystemTextView.setOnClickListener {
            val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.foodOrdersFrameLayout)
            when (fragment) {
                is NewFoodOrderFragment -> switchToFoodOrderListFragment(supportFragmentManager)
                is FoodOrderFragment -> switchToFoodOrderListFragment(supportFragmentManager)
                else -> switchToMainMenuActivity(this)
            }
        }
    }

    companion object {
        const val ORDER_NUMBER_KEY: String = "ORDER_NUMBER_KEY"
        const val ORDER_TIME_KEY: String = "ORDER_TIME_KEY"
        const val CODE_KEY: String = "CODE_KEY"
        const val REMARKS_KEY: String = "REMARKS_KEY"

        // Switch to food order list fragment
        fun switchToFoodOrderListFragment(supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.foodOrdersFrameLayout, FoodOrderListFragment())
                commit()
            }
        }

        // Switch to new food order fragment
        fun switchToNewFoodOrderFragment(supportFragmentManager: FragmentManager) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.foodOrdersFrameLayout, NewFoodOrderFragment())
                commit()
            }
        }

        // Switch to food order fragment
        fun switchToFoodOrderFragment(supportFragmentManager: FragmentManager, foodOrder: FoodOrder) {
            supportFragmentManager.beginTransaction().apply {
                val fragment: Fragment = FoodOrderFragment()
                val bundle: Bundle = Bundle()
                bundle.putInt(ORDER_NUMBER_KEY, foodOrder.orderNumber)
                bundle.putLong(ORDER_TIME_KEY, foodOrder.orderTime)
                bundle.putString(CODE_KEY, foodOrder.code)
                bundle.putString(REMARKS_KEY, foodOrder.remarks)
                fragment.arguments = bundle

                replace(R.id.foodOrdersFrameLayout, fragment)
                commit()
            }
        }

        // Switch to main menu activity
        fun switchToMainMenuActivity(context: Context) {
            val intent: Intent = Intent(context, MainMenuActivity::class.java)
            context.startActivity(intent)
        }

        // Get the food order time string
        fun getFoodOrderTimeString(context: Context, time: Long): String {
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = time

//            val timeFormat: SimpleDateFormat = if (SettingsFragment.is24HourFormat(context))
//                SimpleDateFormat("HH:mm:ss")
//            else
//                SimpleDateFormat("h:mm:ss a")
            val timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm:ss")

            return timeFormat.format(calendar.time)
        }
    }
}