package ru.lorens.rosbankhack.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("surname") val surname: String,
    @Expose @SerializedName("desc") val desc: String?
)

data class Respons(
    @Expose @SerializedName("users") val users: List<User>
)