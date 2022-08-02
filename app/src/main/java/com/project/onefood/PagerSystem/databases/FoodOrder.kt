/*
 * File Name: FoodOrder.kt
 * File Description: An entry of food order database
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem.databases

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_order_table")
data class FoodOrder (

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    var time: Long = 0,
    var code: String = "",
    var remark: String = ""
)