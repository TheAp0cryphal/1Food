package com.project.onefood.MainMenu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.auth.FirebaseAuth
import com.project.onefood.Login.LoginActivity
import com.project.onefood.R
import com.project.onefood.databinding.ActivityUserProfileBinding
import com.project.onefood.showtoast

class UserProfileActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

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

        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        val spinner: Spinner = findViewById(R.id.spinner)
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.languages,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        showtoast(this, p2.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //
    }
}