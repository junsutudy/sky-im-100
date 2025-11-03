package app.junsu.imback.features.main.tabs.photo_list

import androidx.lifecycle.ViewModel
import app.junsu.imback.data.photo.repository.PhotoRepository
import app.junsu.imback.data.photo.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PhotoListUiState())
    val uiState: StateFlow<PhotoListUiState>
        get() = _uiState.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fetchedPhotos = photoRepository.getPhotos(page = 0, limit = 50)
                _uiState.emit(uiState.value.copy(photos = fetchedPhotos))
            } catch (_: Exception) {
            }
        }
    }
}

data class PhotoListUiState(
    val photos: List<Photo> = emptyList(),
)
