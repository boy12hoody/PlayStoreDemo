package uz.boywonder.playstoredemo.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import uz.boywonder.playstoredemo.model.CategoryItem
import uz.boywonder.playstoredemo.model.ImageItem
import uz.boywonder.playstoredemo.util.Constants.Companion.QUERY_PAGE_NUMBER
import uz.boywonder.playstoredemo.util.Constants.Companion.QUERY_PAGE_SIZE

interface ImageAPI {

    @GET("{width}/{height}")
    suspend fun getRandomImage(
        @Path("width") width: Int,
        @Path("height") height: Int
    ): Response<ImageItem>

    @GET("v2/list")
    suspend fun getImageList(
        @Query("page") page: Int = QUERY_PAGE_NUMBER,
        @Query("limit") limit: Int = QUERY_PAGE_SIZE
    ): Response<List<ImageItem>>
}