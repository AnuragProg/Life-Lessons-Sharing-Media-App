package com.android.personallifelessons.components



sealed class Outcome<out T>{
    data class Success<out T>(val data: T): Outcome<T>()
    data class Error(val error: PllException): Outcome<Nothing>()
    object Loading: Outcome<Nothing>()
}