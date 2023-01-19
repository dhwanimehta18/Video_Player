package com.silverorange.videoplayer.response

import com.google.gson.annotations.SerializedName

data class VideoPlayerBean(

    @SerializedName("id") var id: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("hlsURL") var hlsURL: String? = null,
    @SerializedName("fullURL") var fullURL: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("author") var author: Author? = Author(),
    var size: Int = 0

)

data class Author(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null

)