package app.junsu.imback.datasource.photo.model

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("author") val author: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String,
    @SerializedName("download_url") val downloadUrl: String,
)

data class PhotoDetailsResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("author") val author: String,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int,
    @SerializedName("url") val url: String,
    @SerializedName("download_url") val downloadUrl: String,
)
