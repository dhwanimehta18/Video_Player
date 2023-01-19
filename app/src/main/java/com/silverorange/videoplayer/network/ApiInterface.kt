package com.silverorange.videoplayer.network

import com.silverorange.videoplayer.response.VideoPlayerBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET

interface ApiInterface {

    @GET("/videos") // specify the sub url for our base url
    fun getVideoList():Call<List<VideoPlayerBean>>


}