package com.example.mynote.converter

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun fromDateToLong(temp:Date):Long{
        return temp.time
    }

    @TypeConverter
    fun fromLongToDate(value:Long):Date{
        return Date(value)
    }
}