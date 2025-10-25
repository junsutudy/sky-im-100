package app.junsu.imback.datasource.photo

import app.junsu.imback.datasource.photo.network.PhotoNetworkDataSource
import app.junsu.imback.datasource.photo.network.PhotoNetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoDataSourceModule {

//    @Binds
//    abstract fun bindPhotoDatabaseDataSource(): PhotoDatabaseDataSource

    @Binds
    abstract fun bindPhotoNetworkDataSource(impl: PhotoNetworkDataSourceImpl): PhotoNetworkDataSource
}