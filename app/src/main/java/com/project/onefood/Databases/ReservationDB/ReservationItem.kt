package com.project.onefood.Databases.ReservationDB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation_table")
data class ReservationItem(
    val restaurantName : String,
    val date : String,
    val time : String,
    val members : Int) {
    @PrimaryKey (autoGenerate = true) var id : Long = 0
}