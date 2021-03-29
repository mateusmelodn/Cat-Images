package com.mateusmelodn.catimages.core.api

import com.mateusmelodn.catimages.core.api.ApiKeys.CLIENT_ID
import com.mateusmelodn.catimages.core.model.CatImagesResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * An interface for imgur's public api
 * There are many other endpoints available here: https://apidocs.imgur.com/
 */
interface ImgurApi {
    /**
     * Get images of cats from imgur
     */
    @Headers("Authorization: Client-ID $CLIENT_ID")
    @GET("/3/gallery/search?&q=cats")
    suspend fun downloadCatImages(@Query("page") currentPage: Int): Response<CatImagesResponse>
}

object ApiKeys {
    const val CLIENT_ID = "1ceddedc03a5d71"
}