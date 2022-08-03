package com.project.onefood.RestaurantPage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private lateinit var reservationDatabaseDao: ReservationDatabaseDao

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

        var save : Button = findViewById(R.id.savebutton)
        save.setOnClickListener {

            var numOfPeopleEditText = findViewById<EditText>(R.id.numOfPeople)
            var numOfPeople = numOfPeopleEditText.text

            var latLng = intent.getParcelableExtra<LatLng>("restaurant_coordinates")!!

            Log.d("Coords2", "" + latLng.latitude + " " + latLng.longitude)

            CoroutineScope(IO).launch{
                reservationDatabaseDao.insert(ReservationItem(intent.getStringExtra("restaurant_name").toString(),
                    myDate,
                    myTime,
                    numOfPeople.toString().toInt(),
                    latLng.latitude,
                    latLng.longitude))
            }
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
    }
}