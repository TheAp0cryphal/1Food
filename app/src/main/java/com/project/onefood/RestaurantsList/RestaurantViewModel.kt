package com.project.onefood.RestaurantsList
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//
//class RestaurantViewModel(private val list: ArrayList<RestaurantItem>) : ViewModel() {
//    val allActivitesEntriesLiveData: LiveData<List<RestaurantItem>> = list.asLiveData()
//
//    fun insert(activityEntry: ActivityDataClass) {
//        repository.insert(activityEntry)
//    }
//
//    fun deleteWithID(id: String){
//        repository.delete(id.toLong())
//    }
//}