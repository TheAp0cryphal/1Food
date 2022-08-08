package com.project.onefood.MainMenu

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.project.onefood.Databases.ReservationDB.*
import com.project.onefood.MainMenu.PromoAdapter.PromoRecyclerView
import com.project.onefood.MainMenu.ReservationAdapter.ReservationRecyclerView
import com.project.onefood.MainMenu.viewmodels.ReservationItem
import com.project.onefood.R
import com.project.onefood.RestaurantsList.RestaurantItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class ReservationActivity : AppCompatActivity() {

    //var list: List<ReservationItem> = listOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var reservationItemAdapter : ReservationRecyclerView


    override fun onResume() {
        super.onResume()
        getFromFirebase()

       // reservationItemViewModel.allReservationItemsLiveData.observe(this, Observer { it ->

         //   emptyText.isVisible = it.isEmpty()

       //     reservationItemAdapter.replace(it)
      //      reservationItemAdapter.notifyDataSetChanged()
       // })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        recyclerView = findViewById(R.id.reservationrecycler)

        /*
        reservationItemAdapter = ReservationRecyclerView(this, list)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = reservationItemAdapter

         */

        //reservationItemAdapter = ReservationRecyclerView(this@ReservationActivity, getFromFirebase())
        reservationItemAdapter = ReservationRecyclerView(this@ReservationActivity, getFromFirebase())
        recyclerView.layoutManager = LinearLayoutManager(this@ReservationActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = reservationItemAdapter
    }

    private fun getFromFirebase() : ArrayList<ReservationItem> {

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))

        val uid: String = firebaseAuth.currentUser!!.uid

        var reservationList = arrayListOf<ReservationItem>()

        firebaseDatabase.getReference(getString(R.string.firebase_database_reservations)).child(uid).addListenerForSingleValueEvent(
            object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(p0: DataSnapshot) {
                    reservationList.clear()
                    //reservationList = p0.getValue(ReservationItem::class.java)
                    for (postSnapshot : DataSnapshot in p0.children){
                        val reservationItem = postSnapshot.getValue(ReservationItem::class.java)

                        Log.d("checkReturn", reservationItem.toString() + " " + p0.childrenCount)

                        if (reservationItem != null) {
                            val emptyText: TextView = findViewById(R.id.empty)
                            emptyText.isVisible = false

                            reservationList.add(reservationItem)
                            reservationItemAdapter.replace(reservationList)
                            reservationItemAdapter.notifyDataSetChanged()
                        }
                    }
                }
                override fun onCancelled(p0: DatabaseError) {
                    //TODO("Not yet implemented")
                }
            }
        )
        return reservationList
    }
}