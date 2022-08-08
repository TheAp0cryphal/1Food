package com.project.onefood.MainMenu.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.onefood.Login.data.AccountType

class ReservationItemViewModel : ViewModel() {
    val restaurantName: MutableLiveData<String> = MutableLiveData<String>()
    val date: MutableLiveData<String> = MutableLiveData<String>()
    val time: MutableLiveData<String> = MutableLiveData<String>()
    val members: MutableLiveData<Int> = MutableLiveData<Int>()
    val latitude : MutableLiveData<Double> = MutableLiveData<Double>()
    val longitude : MutableLiveData<Double> = MutableLiveData<Double>()
}