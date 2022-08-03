package com.project.onefood.Databases.FavouriteDB

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "Favourite_table", primaryKeys = ["place_id"])
data class FavouriteItem(
    val name : String,
    val address : String,
    val distance : Double,
    val img : String,
    val place_id: String,
)