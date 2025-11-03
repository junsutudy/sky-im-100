package app.junsu.imback.data.photo

import app.junsu.imback.data.photo.repository.PhotoRepository
import app.junsu.imback.data.photo.repository.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoDataModule {

    @Binds
    abstract fun bindPhotoRepository(impl: PhotoRepositoryImpl): PhotoRepository
}
