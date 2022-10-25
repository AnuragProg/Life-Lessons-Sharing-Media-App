package com.android.personallifelessons.presenter.components

import java.text.SimpleDateFormat

object TimestampConvertor {

    fun longToFormattedDate(timestamp: Long): String{
        val format = SimpleDateFormat("dd-MM-yyyy")
        return format.format(timestamp)
    }
    fun longToFormattedTime(timestamp: Long): String{
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