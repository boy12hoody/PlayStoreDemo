package uz.boywonder.playstoredemo.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageItem(
    @Json(name = "author")
    val author: String,
    @Json(name = "download_url")
    val downloadUrl: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "id")
    val id: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "width")
    val width: Int
)