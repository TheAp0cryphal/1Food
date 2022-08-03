package com.project.onefood.Databases.FavouriteDB

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import java.lang.IllegalArgumentException

class FavouriteItemViewModel(private val repository: FavouriteItemRepository) : ViewModel() {
    val allFavouriteItemsLiveData: LiveData<List<FavouriteItem>> =
        repository.allFavouriteItems.asLiveData()

    fun insert(FavouriteItem: FavouriteItem) {
        repository.insert(FavouriteItem)
    }

    fun delete(id: String) {
        repository.delete(id)
    }

    fun deleteFirst() {
        val FavouriteItemsList = allFavouriteItemsLiveData.value
        if (FavouriteItemsList != null && FavouriteItemsList.isNotEmpty()) {
            val id = FavouriteItemsList[0].place_id
            repository.delete(id)
        }
    }
}

    class FavouriteItemViewModelFactory(private val repository: FavouriteItemRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavouriteItemViewModel::class.java))
                return FavouriteItemViewModel(repository) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
