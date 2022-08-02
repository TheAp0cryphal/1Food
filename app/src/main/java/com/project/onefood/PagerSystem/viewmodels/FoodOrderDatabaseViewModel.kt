/*
 * File Name: FoodOrderDatabaseViewModel.kt
 * File Description: Store data for food order repository
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.project.onefood.PagerSystem.databases.FoodOrder
import com.project.onefood.PagerSystem.databases.FoodOrderDao
import com.project.onefood.PagerSystem.databases.FoodOrderDatabase
import com.project.onefood.PagerSystem.databases.FoodOrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodOrderDatabaseViewModel(application: Application): AndroidViewModel(application) {

    val allFoodOrdersLiveData: LiveData<List<FoodOrder>>
    private val foodOrderDao: FoodOrderDao
    private val foodOrderRepository: FoodOrderRepository

    init {
        foodOrderDao = FoodOrderDatabase.getInstance(application).foodOrderDao
        foodOrderRepository = FoodOrderRepository(foodOrderDao)
        allFoodOrdersLiveData = foodOrderRepository.allFoodOrders
    }

    // Insert a food order
    fun insertFoodOrder(foodOrder: FoodOrder): Long {
        return foodOrderRepository.insertFoodOrder(foodOrder).blockingGet()
    }

    // Delete a food order
    fun deleteFoodOrder(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            foodOrderRepository.deleteFoodOrder(id)
        }
    }

    // Delete all food orders
    fun deleteAllFoodOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            foodOrderRepository.deleteAllFoodOrders()
        }
    }
}