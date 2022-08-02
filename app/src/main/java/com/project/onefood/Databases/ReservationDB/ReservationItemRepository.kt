package com.project.onefood.Databases.ReservationDB


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReservationItemRepository(private val reservationDatabaseDao: ReservationDatabaseDao) {

    val allReservationItems: Flow<List<ReservationItem>> = reservationDatabaseDao.getAll()

    fun insert(reservationItem: ReservationItem){
        CoroutineScope(IO).launch{
            reservationDatabaseDao.insert(reservationItem)
        }
    }

    fun delete(id: Long){
        CoroutineScope(IO).launch {
            reservationDatabaseDao.deleteById(id)
        }
    }
}