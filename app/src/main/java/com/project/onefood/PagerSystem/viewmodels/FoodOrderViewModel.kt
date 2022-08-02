/*
 * File Name: FoodOrderViewModel.kt
 * File Description: Store data for food order activity
 * Author: Ching Hang Lam
 * Last Modified: 2022/08/02
 */
package com.project.onefood.PagerSystem.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodOrderViewModel: ViewModel() {

    // Id
    val _id: MutableLiveData<Long> = MutableLiveData<Long>(0)
    var id: Long
        get() = _id.value!!
        set(value) { _id.value = value }

    // Time
    val _time: MutableLiveData<Long> = MutableLiveData<Long>(0)
    var time: Long
        get() = _time.value!!
        set(value) { _time.value = value }

    // Code
    val _code: MutableLiveData<String> = MutableLiveData<String>("")
    var code: String
        get() = _code.value!!
        set(value) { _code.value = value }

    // Remark
    val _remark: MutableLiveData<String> = MutableLiveData<String>("")
    var remark: String
        get() = _remark.value!!
        set(value) { _remark.value = value }

    // QR code
    val _qrCode: MutableLiveData<Bitmap> = MutableLiveData<Bitmap>()
    var qrCode: Bitmap
        get() = _qrCode.value!!
        set(value) { _qrCode.value = value }
}