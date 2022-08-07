/*
 * File Name: FoodOrderNotification.kt
 * File Description: Store the receiver uid
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/07
 */
package com.project.onefood.PagerSystem.data

data class FoodOrderNotification(
    var receiverNumber: Int = 0,
    var receivers: ArrayList<String> = arrayListOf()
)
