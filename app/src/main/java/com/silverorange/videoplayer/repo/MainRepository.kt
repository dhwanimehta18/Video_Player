package com.silverorange.videoplayer.repo

import com.silverorange.videoplayer.network.ApiClient
import com.silverorange.videoplayer.network.getResult

object MainRepository {
    suspend fun getVideoList() = ApiClient.service.getVideoList().getResult()
}