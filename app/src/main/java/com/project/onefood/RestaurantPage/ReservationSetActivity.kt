package com.project.onefood.RestaurantPage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.project.onefood.Databases.ReservationDB.ReservationDatabase
import com.project.onefood.Databases.ReservationDB.ReservationDatabaseDao
import com.project.onefood.Databases.ReservationDB.ReservationItem
import com.project.onefood.R
import com.project.onefood.showtoast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class ReservationSetActivity : AppCompatActivity() {

    private var myDate : String = ""
    private var myTime : String = ""
    private var numOfPeople : String = ""
    private lateinit var reservationDatabaseDao: ReservationDatabaseDao
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase


    private fun displayDatePicker() {

        val MONTHS = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )

        val inst = Calendar.getInstance()
        val year = inst.get(Calendar.YEAR)
        val month = inst.get(Calendar.MONTH)
        val day = inst.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, {view, yr, mth, day ->

            myDate = "${MONTHS[mth]} $day $yr"
            findViewById<EditText>(R.id.editTextDate).textAlignment = EditText.TEXT_ALIGNMENT_CENTER
            findViewById<EditText>(R.id.editTextDate).setText(myDate)

        }, year, month, day)

        datePickerDialog.show()
    }

    private fun displayTimePicker(){
        val inst = Calendar.getInstance()
        val timeListener = TimePickerDialog.OnTimeSetListener{ view, hour, min ->
            inst.set(Calendar.HOUR_OF_DAY, hour)
            inst.set(Calendar.MINUTE, min)
            myTime = SimpleDateFormat("HH:mm").format(inst.time)
            findViewById<EditText>(R.id.editTextTime).textAlignment = EditText.TEXT_ALIGNMENT_CENTER
            findViewById<EditText>(R.id.editTextTime).setText(myTime)
        }
        val timePickerDialog = TimePickerDialog(this, timeListener, inst.get(Calendar.HOUR_OF_DAY), inst.get(Calendar.MINUTE), false)
            .show()

    }

    override fun onResume() {
        super.onResume()
        var setDate : Button = findViewById(R.id.set_date)

        setDate.setOnClickListener {
            displayDatePicker()
        }

        var setTime : Button = findViewById(R.id.set_time)

        setTime.setOnClickListener {
            displayTimePicker()
        }

        var numOfPeopleEditText = findViewById<EditText>(R.id.numOfPeople)
        numOfPeopleEditText.setText("0")

        var save : Button = findViewById(R.id.savebutton)
        save.setOnClickListener {

            var restaurantName = intent.getStringExtra("restaurant_name").toString()
            var latLng = intent.getParcelableExtra<LatLng>("restaurant_coordinates")!!
            numOfPeople = numOfPeopleEditText.text.toString()

            var reservationItem = ReservationItem(
                restaurantName,
                myDate,
                myTime,
                numOfPeople.toInt(),
                latLng.latitude,
                latLng.longitude
            )

            /*
            CoroutineScope(IO).launch {
                reservationDatabaseDao.insert(
                    reservationItem
                )
            }

             */

            val uid: String = firebaseAuth.currentUser!!.uid
            firebaseDatabase.getReference(getString(R.string.firebase_database_reservations))
                .child(uid).setValue(reservationItem).addOnCompleteListener {
                // Successfully store customer info
                if (it.isSuccessful) {
                    showtoast(this@ReservationSetActivity, "Saved")
                }
                // Unsuccessfully store customer info
                else {
                    showtoast(this@ReservationSetActivity, "Error")
                }
            }

            var sendText =
                "Hi, I would like to request a reservation at $restaurantName on $myDate, $myTime for $numOfPeople people"

            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_TEXT, sendText)
            intent.type = "text/plain"
            this.startActivity(Intent.createChooser(intent, "Share Via"))

            showtoast(this, "Reservation has been made!")
        }

        var cancel : Button = findViewById(R.id.cancelbutton)
        cancel.setOnClickListener {
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_set_activity)

        reservationDatabaseDao = ReservationDatabase.getInstance(this).reservationDatabaseDao

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance(getString(R.string.firebase_database_instance_users))
    }
}