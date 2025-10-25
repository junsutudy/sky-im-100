package app.junsu.imback.data.photo

import app.junsu.imback.data.photo.model.Photo
import app.junsu.imback.datasource.photo.PhotoDatabaseDataSource
import app.junsu.imback.datasource.photo.PhotoNetworkDataSource
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoNetworkDataSource: PhotoNetworkDataSource,
    private val photoDatabaseDataSource: PhotoDatabaseDataSource,
) {
    suspend fun getPhotoList(
        page: Int = 0,
        limit: Int = PHOTOS_FETCH_LIMIT,
    ): List<Photo> {
        val fetchedPhotos = photoNetworkDataSource.fetchPhotos(
            page = page,
            limit = limit,
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