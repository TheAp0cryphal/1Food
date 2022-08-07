package com.project.onefood.PagerSystem.viewmodels

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FoodOrderViewModel: ViewModel() {

    // Order number
    private val _orderNumber: MutableLiveData<Int> = MutableLiveData<Int>(0)
    var orderNumber: Int
        get() = _orderNumber.value!!
        set(value) { _orderNumber.value = value }

    // Order time
    private val _orderTime: MutableLiveData<Long> = MutableLiveData<Long>(0)
    var orderTime: Long
        get() = _orderTime.value!!
        set(value) { _orderTime.value = value }

    // Code
    private val _code: MutableLiveData<String> = MutableLiveData<String>("")
    var code: String
        get() = _code.value!!
        set(value) { _code.value = value }

    // Remarks
    private val _remarks: MutableLiveData<String> = MutableLiveData<String>("")
    var remarks: String
        get() = _remarks.value!!
        set(value) { _remarks.value = value }

    // QR code
    private val _qrCode: MutableLiveData<Bitmap> = MutableLiveData<Bitmap>()
    var qrCode: Bitmap
        get() = _qrCode.value!!
        set(value) { _qrCode.value = value }
}