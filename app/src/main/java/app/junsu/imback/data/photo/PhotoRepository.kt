package app.junsu.imback.data.photo

import app.junsu.imback.data.photo.model.Photo
import app.junsu.imback.datasource.photo.database.PhotoDatabaseDataSource
import app.junsu.imback.datasource.photo.network.PhotoNetworkDataSource
import javax.inject.Inject

abstract class PhotoRepository {
    abstract suspend fun getPhotos(
        page: Int?,
        limit: Int?,
    ): List<Photo>
}

class PhotoRepositoryImpl @Inject constructor(
    private val photoNetworkDataSource: PhotoNetworkDataSource,
    private val photoDatabaseDataSource: PhotoDatabaseDataSource,
) : PhotoRepository() {
    override suspend fun getPhotos(
        page: Int?,
        limit: Int?,
    ): List<Photo> {
        val fetchedPhotos = photoNetworkDataSource.fetchPhotos(
            page = page ?: 0,
            limit = limit ?: PHOTOS_FETCH_LIMIT,
        )
        return fetchedPhotos.map { response ->
            Photo(
                id = response.id,
                author = response.author,
                width = response.width,
                height = response.height,
                url = response.url,
                downloadUrl = response.downloadUrl,
            )
        }
    }

    companion object {
        const val PHOTOS_FETCH_LIMIT = 50
    }
}