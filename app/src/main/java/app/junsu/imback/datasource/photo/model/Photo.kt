package app.junsu.imback.datasource.photo.model

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String,
    @SerializedName("download_url") val downloadUrl: String,
)

// Details에 더 많은 필드가 생성될 수 있는 것을 고려하여 일반 Photo 클래스와 다른 클래스를 생성하였습니다.
// 만약 일반 Photo 클래스를 사용한다면 List -> Details Navigation 시 직렬화/역직렬화를 통해
// Argument에서 Photo 정보를 불러올 수 있겠다고 생각하였습니다.
data class PhotoDetailsResponse(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String,
    @SerializedName("download_url") val downloadUrl: String,
)

data class PhotoRecommendation(
    val type: Type,
    val photoId: String,
) {
    enum class Type {
        RECENT_VIEWED, RECENT_FROM_BOOKMARKS, EXCITED, POPULAR,
        ;
    }
}