package com.project.onefood.RestaurantsList

import com.google.android.gms.maps.model.LatLng

data class RestaurantItem(val name:String,
                          val address: String,
                          val distance: Double,
                          val img: String,
                          val latLng : LatLng,
                          val rating : String,
                          val status : String,
                          val place_id: String) {

}