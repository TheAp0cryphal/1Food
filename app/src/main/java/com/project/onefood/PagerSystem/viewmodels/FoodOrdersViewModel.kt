package com.project.onefood.PagerSystem.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.onefood.PagerSystem.data.FoodOrders

class FoodOrdersViewModel: ViewModel() {
    val foodOrders: MutableLiveData<FoodOrders> = MutableLiveData<FoodOrders>(FoodOrders())
}