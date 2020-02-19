package com.liad.radiosavta.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recorded_show")
        class RecordedShow(
    @PrimaryKey
    val id: Int? = null,

    @SerializedName("program_id")
    @ColumnInfo(name = "program_id")
    val programId: Int? = null,

    val url: String? = null,

    val name: String? = null,

    val duration: String? = null,

    @SerializedName("is_displayed")
    @ColumnInfo(name = "is_displayed")
    val isDisplayed: Boolean = false,

    @SerializedName("recorded_at")
    @ColumnInfo(name = "recorded_at")
    val recordedAt: String? = null,

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null

)