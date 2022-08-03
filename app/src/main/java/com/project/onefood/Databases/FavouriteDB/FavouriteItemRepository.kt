package com.project.onefood.Databases.FavouriteDB


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class FavouriteItemRepository(private val FavouriteDatabaseDao: FavouriteDatabaseDao) {

    val allFavouriteItems: Flow<List<FavouriteItem>> = FavouriteDatabaseDao.getAll()

    fun insert(FavouriteItem: FavouriteItem){
        CoroutineScope(IO).launch{
            FavouriteDatabaseDao.insert(FavouriteItem)
        }
    }

    fun delete(id: String){
        CoroutineScope(IO).launch {
            FavouriteDatabaseDao.deleteById(id)
        }
    }

    fun search(id: String){
        CoroutineScope(IO).launch {
            FavouriteDatabaseDao.search(id)
        }
    }

    fun getAll(){
        CoroutineScope(IO).launch {
            FavouriteDatabaseDao.getAll()
        }
    }
}