package com.liad.radiosavta.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "program_times")
data class ProgramTimes(

    @Expose
    @PrimaryKey
    val programId: Int? = null,

    @SerializedName("start_time")
    val startTime: String? = null,

    @SerializedName("end_time")
    val endTime: String? = null,

    @SerializedName("day_of_week")
    val dayOfWeek: Int? = null
)