package com.project.onefood.Databases.ReservationDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "reservation_table")
data class ReservationItem(
    val restaurantName : String,
    val date : String,
    val time : String,
    val members : Int,
    val latitude : Double,
    val longitude : Double
) {
    @PrimaryKey (autoGenerate = true) var id : Long = 0
}