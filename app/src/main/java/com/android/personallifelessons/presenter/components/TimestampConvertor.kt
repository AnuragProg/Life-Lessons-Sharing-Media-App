package com.android.personallifelessons.presenter.components

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
object TimestampConvertor {

    private fun longToFormattedDate(timestamp: Long): String{
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(timestamp)
    }
    private fun longToFormattedTime(timestamp: Long): String{
        val format = SimpleDateFormat("hh:mm a")
        return format.format(timestamp)
    }

    fun dateThenTime(timestamp: Long, separator: String = " "): String{
        return longToFormattedDate(timestamp) + separator + longToFormattedTime(timestamp)
    }
    fun timeThenDate(timestamp: Long, separator: String = " "): String{
        return longToFormattedTime(timestamp) + separator + longToFormattedDate(timestamp)
    }

}