package com.app.assessment.test

import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    fun convertStringToMills(dateString: String): Long {
        val sdf = SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.getDefault())
        val date = sdf.parse(dateString)
        return date?.time ?:-1L
    }

    fun convertStringFormatToAnother(dateString: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd 00:00:00", Locale.getDefault())
        val date = sdf.parse(dateString)
        val toSdf = SimpleDateFormat("MMM dd", Locale.getDefault())
        return toSdf.format(date)
    }
}