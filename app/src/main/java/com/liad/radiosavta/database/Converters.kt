package com.liad.radiosavta.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.liad.radiosavta.models.ProgramTimes
import com.liad.radiosavta.models.User

class Converters {

    // ProgramTimes converter

    @TypeConverter
    fun stringToProgramTimes(data: String?): ProgramTimes =
        Gson().fromJson(data, ProgramTimes::class.java)

    @TypeConverter
    fun programTimesToString(programTimes: ProgramTimes?): String = Gson().toJson(programTimes)

    // User converter

    @TypeConverter
    fun stringToUser(data: String): List<User> {
        val listType = object : TypeToken<List<User>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun userToString(users: List<User?>?): String = Gson().toJson(users)
}