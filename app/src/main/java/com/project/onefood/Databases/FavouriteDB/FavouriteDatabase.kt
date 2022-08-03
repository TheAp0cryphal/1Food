package com.project.onefood.Databases.FavouriteDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =
[FavouriteItem::class],
    version = 1)

abstract class FavouriteDatabase: RoomDatabase() {
    abstract val FavouriteDatabaseDao : FavouriteDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE : FavouriteDatabase? = null

        fun getInstance(context : Context) : FavouriteDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext,
                        FavouriteDatabase::class.java, "Favourite_database"
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