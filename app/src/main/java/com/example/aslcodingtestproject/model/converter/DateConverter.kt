package com.example.aslcodingtestproject.model.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.autotoll.ffts.model.constant.IConstants
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@ProvidedTypeConverter
class DateConverter {

    private val fromDateFormat = SimpleDateFormat(
        IConstants.BASIC.DateTimeFormatSimple,
        Locale.ENGLISH
    )

    @TypeConverter
    fun toDate(strDate: String?): Date? {


        return try {
            fromDateFormat.parse(strDate ?: "")
        } catch (e: Exception) {
            Timber.e(e)
            null
        }

    }

    @TypeConverter
    fun toString(date: Date?): String {

        if (date == null) return ""

        return try {
            fromDateFormat.format(date)
        } catch (e: Exception) {

            ""
        }
    }
}