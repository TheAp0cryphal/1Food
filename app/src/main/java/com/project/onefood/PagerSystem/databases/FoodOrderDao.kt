/*
 * File Name: FoodOrderDao.kt
 * File Description: An interface of food order database
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem.databases

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FoodOrderDao {

    // Insert a food order
    @Insert
    fun insertFoodOrder(foodOrder: FoodOrder): Long

    // Get all food orders
    @Query("SELECT * FROM food_order_table")
    fun getAllFoodOrders(): LiveData<List<FoodOrder>>

    // Delete a food order
    @Query("DELETE FROM food_order_table WHERE id = :key")
    suspend fun deleteFoodOrder(key: Long)

    // Delete all food orders
    @Query("DELETE FROM food_order_table")
    suspend fun deleteAllFoodOrders()
}