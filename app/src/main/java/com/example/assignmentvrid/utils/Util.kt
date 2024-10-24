package com.example.assignmentvrid.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Util {
    @RequiresApi(Build.VERSION_CODES.O)
    public fun convertDateFormat(inputDate: String): String {
        val isoFormatter = DateTimeFormatter.ISO_DATE_TIME
        val localDateTime = LocalDateTime.parse(inputDate, isoFormatter)

        // Define the desired output format (dd-MM-yy)
        val outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yy")

        // Format the parsed LocalDateTime to the desired format
        val formattedDate = localDateTime.format(outputFormatter)
        return formattedDate
    }
}