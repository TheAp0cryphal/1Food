package com.project.onefood.Databases.ReservationDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "reservation_table")
data class ReservationItem (
    val restaurantName : String = "",
    val date : String = "",
    val time : String = "",
    val members : Int = 0,
    val latitude : Double = 0.0,
    val longitude : Double = 0.0
) {
    @PrimaryKey (autoGenerate = true) var id : Long = 0L
}