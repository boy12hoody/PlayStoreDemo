package uz.boywonder.playstoredemo.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryItem(
    @Json(name = "category")
    val category: String,
    @Json(name = "items")
    val items: List<ImageItem> = mutableListOf()
)
