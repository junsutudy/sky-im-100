package app.junsu.imback.features.photo_details

import androidx.lifecycle.ViewModel
import app.junsu.imback.data.photo.PhotoRepository
import app.junsu.imback.data.photo.model.PhotoDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

data class PhotoDetailsState(
    val photoDetails: PhotoDetails? = null,
)

sealed class PhotoDetailsSideEffect {
    data object Loading : PhotoDetailsSideEffect()
}

@HiltViewModel
class PhotoDetailsViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
) : ContainerHost<PhotoDetailsState, PhotoDetailsSideEffect>, ViewModel() {
    override val container =
        container<PhotoDetailsState, PhotoDetailsSideEffect>(PhotoDetailsState())

    fun loadPhotoDetails(photoId: Long) {
        intent {
            val photoDetails = photoRepository.getPhotoDetails(id = photoId)
            reduce {
                state.copy(photoDetails = photoDetails)
            }
        }
    }
}
