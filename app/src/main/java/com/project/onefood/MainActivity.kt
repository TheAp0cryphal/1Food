/*
 * File Name: MainActivity.kt
 * File Description: Provide an auto login function and load the data from database
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/06
 */
package com.project.onefood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.onefood.Login.LoginActivity
import com.project.onefood.Login.data.User
import com.project.onefood.databinding.ActivityMainBinding

private const val TAG: String = "MainActivity"

class MainActivity : AppCompatActivity()  {

    // UI
    private lateinit var binding: ActivityMainBinding

    // Firebase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    // Actions on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initActivity()
        switchToAnotherActivity()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))
    }

    // Switch to another activity
    private fun switchToAnotherActivity() {
        val uid: String? = firebaseAuth.currentUser?.uid

        if (uid == null) {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            loadDataFromDatabase(uid)
        }
    }

    // Load the data from the database
    private fun loadDataFromDatabase(uid: String) {
        firebaseDatabase.getReference(getString(R.string.firebase_database_users)).child(uid).addListenerForSingleValueEvent(
            object: ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val user: User? = p0.getValue(User::class.java)
                    if (user != null) {
                        LoginActivity.switchToMainMenuActivity(binding.root.context, user, false)
                    }
                }

                override fun onCancelled(p0: DatabaseError) {}
            }
        )
    }
}