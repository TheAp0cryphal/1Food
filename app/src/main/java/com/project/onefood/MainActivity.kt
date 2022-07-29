package com.project.onefood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initAutoloader()
    }

    private fun initAutoloader() {

        runOnUiThread {
            var progressBar: ProgressBar = findViewById(R.id.progressBar)

            for (i in 0..100){
                progressBar.setProgress(i, true)
            }
        }
    }
}