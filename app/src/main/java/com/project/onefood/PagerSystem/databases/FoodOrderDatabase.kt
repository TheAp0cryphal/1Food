/*
 * File Name: FoodOrderDatabase.kt
 * File Description: A food order database instance
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FoodOrder::class], version = 1)
abstract class FoodOrderDatabase: RoomDatabase() {

    abstract val foodOrderDao: FoodOrderDao

    companion object {
        @Volatile
        private var INSTANCE: FoodOrderDatabase? = null

        // Get the database instance
        fun getInstance(context: Context): FoodOrderDatabase {
            synchronized(this) {
                var instance: FoodOrderDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, FoodOrderDatabase::class.java, "food_order_table").build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}