package app.junsu.imback.data.photo.repository

import app.junsu.imback.data.photo.model.Photo
import app.junsu.imback.data.photo.model.PhotoDetails
import app.junsu.imback.datasource.photo.database.PhotoDatabaseDataSource
import app.junsu.imback.datasource.photo.network.PhotoNetworkDataSource
import javax.inject.Inject

abstract class PhotoRepository {
    abstract suspend fun getPhotos(
        page: Int?,
        limit: Int?,
    ): List<Photo>

    abstract suspend fun getPhotoDetails(
        id: Long,
    ): PhotoDetails
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

    override suspend fun getPhotoDetails(id: Long): PhotoDetails {
        val fetchedPhoto = photoNetworkDataSource.fetchPhotoDetails(id = id)
        return fetchedPhoto.let {
            PhotoDetails(
                id = it.id,
                author = it.author,
                width = it.width,
                height = it.height,
                url = it.url,
                downloadUrl = it.downloadUrl,
            )
        }
    }

    companion object {
        const val PHOTOS_FETCH_LIMIT = 50
    }
}