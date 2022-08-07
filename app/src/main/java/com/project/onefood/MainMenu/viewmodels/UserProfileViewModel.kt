/*
 * File Name: UserProfileVieModel.kt
 * File Description: Store data for user profile activity
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/06
 */
package com.project.onefood.MainMenu.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.onefood.Login.data.AccountType

class UserProfileViewModel: ViewModel() {
    val accountType: MutableLiveData<AccountType> = MutableLiveData<AccountType>()
    val firstName: MutableLiveData<String> = MutableLiveData<String>()
    val lastName: MutableLiveData<String> = MutableLiveData<String>()
    val restaurantName: MutableLiveData<String> = MutableLiveData<String>()
    val emailAddress: MutableLiveData<String> = MutableLiveData<String>()
    val homeAddress: MutableLiveData<String> = MutableLiveData<String>()
}