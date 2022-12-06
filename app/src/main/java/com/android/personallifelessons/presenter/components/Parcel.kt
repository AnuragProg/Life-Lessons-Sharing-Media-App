package com.android.personallifelessons.presenter.components

import android.net.Uri
import com.google.gson.Gson


fun Any.encodeToParcel():String{
    val dataJson = Gson().toJson(this)
    return Uri.encode(dataJson)
}

inline fun <reified T>String.decodeToT():T{
    val dataJson = Uri.decode(this)
    return Gson().fromJson(dataJson, T::class.java)
}