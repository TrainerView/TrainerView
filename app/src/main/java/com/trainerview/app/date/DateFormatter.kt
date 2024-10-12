package com.trainerview.app.date

import java.text.SimpleDateFormat
import java.util.Date

object DateFormatter {

//    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")

    fun formatString(date: Date) : String {
        return dateFormat.format(date)
    }

    fun parseDate(string: String): Date? {
        return dateFormat.parse(string)
    }
}