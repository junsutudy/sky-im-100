package app.junsu.imback.features.main.tabs.photo_list

import androidx.lifecycle.ViewModel
import app.junsu.imback.data.photo.PhotoRepository
import app.junsu.imback.data.photo.model.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoListViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PhotoListUiState())
    val uiState: StateFlow<PhotoListUiState>
        get() = _uiState.asStateFlow()
}

data class PhotoListUiState(
    val photos: List<Photo> = emptyList(),
)
