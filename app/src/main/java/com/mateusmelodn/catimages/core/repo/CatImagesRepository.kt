package com.mateusmelodn.catimages.core.repo

import com.mateusmelodn.catimages.core.api.ImgurApi
import com.mateusmelodn.catimages.core.model.CatImages

interface CatImagesRepository {
    suspend fun downloadCatImages(currentPage: Int): Result<List<CatImages>>
}

@Suppress("BlockingMethodInNonBlockingContext")
class CatImagesRepositoryImp(private val imgurApi: ImgurApi): CatImagesRepository {
    /**
     * Downloads images of cats via retrofit and returns a Result.
     */
    override suspend fun downloadCatImages(currentPage: Int): Result<List<CatImages>> {
        return try  {
            val response = imgurApi.downloadCatImages(currentPage)
            if (response.isSuccessful) {
                Result.success(response.body()!!.catImages)
            } else {
                Result.failure(Exception("Unknown network Exception."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}