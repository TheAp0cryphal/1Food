package com.project.onefood.Databases.ReservationDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =
[ReservationItem::class],
    version = 1)

abstract class ReservationDatabase: RoomDatabase() {
    abstract val reservationDatabaseDao : ReservationDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE : ReservationDatabase? = null

        fun getInstance(context : Context) : ReservationDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        ReservationDatabase::class.java, "reservation_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}