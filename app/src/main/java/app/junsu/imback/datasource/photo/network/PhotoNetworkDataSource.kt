package app.junsu.imback.datasource.photo.network

import app.junsu.imback.datasource.photo.network.api_service.PhotoApiService
import javax.inject.Inject

abstract class PhotoNetworkDataSource

class PhotoNetworkDataSourceImpl @Inject constructor(
    private val photoApiService: PhotoApiService,
) : PhotoNetworkDataSource()