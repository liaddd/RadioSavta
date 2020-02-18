package com.liad.radiosavta.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.liad.radiosavta.utils.Constants.BASE_IMAGE_URL


@Entity(tableName = "programs")
data class Program(

    @PrimaryKey
    val id: Int? = null,

    @SerializedName("cover_image")
    val coverImage: String? = null,

    @SerializedName("name_he")
    val nameHe: String? = null,

    @SerializedName("name_en")
    val nameEn: String? = null,

    @SerializedName("programTimes")
    val programTimes: ProgramTimes? = null,

    val description: String? = null,


    val users: List<User>? = null

) {

    fun getCover(): String {
        return BASE_IMAGE_URL + coverImage
    }
}