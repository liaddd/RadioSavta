package com.liad.radiosavta.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.liad.radiosavta.utils.Constants.BASE_IMAGE_URL

@Entity(tableName = "users")
data class User(

    @PrimaryKey
    val id: Int? = null,

    @SerializedName("profile_image")
    val profileImage: String? = null,

    val quote: String? = null,

    val name: String? = null,

    val location: String? = null
) {

    fun getProfileImg(): String {
        return BASE_IMAGE_URL + profileImage
    }
}