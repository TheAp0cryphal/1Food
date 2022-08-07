/*
 * File Name: FoodOrdersViewModel.kt
 * File Description: store all the food orders
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/07
 */
package com.project.onefood.PagerSystem.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.onefood.PagerSystem.data.FoodOrders

class FoodOrdersViewModel: ViewModel() {
    val _foodOrders: MutableLiveData<FoodOrders> = MutableLiveData<FoodOrders>(FoodOrders())
    var foodOrders: FoodOrders
        get() = _foodOrders.value!!
        set(value) { _foodOrders.value = value }
}