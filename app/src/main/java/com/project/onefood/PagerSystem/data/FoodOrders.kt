package com.project.onefood.PagerSystem.data

data class FoodOrders(
    var maxOrderNumber: Int = 0,
    var foodOrderList: ArrayList<FoodOrder> = arrayListOf()
)
