package com.project.onefood.MainMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.project.onefood.Login.LoginActivity
import com.project.onefood.R
import com.project.onefood.databinding.ActivityUserProfileBinding
import java.util.*

class UserProfileActivity : AppCompatActivity(){

    // UI
    private lateinit var binding: ActivityUserProfileBinding

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()

        setListeners()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Set the listeners
    private fun setListeners() {
        binding.logoutButton.setOnClickListener {
            clickLogoutButton()
        }
    }

    // Click the logout button
    private fun clickLogoutButton() {
        FirebaseAuth.getInstance().signOut()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        var username = findViewById<TextView>(R.id.user_name)
        var name = intent.getStringExtra("customer_name")

        username.text = name

        var myLanguage = findViewById<TextView>(R.id.myLanguage)
        myLanguage.text = Locale.getDefault().displayName

        var settings = findViewById<LinearLayout>(R.id.settings)

        settings.setOnClickListener {
            var intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }
    }


//        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                when (position) {
//                    0 -> {
//                        LocaleHelper.setLocale(applicationContext, "en")
//                    }
//                    1 -> {
//                        showtoast(applicationContext, "1")
//                    }
//                    else -> {
//                        LocaleHelper.setLocale(applicationContext, "fr")
//                        recreate()
//
//                    }
//                }
//            }
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                //
//            }
//        }
    }