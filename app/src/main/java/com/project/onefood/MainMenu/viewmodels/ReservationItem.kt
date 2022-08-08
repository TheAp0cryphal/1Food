package com.project.onefood.MainMenu.viewmodels

data class ReservationItem (
    val restaurantName : String = "",
    val date : String = "",
    val time : String = "",
    val members : Int = 0,
    val latitude : Double = 0.0,
    val longitude : Double = 0.0
)