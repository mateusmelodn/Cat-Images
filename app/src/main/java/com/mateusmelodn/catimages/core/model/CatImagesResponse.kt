package com.mateusmelodn.catimages.core.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CatImagesResponse(
    @Json(name = "data")
    val catImages: List<CatImages>,
    @Json(name = "status")
    val status: Int,
    @Json(name = "success")
    val success: Boolean
)

@JsonClass(generateAdapter = true)
data class CatImages(
    @Json(name = "title")
    val title: String,
    @Json(name = "link")
    val link: String,
    @Json(name = "images")
    val images: List<Image>?,
)

@JsonClass(generateAdapter = true)
data class Image(
    @Json(name = "link")
    val link: String,
    @Json(name = "type")
    val type: String
)

