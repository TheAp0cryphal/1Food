/*
 * File Name: EntryRepository.kt
 * File Description: A repository of food order database
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem.databases

import androidx.lifecycle.LiveData
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.concurrent.Callable

class FoodOrderRepository(private val foodOrderDao: FoodOrderDao) {

    val allFoodOrders: LiveData<List<FoodOrder>> = foodOrderDao.getAllFoodOrders()

    // Insert a food order
    fun insertFoodOrder(foodOrder: FoodOrder): Single<Long> {
        return Single.fromCallable(
            Callable<Long> { foodOrderDao.insertFoodOrder(foodOrder) }
        )
    }

    // Delete a food order
    fun deleteFoodOrder(id: Long) {
        CoroutineScope(IO).launch {
            foodOrderDao.deleteFoodOrder(id)
        }
    }

    // Delete all food orders
    fun deleteAllFoodOrders() {
        CoroutineScope(IO).launch {
            foodOrderDao.deleteAllFoodOrders()
        }
    }
}