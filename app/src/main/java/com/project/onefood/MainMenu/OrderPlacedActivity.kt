package com.project.onefood.MainMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.project.onefood.R
import com.project.onefood.showtoast

class OrderPlacedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_placed)

        var id = intent.getStringExtra("ID")
        showtoast(this, id.toString())
    }
}