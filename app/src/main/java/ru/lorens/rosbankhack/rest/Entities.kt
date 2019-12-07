package ru.lorens.rosbankhack.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("name") val name: String,
    @Expose @SerializedName("surname") val surname: String,
    @Expose @SerializedName("desc") val desc: String?
)

data class Respons(
    @Expose @SerializedName("users") val users: List<User>
)

data class Screen(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("article_id") val article_id: Int,
    @Expose @SerializedName("title") val title: String?,
    @Expose @SerializedName("image") val image: String?,
    @Expose @SerializedName("desc") val desc: String?,
    @Expose @SerializedName("back_color") val back_color: String?
)

data class Article(
    @Expose @SerializedName("id") val id: Int,
    @Expose @SerializedName("title") val title: String?,
    @Expose @SerializedName("image") val image: String?,
    @Expose @SerializedName("desc") val desc: String?
)

data class Card(
    @Expose @SerializedName("article") val article: Article,
    @Expose @SerializedName("screens") val screens: List<Screen>
)
