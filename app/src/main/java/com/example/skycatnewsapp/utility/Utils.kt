package com.example.skycatnewsapp.utility

import java.text.ParseException
import java.text.SimpleDateFormat

import java.util.*
import java.util.Locale


fun getRelativeTime(isoDate: String): String {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(isoDate) ?: return ""

        val nowUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        val diffInMillis = kotlin.math.abs(nowUtc.time - date.time)

        val minutes = (diffInMillis / (1000 * 60)).toInt()
        val hours = (diffInMillis / (1000 * 60 * 60)).toInt()
        val days = (diffInMillis / (1000 * 60 * 60 * 24)).toInt()

//        Log.d("TimeDebug", "Now (ms): ${System.currentTimeMillis()}")
//        Log.d("TimeDebug", "Parsed date (ms): ${diffInMillis}")
//        Log.d("TimeDebug", "Now (UTC): ${Calendar.getInstance(TimeZone.getTimeZone("UTC")).time}")
//        Log.d("TimeDebug", "minutes: $minutes")
//        Log.d("TimeDebug", "hours: $hours")
//        Log.d("TimeDebug", "days: $days")

        when {
            minutes < 1 -> "just now"
            minutes < 60 -> "$minutes min${if (minutes > 1) "s" else ""} ago"
            hours < 24 -> "$hours hour${if (hours > 1) "s" else ""} ago"
            days < 7 -> "$days day${if (days > 1) "s" else ""} ago"
            else -> {
                val localSdf = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
                localSdf.timeZone = TimeZone.getDefault()
                localSdf.format(date)
            }

        }
    } catch (e: ParseException) {
        e.printStackTrace()
        ""
    }
}