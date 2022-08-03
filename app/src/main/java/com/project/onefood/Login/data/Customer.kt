/*
 * File Name: Customer.kt
 * File Description: Store the customer info
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/03
 */
package com.project.onefood.Login.data

data class Customer(
    var firstName: String,
    var lastName: String,
    var emailAddress: String,
) {
    constructor(): this("", "", "")
}
