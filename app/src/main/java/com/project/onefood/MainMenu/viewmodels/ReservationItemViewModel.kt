package com.project.onefood.MainMenu.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.onefood.Databases.ReservationDB.ReservationItem
import com.project.onefood.Login.data.AccountType

class ReservationItems() {
    var list : ArrayList<ReservationItem> = arrayListOf()
}