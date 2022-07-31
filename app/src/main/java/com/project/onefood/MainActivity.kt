package com.project.onefood

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatDelegate
import com.project.onefood.MainMenu.MainMenuActivity

class MainActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    override fun onResume() {
        super.onResume()
        initAutoloader()
    }

    // Loading progress bar for dataloader API
    private fun initAutoloader() {

        runOnUiThread {
            val progressBar: ProgressBar = findViewById(R.id.progressBar)
            for (i in 0..100){
                progressBar.setProgress(i, true)
            }
        }
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }
}