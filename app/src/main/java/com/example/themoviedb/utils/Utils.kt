package com.example.themoviedb.utils

import java.text.SimpleDateFormat

class Utils {

companion object{
    var formatDateServerDateOnly = "yyyy-MM-dd"
    var formatDateCust = "dd MMMM yyyy"
    fun changeDateFormat(oldDateString: String):String{
        try {
            var newDateString= ""

            val sdf = SimpleDateFormat(formatDateServerDateOnly)
            val d = sdf.parse(oldDateString)
            sdf.applyPattern(formatDateCust)
            newDateString = sdf.format(d)
            return newDateString
        } catch (e: Exception) {
            return oldDateString
        }
    }
}
}