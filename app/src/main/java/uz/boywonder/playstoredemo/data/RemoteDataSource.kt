package uz.boywonder.playstoredemo.data

import retrofit2.Response
import uz.boywonder.playstoredemo.data.network.ImageAPI
import uz.boywonder.playstoredemo.model.ImageItem
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val imageAPI: ImageAPI
) {

    suspend fun getRandomImage(width: Int, height: Int) : Response<ImageItem> {
        return imageAPI.getRandomImage(width, height)
    }

    suspend fun getImageList(page: Int, limit: Int): Response<List<ImageItem>> {
        return imageAPI.getImageList(page, limit)
    }
}