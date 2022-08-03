package com.project.onefood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.project.onefood.Login.LoginActivity
import com.project.onefood.MainMenu.MainMenuActivity
import com.project.onefood.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity()  {

    // UI
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        initActivity()
    }

    override fun onResume() {
        super.onResume()
        initAutoloader()
    }

    // Initialize the activity
    private fun initActivity() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    // Loading progress bar for dataloader API
    private fun initAutoloader() {
        GlobalScope.launch {
            for (i in 0..100) {
                binding.progressBar.setProgress(i, true)

                if (i % 20 == 0) {
                    Thread.sleep(300)
                }
            }

            switchToAnotherActivity()
        }
    }

    // Switch to another activity
    private fun switchToAnotherActivity() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val intent: Intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}