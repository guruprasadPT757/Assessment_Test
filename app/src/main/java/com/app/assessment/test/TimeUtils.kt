package com.app.assessment.test

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.*

/**
 * This is utility class in which some of reusable methods are implemented for Time and date
 * operations.
 */
object TimeUtils {

    /**
     * This method is used to convert date string to millis
     *
     * @param dateString this should be in "yyyy-MM-dd" format.
     * @return [Long] timestamp in millis.
     */
    fun convertStringToMills(dateString: String): Long {
        if (dateString.isEmpty()) return -1L
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(dateString)
        return date?.time ?:-1L
    }

    /**
     * This method is used change date from "yyyy-MM-dd to "MMM yyyy" format.
     *
     * @param textview to which date needs to set.
     * @param dateString format to be changed.
     */
    @JvmStatic
    @BindingAdapter("app:formatDate")
    fun convertStringFormatToAnother(textview: TextView, dateString: String) {
        if (dateString.isEmpty()) {
            return
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(dateString)
        val toSdf = SimpleDateFormat("MMM yyyy", Locale.getDefault())
        textview.text =  toSdf.format(date)?:dateString
    }
}
