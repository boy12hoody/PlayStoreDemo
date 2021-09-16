package uz.boywonder.playstoredemo.data

import retrofit2.Response
import uz.boywonder.playstoredemo.data.network.ImageAPI
import uz.boywonder.playstoredemo.model.ImageItem
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val imageAPI: ImageAPI
) {

    suspend fun getImageList(): Response<List<ImageItem>> {
        return imageAPI.getImageList()
    }
}