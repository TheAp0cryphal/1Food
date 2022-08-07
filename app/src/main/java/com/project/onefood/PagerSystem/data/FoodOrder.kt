/*
 * File Name: FoodOrder.kt
 * File Description: Store the food order info
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/07
 */
package com.project.onefood.PagerSystem.data

data class FoodOrder (
    var orderNumber: Int = 0,
    var orderTime: Long = 0,
    var code: String = "",
    var remarks: String = ""
)