package com.project.onefood.MainMenu

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
import com.project.onefood.R
import com.project.onefood.RestaurantsList.RestaurantItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class ReservationActivity : AppCompatActivity() {

    var list: List<ReservationItem> = listOf()
    private lateinit var recyclerView: RecyclerView
    private lateinit var reservationItemRepository : ReservationItemRepository
    private lateinit var reservationDatabaseDao : ReservationDatabaseDao
    private lateinit var reservationItemViewModelFactory: ReservationItemViewModelFactory
    private lateinit var reservationItemViewModel: ReservationItemViewModel
    private lateinit var reservationItemAdapter : ReservationRecyclerView


    override fun onResume() {
        super.onResume()

        val emptyText: TextView = findViewById(R.id.empty)

        getFromFirebase()

        reservationItemViewModel.allReservationItemsLiveData.observe(this, Observer { it ->

            emptyText.isVisible = it.isEmpty()

            reservationItemAdapter.replace(it)
            reservationItemAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        reservationDatabaseDao = ReservationDatabase.getInstance(this).reservationDatabaseDao
        reservationItemRepository = ReservationItemRepository(reservationDatabaseDao)
        reservationItemViewModelFactory = ReservationItemViewModelFactory(repository = reservationItemRepository)
        reservationItemViewModel = ViewModelProvider(this, reservationItemViewModelFactory)
            .get(ReservationItemViewModel::class.java)

        recyclerView = findViewById(R.id.reservationrecycler)

        /*
        reservationItemAdapter = ReservationRecyclerView(this, list)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = reservationItemAdapter

         */

        reservationItemAdapter = ReservationRecyclerView(this@ReservationActivity, getFromFirebase())
    }

    private fun getFromFirebase() : ArrayList<ReservationItem> {

        Log.d("checkReturn2345", "here")

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))

        val uid: String = firebaseAuth.currentUser!!.uid
        var reservationList = arrayListOf<ReservationItem>()

        firebaseDatabase.getReference(getString(R.string.firebase_database_reservations)).child(uid).addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    reservationList.clear()
                    for (postSnapshot : DataSnapshot in p0.children){
                        Log.d("checkReturn", postSnapshot.toString())
                        val reservationItem = p0.getValue(ReservationItem::class.java)
                        if (reservationItem != null) {
                            reservationList.add(reservationItem)
                        }
                    }

                    reservationItemAdapter = ReservationRecyclerView(this@ReservationActivity, reservationList)
                    recyclerView.layoutManager = LinearLayoutManager(this@ReservationActivity, LinearLayoutManager.VERTICAL, false)
                    recyclerView.adapter = reservationItemAdapter
                }

                override fun onCancelled(p0: DatabaseError) {
                    //TODO("Not yet implemented")
                }
            }
        )
        return reservationList
    }
}