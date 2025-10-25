package app.junsu.imback.data.photo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PhotoModule {

    @Binds
    abstract fun bindPhotoRepository(): PhotoRepository
}
