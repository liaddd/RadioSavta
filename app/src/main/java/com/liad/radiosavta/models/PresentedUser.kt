package com.liad.radiosavta.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "presenter_table")
data class PresentedUser(@ColumnInfo(name = "image_url") val imageUrl: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}