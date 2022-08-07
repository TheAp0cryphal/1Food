/*
 * File Name: FoodOrders.kt
 * File Description: Store the all the food orders
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/07
 */
package com.project.onefood.PagerSystem.data

data class FoodOrders(
    var maxOrderNumber: Int = 0,
    var foodOrderList: ArrayList<FoodOrder> = arrayListOf()
)