package app.junsu.imback.datasource.photo.network

import app.junsu.imback.data.photo.PhotoRepositoryImpl.Companion.PHOTOS_FETCH_LIMIT
import app.junsu.imback.datasource.photo.model.PhotoResponse
import app.junsu.imback.datasource.photo.network.api_service.PhotoApiService
import javax.inject.Inject

abstract class PhotoNetworkDataSource {
    abstract suspend fun fetchPhotos(page: Int?, limit: Int?): List<PhotoResponse>
}

class PhotoNetworkDataSourceImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
) : PhotoNetworkDataSource() {
    override suspend fun fetchPhotos(
        page: Int?,
        limit: Int?,
    ): List<PhotoResponse> {
        return photoApiService.fetchPhotos(
            page = page ?: 0,
            limit = limit ?: PHOTOS_FETCH_LIMIT,
        )
    }
}