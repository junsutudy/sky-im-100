package app.junsu.imback.datasource.photo.network.api_service

import app.junsu.imback.datasource.photo.model.PhotoDetailsResponse
import app.junsu.imback.datasource.photo.model.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PhotoApiService {
    @GET("/v2/list")
    suspend fun fetchPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int = 20,
    ): List<PhotoResponse>

    @GET("/id/{photo_id}/info")
    suspend fun fetchPhotoDetails(
        @Path("photo_id") id: Long,
    ): PhotoDetailsResponse
}