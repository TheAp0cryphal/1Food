package com.project.onefood.Databases.ReservationDB

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import java.lang.IllegalArgumentException

class ReservationItemViewModel(private val repository: ReservationItemRepository) : ViewModel() {
    val allReservationItemsLiveData: LiveData<List<ReservationItem>> =
        repository.allReservationItems.asLiveData()

    fun insert(reservationItem: ReservationItem) {
        repository.insert(reservationItem)
    }

    fun deleteFirst() {
        val reservationItemsList = allReservationItemsLiveData.value
        if (reservationItemsList != null && reservationItemsList.isNotEmpty()) {
            val id = reservationItemsList[0].id
            repository.delete(id)
        }
    }
}

    class ReservationItemViewModelFactory(private val repository: ReservationItemRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReservationItemViewModel::class.java))
                return ReservationItemViewModel(repository) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
