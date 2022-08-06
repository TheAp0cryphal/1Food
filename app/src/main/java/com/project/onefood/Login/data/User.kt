/*
 * File Name: User.kt
 * File Description: Store the user info
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/06
 */
package com.project.onefood.Login.data

enum class AccountType {
    CUSTOMER, RESTAURANT_MANAGER, ANONYMOUS, DEVELOPER
}

data class User(
    var accountType: AccountType = AccountType.ANONYMOUS,

    var firstName: String = "",
    var lastName: String = "",

    var restaurantName: String = "",

    var emailAddress:String = "",

    var homeAddress: String = ""
)