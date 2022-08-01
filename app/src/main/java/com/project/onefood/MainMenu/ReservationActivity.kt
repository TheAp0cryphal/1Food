package com.project.onefood.MainMenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.onefood.MainMenu.PromoAdapter.PromoRecyclerView
import com.project.onefood.MainMenu.ReservationAdapter.ReservationRecyclerView
import com.project.onefood.R

class ReservationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        val recyclerView: RecyclerView = findViewById(R.id.reservationrecycler)
        val adapter = ReservationRecyclerView()

        val emptyText: TextView = findViewById(R.id.empty)
        //if(dataset.isEmpty(){
        //emptyText.isVisible = true
        //else
        emptyText.isVisible = false



        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
    }
}