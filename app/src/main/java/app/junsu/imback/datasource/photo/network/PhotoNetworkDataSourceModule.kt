package app.junsu.imback.datasource.photo.network

import app.junsu.imback.datasource.photo.network.api_service.PhotoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object PhotoNetworkDataSourceModule {
    @Provides
    fun providePhotoApiService(retrofit: Retrofit): PhotoApiService {
        return retrofit.create(PhotoApiService::class.java)
    }
}