package com.project.onefood.Databases.ReservationDB

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDatabaseDao {

    @Insert
    fun insert(reservationItem: ReservationItem)
    @Query("DELETE FROM reservation_table where id = :myId")
    fun deleteById(myId : Long)
    @Delete
    fun delete(reservationItem: ReservationItem)
    @Query("SELECT * FROM reservation_table")
    fun getAll(): Flow<List<ReservationItem>>



}