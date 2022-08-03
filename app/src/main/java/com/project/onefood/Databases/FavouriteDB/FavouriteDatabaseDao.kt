package com.project.onefood.Databases.FavouriteDB

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDatabaseDao {

    @Insert
    fun insert(FavouriteItem: FavouriteItem)
    @Query("DELETE FROM Favourite_table where place_id = :myId")
    fun deleteById(myId : String)
    @Delete
    fun delete(FavouriteItem: FavouriteItem)
    @Query("SELECT * FROM Favourite_table")
    fun getAll(): Flow<List<FavouriteItem>>

    @Query("SELECT * From Favourite_table  where place_id = :myId")
    fun search(myId : String): List<FavouriteItem>



}