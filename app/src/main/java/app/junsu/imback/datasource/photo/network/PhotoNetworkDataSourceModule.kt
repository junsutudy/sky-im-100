package app.junsu.imback.datasource.photo.network

import app.junsu.imback.datasource.photo.network.api_service.PhotoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PhotoNetworkDataSourceModule {
    @Provides
    fun providePhotoApiService() {
        return PhotoApiService
    }
}