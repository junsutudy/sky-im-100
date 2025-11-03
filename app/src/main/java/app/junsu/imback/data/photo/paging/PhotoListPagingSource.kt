package app.junsu.imback.data.photo.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import app.junsu.imback.data.photo.model.Photo
import kotlinx.coroutines.delay
import kotlin.math.max

private const val STARTING_KEY = 0
private const val LOAD_DELAY_MILLIS = 3_000L

class PhotoListPagingSource(
    val getPhotos: suspend (page: Int, limit: Int) -> List<Photo>,
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val startKey = params.key ?: STARTING_KEY
        val response = getPhotos(startKey, params.loadSize)

        if (startKey != STARTING_KEY) {
            delay(LOAD_DELAY_MILLIS)
        }

        return LoadResult.Page(
            data = response, prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = startKey - params.loadSize)) {
                    STARTING_KEY -> null
                    else -> prevKey
                }
            }, nextKey = startKey + 1
        )
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}